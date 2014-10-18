package com.wansan.template.service;

import com.wansan.template.test.BaseTest;
import junit.framework.Assert;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by Administrator on 14-4-28.
 */
public class ResourceServiceTest extends BaseTest{
    @Resource
    private IResourceService resourceService;

    @Test
    public void testGetMenusByUsername() throws Exception {

    }
}
