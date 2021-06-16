package com.lft.rbac.service;

import com.lft.rbac.entity.Permission;
import com.lft.rbac.entity.User;

import java.util.List;

public interface PermissionService {
    
    Permission queryRootPermission();
    
    List<Permission> queryChildPermissions(Long pid);
    
    List<Permission> queryAllPermissions();
    
    Integer insertPermission(Permission permission);
    
    Permission queryPermissionById(Long id);
    
    Integer updatePermission(Permission permission);
    
    Boolean deletePermissionById(Long id);
    
    List<Long> queryPermissionIdByRoleId(Long roleId);
    
    List<Permission> queryPermissionByUser(User userFromDb);
}
