package com.wansan.template.model;

import javax.persistence.*;

/**
 * Created by Administrator on 14-4-16.
 */
@Entity
@Table(name = "resource")
public class Resource extends BasePojo{
    private Byte isMenu;
    private Integer showOrder;
    private String parentId;
    private String state;
    private String icon;
    private String url;

    @Basic
    @Column(name = "isMenu")
    public Byte getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(Byte isMenu) {
        this.isMenu = isMenu;
    }

    @Basic
    @Column(name = "showOrder")
    public Integer getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
    }

    @Basic
    @Column(name = "parentId")
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Basic
    @Column(name = "state")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Basic
    @Column(name = "icon")
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Basic
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resource resource = (Resource) o;

        if (comment != null ? !comment.equals(resource.comment) : resource.comment != null) return false;
        if (createtime != null ? !createtime.equals(resource.createtime) : resource.createtime != null) return false;
        if (icon != null ? !icon.equals(resource.icon) : resource.icon != null) return false;
        if (id != null ? !id.equals(resource.id) : resource.id != null) return false;
        if (isMenu != null ? !isMenu.equals(resource.isMenu) : resource.isMenu != null) return false;
        if (name != null ? !name.equals(resource.name) : resource.name != null) return false;
        if (parentId != null ? !parentId.equals(resource.parentId) : resource.parentId != null) return false;
        if (showOrder != null ? !showOrder.equals(resource.showOrder) : resource.showOrder != null) return false;
        if (state != null ? !state.equals(resource.state) : resource.state != null) return false;
        if (url != null ? !url.equals(resource.url) : resource.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (createtime != null ? createtime.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (isMenu != null ? isMenu.hashCode() : 0);
        result = 31 * result + (showOrder != null ? showOrder.hashCode() : 0);
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (icon != null ? icon.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }
}
