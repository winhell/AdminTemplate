package com.wansan.template.controller;

import com.wansan.template.model.CodeEnum;
import com.wansan.template.model.ResultEnum;
import com.wansan.template.model.Systemcode;
import com.wansan.template.service.ISystemcodeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 14-7-13.
 */
@Controller
@ResponseBody
@RequestMapping(value = "/mainpage/system")
public class CodeController extends BaseController{
    @Resource
    private ISystemcodeService systemcodeService;

    @RequestMapping(value = "/getCodeTypeList")
    public Map<String,String> getTypeList(){
        return systemcodeService.getCodetypeList();
    }

    @RequestMapping(value = "/getCodeList")
    public List<Systemcode> getList(CodeEnum type){
        return systemcodeService.getCodeList(type);
    }

    @RequestMapping(value = "/getAllCode")
    public Map<String,Object> getAll(String codeIndex){
        Map<String,Object> result = new HashMap<>();
        List<Systemcode> codeList;
        if(codeIndex.equals("all"))
            codeList = systemcodeService.findByMapWithCond(null,"type,code",true);
        else {
            CodeEnum codeEnum = CodeEnum.valueOf(codeIndex);
            Map<String,Object> params = new HashMap<>();
            params.put("type =",codeEnum);
            codeList = systemcodeService.findByMapWithCond(params,"type,code",true);
        }
        result.put("data",codeList);
        result.put("status", ResultEnum.SUCCESS);
        return result;
    }

    @RequestMapping(value = "/addcode")
    public Map<String,Object> add(Systemcode systemcode){
        try{
            systemcodeService.txSave(systemcode,getLoginPerson());
            return returnMap(true,"添加字典成功！");
        }catch (Exception e){
            return returnMap(false,e.getMessage());
        }
    }

    @RequestMapping(value = "/updatecode")
    public Map<String,Object> update(Systemcode systemcode){
        try{
            systemcodeService.txUpdate(systemcode,getLoginPerson());
            return returnMap(true,"修改字典成功！");
        }catch (Exception e){
            return returnMap(false,e.getMessage());
        }
    }

    @RequestMapping(value = "/deletecode")
    public Map<String,Object> delete(String idList){
        try {
            systemcodeService.txDelete(idList,getLoginPerson());
            return returnMap(true,"成功删除字典条目！");
        }catch (Exception e){
            return returnMap(false,e.getMessage());
        }
    }
}
