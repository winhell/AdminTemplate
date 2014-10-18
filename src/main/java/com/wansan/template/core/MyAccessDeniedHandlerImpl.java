package com.wansan.template.core;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 14-4-23.
 */
public class MyAccessDeniedHandlerImpl implements AccessDeniedHandler {


    public MyAccessDeniedHandlerImpl()
    {

    }
    public String getAccessDeniedUrl() {
        return accessDeniedUrl;
    }


    public void setAccessDeniedUrl(String accessDeniedUrl) {
        this.accessDeniedUrl = accessDeniedUrl;
    }

    public MyAccessDeniedHandlerImpl(String accessDeniedUrl)
    {
        this.accessDeniedUrl=accessDeniedUrl;
    }
    private String accessDeniedUrl;


    @Override
    public void handle(HttpServletRequest req,
                       HttpServletResponse resp, AccessDeniedException reason) throws ServletException,
            IOException {
        boolean isAjax = "XMLHttpRequest".equals(req.getHeader("X-Requested-With"));

//如果是ajax请求
        if (isAjax) {

            String jsonObject = "{\"msg\":\"没有足够的权限执行此功能！\","+
                          "\"access-denied\":true,\"cause\":\"AUTHORIZATION_FAILURE\",\"status\":\"FAIL\"}";
                    String contentType = "application/json";
            resp.setContentType(contentType);
            PrintWriter out = resp.getWriter();
            out.print(jsonObject);
            out.flush();
            out.close();
            return;
        }
        else
        {

            String path = req.getContextPath();
            String basePath = req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+path+"/";
            resp.sendRedirect(basePath+accessDeniedUrl);
        }
    }
}