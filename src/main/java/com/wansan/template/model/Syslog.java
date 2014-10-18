package com.wansan.template.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Administrator on 14-5-7.
 */
@Entity
@Table(name = "syslog")
public class Syslog extends BasePojo{

    private String userid;

    @Basic
    @Column(name = "userid")
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Syslog syslog = (Syslog) o;

        if (comment != null ? !comment.equals(syslog.comment) : syslog.comment != null) return false;
        if (createtime != null ? !createtime.equals(syslog.createtime) : syslog.createtime != null) return false;
        if (id != null ? !id.equals(syslog.id) : syslog.id != null) return false;
        if (name != null ? !name.equals(syslog.name) : syslog.name != null) return false;
        if (userid != null ? !userid.equals(syslog.userid) : syslog.userid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (createtime != null ? createtime.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (userid != null ? userid.hashCode() : 0);
        return result;
    }
}
