package com.wansan.template.controller;

import com.wansan.template.model.Person;
import com.wansan.template.model.ResultEnum;
import com.wansan.template.service.IPersonService;
import com.wansan.template.service.IRoleService;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 14-4-29.
 */
@Controller
@RequestMapping(value = "/mainpage/system")
@ResponseBody
public class UserController extends BaseController{

    private Logger logger = Logger.getLogger(this.getClass());
    @Resource
    private IPersonService personService;

    @Resource
    private IRoleService roleService;

    @RequestMapping(value = "/listuser")
    public Map getUserList(){
        Map<String,Object> result = new HashMap<>();
        result.put("data",personService.getUserList(getLoginPerson()));
        result.put("status",ResultEnum.SUCCESS);
        return result;
    }

    @RequestMapping(value = "/adduser")
    public Map add(Person person){
        Map<String,Object> result = new HashMap<>();
        Person oper = getLoginPerson();
        try{
            personService.txSave(person,oper);
            result.put("status", ResultEnum.SUCCESS);
            result.put("msg","用户添加成功！");
        }catch (Exception e){
            result.put("status",ResultEnum.FAIL);
            result.put("msg","用户添加失败！");
            logger.error("用户添加失败！");
            logger.error(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/deleteuser")
    public Map delete(String idList){
        Map<String,Object> result = new HashMap<>();
        Person oper = getLoginPerson();
        try{
            personService.txDelete(idList,oper);
            result.put("status",ResultEnum.SUCCESS);
            result.put("msg","用户删除成功！");
        }catch (HibernateException e){
            result.put("status",ResultEnum.FAIL);
            result.put("msg","用户删除失败！");
            logger.error(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/updateuser")
    public Map update(Person person){
        Map<String,Object> result = new HashMap<>();
        Person oper = getLoginPerson();
        try {
            personService.txUpdate(person,oper,true );
            result.put("status",ResultEnum.SUCCESS);
            result.put("msg","用户修改成功！");
        }catch (HibernateException e){
            result.put("status",ResultEnum.FAIL);
            result.put("msg","修改用户失败！");
            logger.error(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/getUserRoles")
    public String getRoles(String userID){
        return roleService.getRolesByUserID(userID);
    }

    @RequestMapping(value = "/setUserRoles")
    public Map<String,Object> setRoles(String userID,String idList){
        Map<String,Object> result = new HashMap<>();
        try {
            roleService.txSetRolesByUserID(userID,idList);
            result.put("status",ResultEnum.SUCCESS);
            result.put("msg","分配角色成功！");
        }catch (Exception e){
            result.put("status",ResultEnum.FAIL);
            result.put("msg","分配角色失败！");
            logger.error(e.getMessage());
        }
        return result;
    }
}
