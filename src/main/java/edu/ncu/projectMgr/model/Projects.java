package edu.ncu.projectMgr.model;

import com.wansan.template.model.BasePojo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2014/9/24.
 */
@Entity
@Table(name = "projects")
public class Projects extends BasePojo{

    private String operatorId;
    private String registerId;
    private String projYear;
    private String depart;
    private String owner;
    private BigDecimal fee;
    private Integer projectType;
    private Integer departType;
    private String partyMember;

    @Basic
    @Column(name = "operatorID", nullable = true, insertable = true, updatable = true, length = 36)
    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    @Basic
    @Column(name = "registerID", nullable = true, insertable = true, updatable = true, length = 9)
    public String getRegisterId() {
        return registerId;
    }

    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    @Basic
    @Column(name = "projYear", nullable = true, insertable = true, updatable = true, length = 4)
    public String getProjYear() {
        return projYear;
    }

    public void setProjYear(String projYear) {
        this.projYear = projYear;
    }

    @Basic
    @Column(name = "depart", nullable = true, insertable = true, updatable = true, length = 100)
    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    @Basic
    @Column(name = "owner", nullable = true, insertable = true, updatable = true, length = 20)
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Basic
    @Column(name = "fee", nullable = true, insertable = true, updatable = true, precision = 2)
    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    @Basic
    @Column(name = "projectType", nullable = true, insertable = true, updatable = true)
    public Integer getProjectType() {
        return projectType;
    }

    public void setProjectType(Integer projectType) {
        this.projectType = projectType;
    }

    @Basic
    @Column(name = "departType", nullable = true, insertable = true, updatable = true)
    public Integer getDepartType() {
        return departType;
    }

    public void setDepartType(Integer departType) {
        this.departType = departType;
    }

    @Basic
    @Column(name = "partyMember", nullable = true, insertable = true, updatable = true, length = 200)
    public String getPartyMember() {
        return partyMember;
    }

    public void setPartyMember(String partyMember) {
        this.partyMember = partyMember;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Projects projects = (Projects) o;

        if (comment != null ? !comment.equals(projects.comment) : projects.comment != null) return false;
        if (createtime != null ? !createtime.equals(projects.createtime) : projects.createtime != null) return false;
        if (depart != null ? !depart.equals(projects.depart) : projects.depart != null) return false;
        if (departType != null ? !departType.equals(projects.departType) : projects.departType != null) return false;
        if (fee != null ? !fee.equals(projects.fee) : projects.fee != null) return false;
        if (id != null ? !id.equals(projects.id) : projects.id != null) return false;
        if (name != null ? !name.equals(projects.name) : projects.name != null) return false;
        if (operatorId != null ? !operatorId.equals(projects.operatorId) : projects.operatorId != null) return false;
        if (owner != null ? !owner.equals(projects.owner) : projects.owner != null) return false;
        if (partyMember != null ? !partyMember.equals(projects.partyMember) : projects.partyMember != null)
            return false;
        if (projYear != null ? !projYear.equals(projects.projYear) : projects.projYear != null) return false;
        if (projectType != null ? !projectType.equals(projects.projectType) : projects.projectType != null)
            return false;
        if (registerId != null ? !registerId.equals(projects.registerId) : projects.registerId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (createtime != null ? createtime.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (operatorId != null ? operatorId.hashCode() : 0);
        result = 31 * result + (registerId != null ? registerId.hashCode() : 0);
        result = 31 * result + (projYear != null ? projYear.hashCode() : 0);
        result = 31 * result + (depart != null ? depart.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (fee != null ? fee.hashCode() : 0);
        result = 31 * result + (projectType != null ? projectType.hashCode() : 0);
        result = 31 * result + (departType != null ? departType.hashCode() : 0);
        result = 31 * result + (partyMember != null ? partyMember.hashCode() : 0);
        return result;
    }
}
