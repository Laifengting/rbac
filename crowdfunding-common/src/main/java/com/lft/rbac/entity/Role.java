package com.lft.rbac.entity;

import java.io.Serializable;

public class Role implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String gmtCreated;
    
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getGmtCreated() {
        return gmtCreated;
    }
    
    public void setGmtCreated(String gmtCreated) {
        this.gmtCreated = gmtCreated;
    }
}
