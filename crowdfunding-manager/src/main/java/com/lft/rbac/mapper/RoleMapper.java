package com.lft.rbac.mapper;

import com.lft.rbac.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RoleMapper {
    
    List<Role> selectDatePage(Map<String, Object> map);
    
    Integer selectRolesCount(Map<String, Object> map);
    
    Integer insert(Role role);
    
    Role selectRoleById(@Param ("id") Long roleId);
    
    Integer updateRole(Role role);
    
    Boolean deleteById(@Param ("id") Long roleId);
    
    Boolean batchDeleteByIds(Map<String, Object> map);
    
    List<Role> selectAllRoles();
    
    Integer doAssign(Map<String, Object> map);
    
    void clearOld(Map<String, Object> map);
}

