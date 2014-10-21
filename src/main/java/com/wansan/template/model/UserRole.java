package com.wansan.template.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Administrator on 14-4-15.
 */
@Entity
@Table(name = "user_role")
public class UserRole extends BasePojo{

    private String userid;
    private String roleid;


    @Basic
    @Column(name = "userid")
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Basic
    @Column(name = "roleid")
    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRole userRole = (UserRole) o;

        if (comment != null ? !comment.equals(userRole.comment) : userRole.comment != null) return false;
        if (createtime != null ? !createtime.equals(userRole.createtime) : userRole.createtime != null) return false;
        if (id != null ? !id.equals(userRole.id) : userRole.id != null) return false;
        if (name != null ? !name.equals(userRole.name) : userRole.name != null) return false;
        if (roleid != null ? !roleid.equals(userRole.roleid) : userRole.roleid != null) return false;
        if (userid != null ? !userid.equals(userRole.userid) : userRole.userid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (createtime != null ? createtime.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (userid != null ? userid.hashCode() : 0);
        result = 31 * result + (roleid != null ? roleid.hashCode() : 0);
        return result;
    }
}
