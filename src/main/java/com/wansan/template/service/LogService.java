package com.wansan.template.service;

import com.wansan.template.model.Syslog;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 14-7-7.
 */
@Service
public class LogService extends BaseDao<Syslog> implements ILogService {
    public List<Syslog> search(Map<String,String> params) throws ParseException {
        Map<String,Object> newParams = new HashMap<>();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        if(!"".equals(params.get("fromDate"))){
            date = format.parse(params.get("fromDate"));
            newParams.put("createtime >=", new Timestamp(date.getTime()));
        }
        if(!"".equals(params.get("toDate"))){
            date = format.parse(params.get("toDate"));
            newParams.put("createtime <=", new Timestamp(date.getTime()));
        }
        if(!"".equals(params.get("name"))){
            newParams.put("name =", params.get("name"));
        }
        if(!"".equals(params.get("userid"))){
            newParams.put("userid =", params.get("userid"));
        }
        return findByMapWithCond(newParams,null,null);
    }
}
