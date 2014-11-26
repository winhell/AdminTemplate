package com.wansan.template.service;

import com.wansan.template.model.Person;
import com.wansan.template.model.Role;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 14-4-15.
 */
public interface IRoleService extends IBaseDao<Role> {
    List<Role> getRolesByUser(Person person);
    public void txSetRolesByUserID(String userID,String idList);
    public String getRolesByUserID(String userID);
    public List<Role> getRoleList(Person person);
    public Boolean isUserInRole(Person person,String roleName);
}
