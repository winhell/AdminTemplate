package com.wansan.template.controller;

import com.wansan.template.model.Person;
import com.wansan.template.model.ResultEnum;
import com.wansan.template.service.IPersonService;
import com.wansan.template.service.IResourceService;
import org.hibernate.HibernateException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 14-4-28.
 */
@Controller
@ResponseBody
@RequestMapping(value = "/mainpage/system")
public class MenuController extends BaseController{

    @Resource
    private IResourceService resourceService;

    @RequestMapping(value = "/getUserMenus")

    public List<com.wansan.template.model.Resource> getMenus(){
        Object principal =  SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            String username = ((UserDetails)principal).getUsername();
            return resourceService.getMenusByUsername(username);
        }
        return null;
    }

    @RequestMapping(value = "/getMenus")
    public List<com.wansan.template.model.Resource> getMenus(HttpServletRequest request){
        String id = request.getParameter("id");
        List<com.wansan.template.model.Resource> tree;
        if(id==null||"".equals(id))
            tree = resourceService.getParent();
        else
            tree = resourceService.getChildren(id);
        return tree;
    }


    @RequestMapping(value = "/getMenuTree")
    public List<Map<String,String>> getMenuTree(String id)
    {

        List<com.wansan.template.model.Resource> menuList = resourceService.getAllMenus();
        List<Map<String,String>> result = new ArrayList<>();
        for(com.wansan.template.model.Resource menuItem:menuList){
            Map<String,String> newItem = new HashMap<>();
            newItem.put("id",menuItem.getId());
            newItem.put("parent",menuItem.getParentId().equals("0")?"#":menuItem.getParentId());
            newItem.put("text",menuItem.getName());
            newItem.put("icon","fa fa-"+menuItem.getIcon());
            result.add(newItem);
        }
        return result;
    }

    @RequestMapping(value = "/listmenus")
    public Map<String,Object> getAllMenus(){
        Map<String,Object> result = new HashMap<>();
        result.put("data",resourceService.getAllMenus());
        result.put("status",ResultEnum.SUCCESS);
        return result;
    }

    @RequestMapping(value = "/addmenu")
    public Map add(com.wansan.template.model.Resource menu){
        Map<String,Object> result = new HashMap<>();
        byte isMenu = 1;
        menu.setIsMenu(isMenu);
        Person oper = getLoginPerson();
        try{
            if(null==menu.getParentId()||"".equals(menu.getParentId())){
                menu.setParentId("0");
                menu.setState("closed");
            }
            else menu.setState("open");
            String newMenuID = (String) resourceService.txSave(menu,oper);
            resourceService.txAddRoleResource("super",newMenuID,"menu");   //新加菜单直接对超级用户授权
            result.put("status", ResultEnum.SUCCESS);
            result.put("msg","菜单添加完成！");
        }catch (HibernateException e){
            result.put("status",ResultEnum.FAIL);
            result.put("msg","菜单添加失败！");
        }
        return result;
    }

    @RequestMapping(value = "/updatemenu")
    public Map update(com.wansan.template.model.Resource menu){
        Person oper = getLoginPerson();
        Map<String,Object> result = new HashMap<>();
        try{
            byte isMenu =1;
            menu.setIsMenu(isMenu);
            resourceService.txUpdate(menu,oper);
            result.put("status",ResultEnum.SUCCESS);
            result.put("msg","菜单项更新成功！");
        }catch (Exception e){
            result.put("status",ResultEnum.FAIL);
            result.put("msg","菜单项更新失败："+e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/deletemenu")
    public Map delete(String idList){
        Person oper = getLoginPerson();
        resourceService.txDelete(idList,oper);
        Map<String,Object> result = new HashMap<>();
        result.put("status",ResultEnum.SUCCESS);
        result.put("msg","菜单项已删除！");
        return result;
    }

    @RequestMapping(value = "/setRoleMenu")
    public Map setMenu(String idList,String roleId){
        resourceService.txSetRoleResource(roleId,idList,true);
        Map<String,Object> result = new HashMap<>();
        result.put("status",ResultEnum.SUCCESS);
        result.put("msg","菜单设置完成！");
        return result;
    }

    @RequestMapping(value = "/getMenusByRole")
    public String getMenusByRole(String roleId){
        List<com.wansan.template.model.Resource> resources = resourceService.getMenusByRole(roleId);
        StringBuilder sb = new StringBuilder();
        for(com.wansan.template.model.Resource item:resources){
            sb.append(item.getId());
            sb.append(",");
        }
        return sb.toString();
    }
}
