package com.lft.rbac.service;

import com.lft.rbac.entity.Role;

import java.util.List;
import java.util.Map;

public interface RoleService {
    
    List<Role> queryDataPage(Map<String, Object> map);
    
    Integer queryRolesCount(Map<String, Object> map);
    
    Integer insertRole(Role role);
    
    Role queryRoleById(Long roleId);
    
    Integer updateRole(Role role);
    
    Boolean deleteRoleById(Long id);
    
    Boolean batchDeleteRolesByIds(Map<String, Object> map);
    
    List<Role> queryAllRoles();
    
    Integer doAssign(Map<String, Object> map);
}
