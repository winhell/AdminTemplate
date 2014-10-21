package com.wansan.template.controller;

import com.wansan.template.model.Person;
import com.wansan.template.model.ResultEnum;
import com.wansan.template.model.Role;
import com.wansan.template.service.IPersonService;
import com.wansan.template.service.IResourceService;
import com.wansan.template.service.IRoleService;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 14-4-30.
 */
@Controller
@RequestMapping(value = "/mainpage/system")
public class RoleController extends BaseController {
    private Logger logger = Logger.getLogger(this.getClass());
    @Resource
    private IRoleService roleService;

    @Resource
    private IResourceService resourceService;

    @RequestMapping(value = "/getAllRoles")
    @ResponseBody
    public Map getRoles(){
        List<Role> roles =  roleService.getRoleList(getLoginPerson());
        Map<String,Object> result = new HashMap<>();
        result.put("status",ResultEnum.SUCCESS);
        result.put("data",roles);
        return result;
    }

    @RequestMapping(value = "/addrole")
    @ResponseBody
    public Map add(Role role){
        Map<String,Object> result = new HashMap<>();
        Person oper = getLoginPerson();
        try{
            roleService.txSave(role,oper);
            result.put("status", ResultEnum.SUCCESS);
            result.put("msg","成功添加角色！");
        }catch (HibernateException e){
            result.put("status",ResultEnum.FAIL);
            result.put("msg","添加角色失败！");
            logger.error(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/deleterole")
    @ResponseBody
    public Map delete(String idList){
        Map<String,Object> result = new HashMap<>();
        Person oper = getLoginPerson();
        try{
            roleService.txDelete(idList,oper);
            result.put("status",ResultEnum.SUCCESS);
            result.put("msg","角色删除成功！");
        }catch (HibernateException e){
            result.put("status",ResultEnum.FAIL);
            result.put("msg","角色删除失败！");
            logger.error(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/updaterole")
    @ResponseBody
    public Map update(Role role){
        Map<String,Object> result = new HashMap<>();
        Person oper = getLoginPerson();
        try {
            roleService.txUpdate(role,oper );
            result.put("status",ResultEnum.SUCCESS);
            result.put("msg","角色修改成功！");
        }catch (HibernateException e){
            result.put("status",ResultEnum.FAIL);
            result.put("msg","修改角色失败！");
            logger.error(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/mapRoleResources")
    public @ResponseBody Map mapResource(String idList,String roleId){
        resourceService.txSetRoleResource(roleId,idList,false);
        Map<String,Object> result = new HashMap<>();
        result.put("status",ResultEnum.SUCCESS);
        result.put("msg","菜单设置完成！");
        return result;
    }
}
