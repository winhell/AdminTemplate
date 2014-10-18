package com.wansan.template.core;

import com.wansan.template.model.CodeEnum;

import java.beans.PropertyEditorSupport;

/**
 * Created by Administrator on 14-7-13.
 */
public class CodeEnumEditor extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        int val = Integer.parseInt(text);
        boolean found = false;

        for (CodeEnum d : CodeEnum.values()) {
            if (val == d.ordinal()) {
                this.setValue(d);
                found = true;
                break;
            }

        }

        if (!found)
            this.setValue(CodeEnum.sex);
    }
}
