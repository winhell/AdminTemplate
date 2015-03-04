package edu.ncu.projectMgr.service;

import com.wansan.template.model.Person;
import com.wansan.template.service.BaseDao;
import com.wansan.template.service.IRoleService;
import edu.ncu.projectMgr.model.Projects;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/10/9.
 */
@Service
public class ProjectService extends BaseDao<Projects> implements IProjectService {

    @Resource
    private IRoleService roleService;

    @Override
    public Boolean hasProject(String projectID) {
        List result = findByProperty("registerID",projectID);
        return result.size()>0;
    }

    @Override
    public void txImportData(List<Projects> list) {
        saveOrUpdate(list);
    }

    public List<Projects> getProjectList(Person user,Integer codeIndex){
        Map<String,Object> params = new HashMap<>();
        Boolean isAdmin = roleService.isUserInRole(user,"ROLE_ADMIN");
        if(codeIndex==0 && isAdmin)
            return listAll();
        if(codeIndex!=0)
            params.put("processType",codeIndex);
        if(!isAdmin)
            params.put("depart",user.getDepartId());
        return findByMap(null,params);
    }
}
