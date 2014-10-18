package com.wansan.template.test;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 14-4-16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml","classpath:dispatcher-servlet.xml",
        "classpath:spring-security.xml"})
public abstract class BaseMVCTest {
    @Autowired
    protected WebApplicationContext wac;

    protected static MockMvc mockMvc;
    protected static HttpServletResponse response;
    protected static HttpServletRequest request;
    protected static HttpSession session;

    @BeforeClass
    public static void evnSetup(){
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session = request.getSession();
    }
}
