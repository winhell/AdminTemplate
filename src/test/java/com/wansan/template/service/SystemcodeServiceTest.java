package com.wansan.template.service;

import com.wansan.template.model.CodeEnum;
import com.wansan.template.model.Systemcode;
import com.wansan.template.test.BaseTest;
import junit.framework.Assert;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by Administrator on 14-7-13.
 */
public class SystemcodeServiceTest extends BaseTest{

    @Resource
    private ISystemcodeService systemcodeService;
    @Test
    public void testGetCodeList() throws Exception {
        CodeEnum type = CodeEnum.sex;
        Assert.assertEquals(2,systemcodeService.getCodeList(type).size());
    }

}
