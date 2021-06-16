package com.lft.rbac.entity;

import java.io.Serializable;

/**
 * @author Administrator
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String username;
    private String loginacct;
    private String userpwd;
    private String email;
    private String gmtCreated;
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getLoginacct() {
        return loginacct;
    }
    
    public void setLoginacct(String loginacct) {
        this.loginacct = loginacct;
    }
    
    public String getUserpwd() {
        return userpwd;
    }
    
    public void setUserpwd(String userpwd) {
        this.userpwd = userpwd;
    }
    
    public String getGmtCreated() {
        return gmtCreated;
    }
    
    public void setGmtCreated(String gmtCreated) {
        this.gmtCreated = gmtCreated;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", loginacct='" + loginacct + '\'' +
                ", userpwd='" + userpwd + '\'' +
                ", email='" + email + '\'' +
                ", gmtCreated=" + gmtCreated +
                '}';
    }
}
