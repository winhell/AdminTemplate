package edu.ncu.projectMgr.service;

import com.wansan.template.service.BaseDao;
import edu.ncu.projectMgr.model.Projects;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2014/10/9.
 */
@Service
public class ProjectService extends BaseDao<Projects> implements IProjectService {
    @Override
    public Boolean hasProject(String projectID) {
        List result = findByProperty("registerID",projectID);
        return result.size()>0;
    }

    @Override
    public void txImportData(List<Projects> list) {
        saveOrUpdate(list);
    }
}
