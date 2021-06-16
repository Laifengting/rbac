package com.lft.rbac.service.impl;

import com.lft.rbac.entity.Role;
import com.lft.rbac.mapper.RoleMapper;
import com.lft.rbac.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {
    
    @Autowired
    private RoleMapper roleMapper;
    
    @Override
    public List<Role> queryDataPage(Map<String, Object> map) {
        return roleMapper.selectDatePage(map);
    }
    
    @Override
    public Integer queryRolesCount(Map<String, Object> map) {
        return roleMapper.selectRolesCount(map);
    }
    
    @Override
    public Integer insertRole(Role role) {
        return roleMapper.insert(role);
    }
    
    @Override
    public Role queryRoleById(Long roleId) {
        return roleMapper.selectRoleById(roleId);
    }
    
    @Override
    public Integer updateRole(Role role) {
        return roleMapper.updateRole(role);
    }
    
    @Override
    public Boolean deleteRoleById(Long id) {
        return roleMapper.deleteById(id);
    }
    
    @Override
    public Boolean batchDeleteRolesByIds(Map<String, Object> map) {
        return roleMapper.batchDeleteByIds(map);
    }
    
    @Override
    public List<Role> queryAllRoles() {
        return roleMapper.selectAllRoles();
    }
    
    @Override
    public Integer doAssign(Map<String, Object> map) {
        
        // 授权之前清空之前的所有数据
        roleMapper.clearOld(map);
        // 授权新的许可
        map.put("gmtCreated", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return roleMapper.doAssign(map);
    }
    
}
