package com.wansan.template.controller;

import com.wansan.template.test.BaseMVCTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.Resource;

/**
 * Created by Administrator on 14-4-16.
 */
public class HelloWorldTest extends BaseMVCTest{

    Log log = LogFactory.getLog(this.getClass());

    @Resource
    private HelloWorld helloWorld;

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(helloWorld).build();
    }

    @Test
    public void testHelloWorld() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/hello.action"));
        MvcResult mvcResult = resultActions.andReturn();
        log.info(mvcResult.getResponse().getContentAsString());
    }
}
