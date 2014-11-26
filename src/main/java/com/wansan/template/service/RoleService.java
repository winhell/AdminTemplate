package com.wansan.template.service;

import com.wansan.template.core.Utils;
import com.wansan.template.model.Person;
import com.wansan.template.model.Role;
import com.wansan.template.model.UserRole;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 14-4-15.
 */

@Service
public class RoleService extends BaseDao<Role> implements IRoleService {
    @Override
    @SuppressWarnings("unchecked")
    public List<Role> getRolesByUser(Person person){
        List<UserRole> userRoles = publicFind("from UserRole where userid = '" + person.getId() + "'");
        List<Role> result = new ArrayList<Role>();
        for(UserRole userRole:userRoles){
            Role role = findById(userRole.getRoleid());
            result.add(role);
        }
        return result;
    }

    public String getRolesByUserID(String userID){
        List<UserRole> userRoles = publicFind("from UserRole where userid = '" + userID + "'");
        StringBuilder builder = new StringBuilder();
        for(UserRole userRole:userRoles)
            builder.append(userRole.getRoleid()).append(",");
        return builder.toString();
    }

    public void txSetRolesByUserID(String userID,String idList){
        String[] ids = idList.split(",");
        executeQuery("delete from UserRole where userid = '"+userID+"'");
        for(String id:ids){
            UserRole userRole = new UserRole();
            userRole.setUserid(userID);
            userRole.setRoleid(id);
            userRole.setId(Utils.getNewUUID());
            userRole.setCreatetime(Utils.getNow());
            getSession().save(userRole);
        }
    }

    @Override
    public void txDelete(String idList,Person oper){
        String[] ids = idList.split(",");
        for(String id:ids){
            executeQuery("delete from UserRole where roleId = '"+id+"'");
            executeQuery("delete from RoleResource where roleId ='"+id+"'");
        }
        delete(idList);

    }

    public List<Role> getRoleList(Person person){
        String userRoleIDs = getRolesByUserID(person.getId());
        if(userRoleIDs.contains("super"))
            return listAll();
        else {
            Query query = getSession().createQuery("from Role where id <> 'super'");
            return query.list();
        }
    }

    public Boolean isUserInRole(Person person,String roleName){
        List<Role> roles = getRoleList(person);
        Role role = findByName(roleName).get(0);
        return roles.contains(role);
    }
}
