package com.wansan.template.service;

import com.wansan.template.model.Resource;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 14-4-27.
 */
public interface IResourceService extends IBaseDao<Resource> {
    public List<Resource> getParent();
    public List<Resource> getChildren(String parent);
    public List<Resource> getMenusByRole(String roleId);
    public void txSetRoleResource(String roleId,String idList,boolean isMenu);
    public void txDelMenuItem(String idList);
    public List<Resource> getMenusByUsername(String username);
    public List<Resource> getAllResource();
    public List<Resource> getAllMenus();
    public String getRolesByResource(String resourceId);
    public void txAddRoleResource(String roleId,String resourceId,String resourceType);
}
