package com.wansan.template.service;

import com.wansan.template.model.CodeEnum;
import com.wansan.template.model.Systemcode;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 14-7-13.
 */
public interface ISystemcodeService extends IBaseDao<Systemcode> {

    public Map<String,String> getCodetypeList();
    public String getCodeString(CodeEnum type,int code);
    public int getCode(CodeEnum type,String codeName);
    public Map<String,String> getCodeMap(CodeEnum type);
    public List<Systemcode> getCodeList(CodeEnum type);
}
