package com.wansan.template.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wansan.template.core.CustomTimestampEditor;
import com.wansan.template.model.Person;
import com.wansan.template.model.ResultEnum;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 14-4-30.
 */
public class BaseController {

    private static ObjectMapper mapper=null;

    protected Person getLoginPerson(){
        Person oper=null;
        Object principal =  SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            oper = (Person)principal;
        }
        return oper;
    }

    protected Map<String,Object> returnMap(boolean success,String message){
        Map<String,Object> result = new HashMap<>();
        if(success){
            result.put("status",ResultEnum.SUCCESS);
            result.put("msg",message);
        }else {
            result.put("status",ResultEnum.FAIL);
            result.put("msg",message);
        }
        return result;
    }

    protected Map<String,Object> result(boolean success){
        Map<String,Object> result = new HashMap<>();
        if(success){
            result.put("status",ResultEnum.SUCCESS);
            result.put("msg","操作成功！");
        }else {
            result.put("status",ResultEnum.FAIL);
            result.put("msg","操作失败");
        }
        return result;
    }

    protected String jsonpResult(String callback,Object object,Boolean isShort) throws JsonProcessingException {
        StringBuilder sb = new StringBuilder();
        if(null == mapper){
            mapper = new ObjectMapper();
        }
        if(isShort)
            mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        else
            mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        if(StringUtils.hasText(callback)){
            sb.append(callback).append("(");
        }else
            sb.append("jsonpCallback(");
        sb.append(mapper.writeValueAsString(object)).append(");");
        return sb.toString();
    }

    protected String jsonpResult(Object object) throws JsonProcessingException {
        return jsonpResult("",object,false);
    }

    @InitBinder
    protected void initBinder(HttpServletRequest request,ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        CustomDateEditor dateEditor = new CustomDateEditor(df, true);
//        binder.registerCustomEditor(Date.class, dateEditor);
        CustomTimestampEditor timeEditor = new CustomTimestampEditor(df,true);
        binder.registerCustomEditor(Timestamp.class,timeEditor);
    }
}
