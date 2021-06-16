package com.lft.rbac.mapper;

import com.lft.rbac.entity.Permission;
import com.lft.rbac.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionMapper {
    
    Permission selectRootPermission();
    
    List<Permission> selectChildPermissions(@Param ("pid") Long pid);
    
    List<Permission> selectAllPermissions();
    
    Integer insertPermission(Permission permission);
    
    Permission selectPermissionById(@Param ("id") Long id);
    
    Integer updatePermission(Permission permission);
    
    Boolean deletePermissionById(@Param ("id") Long id);
    
    List<Long> queryPermissionIdByRoleId(Long roleId);
    
    List<Permission> queryPermissionByUser(User userFromDb);
}
