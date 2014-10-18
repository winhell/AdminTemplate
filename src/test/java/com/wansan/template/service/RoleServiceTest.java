package com.wansan.template.service;

import com.wansan.template.model.Person;
import com.wansan.template.test.BaseTest;
import junit.framework.Assert;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by Administrator on 14-4-16.
 */
public class RoleServiceTest extends BaseTest{

    @Resource
    IRoleService roleService;

    @Resource
    IPersonService personService;

    @Test
    public void testGetRolesByUser() throws Exception {

    }
}
