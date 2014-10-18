package com.wansan.template.service;

import junit.framework.Assert;
import org.junit.Test;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

/**
 * Created by Administrator on 14-4-26.
 */
public class MyInvocationSecurityMetadataSourceServiceTest {
    @Test
    public void testSupports() throws Exception {
        PathMatcher matcher = new AntPathMatcher();
        String url = "/jsp/aaa.action";
        String matchUrl = "/jsp/*.action";
        Assert.assertTrue("Should be match",matcher.match(matchUrl,url));
    }
}
