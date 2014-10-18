package edu.ncu.projectMgr.controller;

import com.wansan.template.controller.BaseController;
import com.wansan.template.core.Utils;
import com.wansan.template.model.ResultEnum;
import edu.ncu.projectMgr.model.Projects;
import edu.ncu.projectMgr.service.IProjectService;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by Administrator on 2014/9/26.
 */
@RestController
@RequestMapping(value = "/mainpage/projmgr")
public class ProjectController extends BaseController {
    private Logger logger = Logger.getLogger(this.getClass());

    @Resource
    private IProjectService projectService;
    private final static String uploadPath = "/upload/doc";

    @RequestMapping(value = "/getProjectsList")
    public Map<String,Object> getList(int page,int rows){
        Map<String,Object> result = new HashMap<>();
        try {
            result = projectService.findByMap(null,page,rows,"createtime",false);
            result.put("status",ResultEnum.SUCCESS);
        }catch (Exception e){
            logger.error(e.getMessage());
            result.put("status",ResultEnum.FAIL);
        }
        return result;
    }

    @RequestMapping(value = "/wrapperList")
    public Map<String,Object> wrapper(int start,int length,int draw,HttpServletRequest request){
        Map<String,Object> temp = getList(start/length+1,length);
        Map<String,Object> result = new HashMap<>();
        String searchStr = request.getParameter("search[value]");
        logger.info(searchStr);
        result.put("recordsTotal",temp.get("total"));
        result.put("recordsFiltered",temp.get("total"));
        result.put("data",temp.get("rows"));
        result.put("draw",draw);
        return result;
    }

    @RequestMapping(value = "/clientList")
    public Map<String,Object> client(){
        Map<String,Object> result = new HashMap<>();
        result.put("data",projectService.listAll());
        return result;
    }

    @RequestMapping(value = "/addproject")
    public Map<String,Object> add(Projects projects){
        try {
            projectService.txSave(projects,getLoginPerson());
            return result(true);
        }catch (Exception e){
            logger.error(e.getMessage());
            return result(false);
        }
    }

    @RequestMapping(value = "/deleteproject")
    public Map<String,Object> delete(String idList){
        try {
            projectService.txDelete(idList,getLoginPerson());
            return result(true);
        }catch (Exception e){
            logger.error(e.getMessage());
            return result(false);
        }
    }

    @RequestMapping(value = "/updateproject")
    public Map<String,Object> update(Projects projects){
        try {
            projectService.txUpdate(projects,getLoginPerson());
            return result(true);
        }catch (Exception e){
            logger.error(e.getMessage());
            return result(false);
        }
    }

    @RequestMapping(value = "/uploadZip")
    public Map<String,Object> uploadZip(MultipartFile uploadFile,HttpServletRequest request) throws IOException {
        Map<String,Object> json = new HashMap<>();
        int length;
        byte b[] = new byte [1024];
        CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getServletContext());
        if(resolver.isMultipart(request)&&null!=uploadFile){
            String fileName = uploadFile.getOriginalFilename();
            String extension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase().substring(1);
            if(!"zip".equals(extension)){
                json.put("status", ResultEnum.FAIL);
                json.put("msg", "错误的文件格式！");
                return json;
            }
            File newFile = new File("temp.zip");
            uploadFile.transferTo(newFile);
            ZipEntry entry;
            ZipFile zipFile = new ZipFile(newFile);
            String path = request.getServletContext().getRealPath(uploadPath);
            Enumeration enumeration = zipFile.entries();
            while(enumeration.hasMoreElements()){
                entry = (ZipEntry) enumeration.nextElement();
                File loadFile = new File(path,entry.getName());
                OutputStream outputStream = new FileOutputStream(loadFile);
                InputStream inputStream = zipFile.getInputStream(entry);
                while ((length = inputStream.read(b)) > 0)
                    outputStream.write(b, 0, length);
                outputStream.flush();
                outputStream.close();
            }
            newFile.delete();
        }
        json.put("status",ResultEnum.SUCCESS);
        json.put("msg","论文上传并解包成功！");
        return json;
    }

    @RequestMapping(value = "/uploadExcel")
    public Map<String,Object> uploadExcel(MultipartFile uploadFile,HttpServletRequest request) throws IOException {
        Map<String,Object> json = new HashMap<>();
        CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getServletContext());
        if(resolver.isMultipart(request)&&null!=uploadFile){
            String fileName = uploadFile.getOriginalFilename();
            String extension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase().substring(1);
            if(!"xls".equals(extension)){
                json.put("status", ResultEnum.FAIL);
                json.put("msg", "错误的文件格式！");
                return json;
            }
            POIFSFileSystem fs = new POIFSFileSystem(uploadFile.getInputStream());
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            int rowIndex = 1;
            HSSFRow row;
            List<Projects> excelList = new ArrayList<>();
            while ((row=sheet.getRow(rowIndex))!=null){
                rowIndex ++ ;
                HSSFCell cell = row.getCell(1);
                String registerID = cell.getStringCellValue();
                if(null==registerID||"".equals(registerID)){
                    json.put("status",ResultEnum.FAIL);
                    json.put("msg","文件中包含无效的项目编号，导入中断！");
                    return json;
                }
                if(projectService.hasProject(registerID))
                    continue;
                Projects project = new Projects();
                project.setId(Utils.getNewUUID());
                project.setRegisterId(getCellText(row,1));
                project.setName(row.getCell(2).getStringCellValue());
                project.setDepart(row.getCell(3).getStringCellValue());
                project.setOperatorId(getLoginPerson().getId());
                project.setOwner(row.getCell(4).getStringCellValue());
                project.setProjYear(getCellText(row,7));
                excelList.add(project);
            }
            projectService.txImportData(excelList);
            json.put("status",ResultEnum.SUCCESS);
            json.put("msg","成功导入"+excelList.size()+"条记录！");
            excelList = null;
        }
        return json;
    }

    //强制把单元格内容转成字符串
    private String getCellText(HSSFRow row, int cellNum){
        HSSFCell cell = row.getCell(cellNum);
        switch (cell.getCellType()){
            case HSSFCell.CELL_TYPE_NUMERIC:
                Integer temp = (int)cell.getNumericCellValue();
                return temp.toString();
            case HSSFCell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            default:
                return "null";
        }
    }
}
