package com.wansan.template.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Administrator on 14-7-13.
 */
@Entity
@Table(name = "systemcode")
public class Systemcode extends BasePojo{

    private CodeEnum type;
    private Integer code;

    @Basic
    @Enumerated()
    @Column(name = "type")
    public CodeEnum getType() {
        return type;
    }
    public void setType(CodeEnum type) {
        this.type = type;
    }

    @Basic
    @Column(name = "code")
    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Systemcode that = (Systemcode) o;

        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;
        if (createtime != null ? !createtime.equals(that.createtime) : that.createtime != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (createtime != null ? createtime.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        return result;
    }

    @Transient
    public String getTypename(){
        return type.toString();
    }

}
