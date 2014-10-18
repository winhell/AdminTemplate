package com.wansan.template.core;

import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by Administrator on 2014/9/3.
 */
public class CustomTimestampEditor extends PropertyEditorSupport {
    private DateFormat dateFormat;
    private Boolean allowEmpty;

    public CustomTimestampEditor(DateFormat dateFormat,Boolean allowEmpty){
        this.dateFormat = dateFormat;
        this.allowEmpty = allowEmpty;
    }

    @Override
    public void setAsText(String text){
        if(allowEmpty&&!StringUtils.hasText(text)){
            setValue(null);
        }else {
            try {
                Date date = dateFormat.parse(text);
                setValue(new Timestamp(date.getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
                setValue(null);
            }
        }
    }

    @Override
    public String getAsText(){
        return dateFormat.format((Timestamp)getValue());
    }
}
