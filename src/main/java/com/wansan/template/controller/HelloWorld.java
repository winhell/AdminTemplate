package com.wansan.template.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 14-4-13.
 */
@Controller
public class HelloWorld {

    @RequestMapping(value = "/hello")
    public ModelAndView helloWorld(){
        ModelAndView mv = new ModelAndView();
        mv.addObject("message","hwh say hello!");
        mv.setViewName("myfirst");
        return mv;
    }

    @RequestMapping(value = "/springUpload")
    public String upload(HttpServletRequest request) throws IOException {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if(resolver.isMultipart(request)){
            MultipartRequest multipartRequest = (MultipartRequest)request;
//            MultipartFile file = multipartRequest.getFile("myfile");
            String name = request.getParameter("username");
//            file.transferTo(new File("myfile"));
            request.setAttribute("message",name);
        }
        return "myfirst";
    }

    @RequestMapping(value = "/gotoForm")
    public String toTestForm(){
        return "testForm";
    }
}
