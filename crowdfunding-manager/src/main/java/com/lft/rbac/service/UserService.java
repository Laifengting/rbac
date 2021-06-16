package com.lft.rbac.service;

import com.lft.rbac.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<User> queryAllUsers();
    
    User query4Login(User user);
    
    List<User> queryDataPage(Map<String, Object> map);
    
    Integer queryUsersCount(Map<String, Object> map);
    
    Integer insertUser(User user);
    
    User queryUserById(Long userId);
    
    Integer updateUser(User user);
    
    Boolean deleteUserById(Long userId);
    
    Boolean batchDeleteUsersByIds(Map<String, Object> map);
    
    Integer insertUserRoles(Map<String, Object> map);
    
    Integer deleteUserRoles(Map<String, Object> map);
    
    List<Long> queryRolesByUserId(Long id);
}
