package com.wansan.template.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 14-4-26.
 */

@Controller
public class indexAction extends BaseController{
    @RequestMapping(value = "/index")
    public String index(){
        return "redirect:/mainpage/index.html";
    }

    @RequestMapping(value = "/getLoginError")
    public @ResponseBody
    Map<String,String> getError(HttpSession session){
        Map<String,String> result = new HashMap<>();
        Exception e = (Exception)session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        result.put("msg",e.getMessage());
        return result;
    }

    @RequestMapping(value = "/mainpage/getLoginUser")
    public @ResponseBody String getUsername(){
        return getLoginPerson().getName();
    }
}
