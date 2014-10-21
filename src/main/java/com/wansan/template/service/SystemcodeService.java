package com.wansan.template.service;

import com.wansan.template.model.CodeEnum;
import com.wansan.template.model.Person;
import com.wansan.template.model.Systemcode;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 14-7-13.
 */
@Service
public class SystemcodeService extends BaseDao<Systemcode> implements ISystemcodeService {

    @Override
    public Map<String, String> getCodetypeList() {
        Map<String,String> result = new HashMap<>();
        for(CodeEnum c:CodeEnum.values()){
            result.put(c.name(),c.toString());
        }
        return result;
    }

    @Override
    public String getCodeString(CodeEnum type, int code) {
        Map<String,Object> params = new HashMap<>();
        params.put("type",type);
        params.put("code",code);
        List<Systemcode> list = (List<Systemcode>) findByMap(params,0,0,null,false).get("rows");
        if(list.size()>0)
        return list.get(0).getName();
        else
            return "未定义";
    }

    @Override
    public int getCode(CodeEnum type, String codeName) {
        Map<String,Object> params = new HashMap<>();
        params.put("type",type);
        params.put("name",codeName);
        List<Systemcode> list = (List<Systemcode>) findByMap(params,0,0,null,false).get("rows");
        if(list.size()>0)
            return list.get(0).getCode();
        return -1;
    }

    @Override
    public Serializable txSave(Systemcode systemcode,Person person){
        String hql = "select max(code) from Systemcode where type = :type";
        Query query = getSession().createQuery(hql);
        query.setParameter("type",systemcode.getType());
        Integer codeInsert = (Integer)query.list().get(0);
        systemcode.setCode(null==codeInsert?1:codeInsert+1);
        return save(systemcode);
    }

    @Override
    public Map<String, String> getCodeMap(CodeEnum type) {
        return null;
    }

    public List<Systemcode> getCodeList(CodeEnum type){
        return find("from Systemcode where type = ?",type);
    }
}
