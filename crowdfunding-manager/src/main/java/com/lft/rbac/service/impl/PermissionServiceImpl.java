package com.lft.rbac.service.impl;

import com.lft.rbac.entity.Permission;
import com.lft.rbac.entity.User;
import com.lft.rbac.mapper.PermissionMapper;
import com.lft.rbac.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {
    
    @Autowired
    private PermissionMapper permissionMapper;
    
    @Override
    public Permission queryRootPermission() {
        return permissionMapper.selectRootPermission();
    }
    
    @Override
    public List<Permission> queryChildPermissions(Long pid) {
        return permissionMapper.selectChildPermissions(pid);
    }
    
    @Override
    public List<Permission> queryAllPermissions() {
        return permissionMapper.selectAllPermissions();
    }
    
    @Override
    public Integer insertPermission(Permission permission) {
        permission.setGmtCreated(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
        permission.setIcon("default");
        return permissionMapper.insertPermission(permission);
    }
    
    @Override
    public Permission queryPermissionById(Long id) {
        return permissionMapper.selectPermissionById(id);
    }
    
    @Override
    public Integer updatePermission(Permission permission) {
        return permissionMapper.updatePermission(permission);
    }
    
    @Override
    public Boolean deletePermissionById(Long id) {
        return permissionMapper.deletePermissionById(id);
    }
    
    @Override
    public List<Long> queryPermissionIdByRoleId(Long roleId) {
        return permissionMapper.queryPermissionIdByRoleId(roleId);
    }
    
    @Override
    public List<Permission> queryPermissionByUser(User userFromDb) {
        // 用户有哪些角色
        // 角色有哪些许可
        return permissionMapper.queryPermissionByUser(userFromDb);
    }
}
