package com.lft.rbac.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Permission implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String name;
    private String url;
    private Long pid;
    private String gmtCreated;
    
    private String icon;
    private boolean open = true;
    private boolean checked = false;
    private List<Permission> children = new ArrayList<>();
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public boolean isChecked() {
        return checked;
    }
    
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public Long getPid() {
        return pid;
    }
    
    public String getIcon() {
        return icon;
    }
    
    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    public void setPid(Long pid) {
        this.pid = pid;
    }
    
    public String getGmtCreated() {
        return gmtCreated;
    }
    
    public void setGmtCreated(String gmtCreated) {
        this.gmtCreated = gmtCreated;
    }
    
    public boolean isOpen() {
        return open;
    }
    
    public void setOpen(boolean open) {
        this.open = open;
    }
    
    public List<Permission> getChildren() {
        return children;
    }
    
    public void setChildren(List<Permission> children) {
        this.children = children;
    }
}
