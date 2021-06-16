package com.lft.rbac.service.impl;

import com.lft.rbac.entity.User;
import com.lft.rbac.mapper.UserMapper;
import com.lft.rbac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public List<User> queryAllUsers() {
        return userMapper.selectAllUsers();
    }
    
    @Override
    public User query4Login(User user) {
        return userMapper.selectUser4Login(user);
    }
    
    @Override
    public List<User> queryDataPage(Map<String, Object> map) {
        return userMapper.selectDatePage(map);
    }
    
    @Override
    public Integer queryUsersCount(Map<String, Object> map) {
        return userMapper.selectUsersCount(map);
    }
    
    @Override
    public Integer insertUser(User user) {
        return userMapper.insert(user);
    }
    
    @Override
    public User queryUserById(Long userId) {
        return userMapper.selectUserById(userId);
    }
    
    @Override
    public Integer updateUser(User user) {
        return userMapper.update(user);
    }
    
    @Override
    public Boolean deleteUserById(Long userId) {
        return userMapper.delete(userId);
    }
    
    @Override
    public Boolean batchDeleteUsersByIds(Map<String, Object> map) {
        return userMapper.batchDelete(map);
    }
    
    @Override
    public Integer insertUserRoles(Map<String, Object> map) {
        map.put("gmtCreated", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
        return userMapper.insertUserRoles(map);
    }
    
    @Override
    public Integer deleteUserRoles(Map<String, Object> map) {
        return userMapper.deleteUserRoles(map);
    }
    
    @Override
    public List<Long> queryRolesByUserId(Long id) {
        return userMapper.queryRolesByUserId(id);
    }
}
