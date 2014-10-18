package edu.ncu.projectMgr.service;

import com.wansan.template.service.IBaseDao;
import edu.ncu.projectMgr.model.Projects;

import java.util.List;

/**
 * Created by Administrator on 2014/9/26.
 */
public interface IProjectService extends IBaseDao<Projects> {
    public Boolean hasProject(String projectID);
    public void txImportData(List<Projects> list);
}
