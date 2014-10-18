package com.wansan.template.service;

import com.wansan.template.core.Utils;
import com.wansan.template.model.Person;
import com.wansan.template.model.Resource;
import com.wansan.template.model.RoleResource;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Administrator on 14-4-27.
 */
@Service
public class ResourceService extends BaseDao<Resource> implements IResourceService {

    @javax.annotation.Resource
    private IPersonService personService;

    public List<Resource> getParent(){
        return getChildren("0");
    }

    public List<Resource> getChildren(String parent){
        return findByProperty("parentId",parent);
    }

    public List<Resource> getMenusByRole(String roleId){
        List menus = publicFind("from Resource where id in (select resourceId from RoleResource where roleId ='"
                +roleId+"') and isMenu='1' and parentId<>'0'");
        Collections.sort(menus, new Comparator<Resource>() {
            @Override
            public int compare(Resource o1, Resource o2) {
                return o1.getShowOrder().compareTo(o2.getShowOrder());
            }
        });
        return menus;
    }

    public List<Resource> getMenusByUsername(String username){
        Person person = personService.findPersonByName(username);
        List roles = publicFind("select roleid from UserRole where userid = '"+person.getId()+"'");
        List<Resource> resources = findInCollectionByPage("from Resource where id in (select resourceId from RoleResource where roleId in (:paraList)) order by showOrder asc",roles,-1,0);
        return resources;
    }

    //通过idList更新角色资源关系
    public void txSetRoleResource(String id,String idList,boolean isMenu){
        String[] ids = idList.split(",");
        String resourceType;
        if(isMenu){
            resourceType = "menu";
            executeQuery("delete from RoleResource where roleId='"+id+"' and name = 'menu'");
            for(String item:ids){
                RoleResource rolemenu = new RoleResource();
                rolemenu.setName(resourceType);
                rolemenu.setId(Utils.getNewUUID());
                rolemenu.setCreatetime(Utils.getNow());
                rolemenu.setResourceId(item);
                rolemenu.setRoleId(id);
                getSession().save(rolemenu);
            }
        }
        else{
            resourceType = "resource";
            executeQuery("delete from RoleResource where resourceId='"+id+"'");
            for(String item:ids){
                RoleResource rolemenu = new RoleResource();
                rolemenu.setName(resourceType);
                rolemenu.setId(Utils.getNewUUID());
                rolemenu.setCreatetime(Utils.getNow());
                rolemenu.setRoleId(item);
                rolemenu.setResourceId(id);
                getSession().save(rolemenu);
            }
        }

    }

    public List<Resource> getAllResource(){
        Map<String,Object> params = new HashMap<>();
        params.put("url <>","");
        params.put("isMenu <>",Byte.valueOf("1"));

        return findByMapWithCond(params,"showOrder",true);
    }

    public List<Resource> getAllMenus(){
        Map<String,Object> params = new HashMap<>();
        params.put("isMenu",Byte.valueOf("1"));
        return findByMap("from Resource where isMenu=:isMenu order by showOrder asc",params);
    }

    public void txDelMenuItem(String idList) {
        String[] ids = idList.split(",");
        for(String id:ids){
            executeQuery("delete from RoleResource where resourceId='"+id+"'");
        }
        delete(idList);
    }

    public String getRolesByResource(String resourceId){
        List rolesList = publicFind("from RoleResource where resourceId='"+resourceId+"'");
        StringBuilder builder = new StringBuilder();
        for(Object item:rolesList)
            builder.append(((RoleResource)item).getRoleId()).append(",");
        return builder.toString();
    }

    //直接添加一条角色资源关系
    public void txAddRoleResource(String roleId,String resourceId,String resourceType){
        RoleResource roleResource = new RoleResource();
        roleResource.setResourceId(resourceId);
        roleResource.setRoleId(roleId);
        roleResource.setId(Utils.getNewUUID());
        roleResource.setCreatetime(Utils.getNow());
        roleResource.setName(resourceType);
        getSession().save(roleResource);
    }
}
