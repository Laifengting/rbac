package com.lft.rbac.mapper;

import com.lft.rbac.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    List<User> selectAllUsers();
    
    User selectUser4Login(User user);
    
    List<User> selectDatePage(Map<String, Object> map);
    
    Integer selectUsersCount(Map<String, Object> map);
    
    Integer insert(User user);
    
    User selectUserById(@Param ("id") Long userId);
    
    Integer update(User user);
    
    Boolean delete(@Param ("id") Long userId);
    
    Boolean batchDelete(Map<String, Object> map);
    
    Integer insertUserRoles(Map<String, Object> map);
    
    Integer deleteUserRoles(Map<String, Object> map);
    
    List<Long> queryRolesByUserId(@Param ("id") Long userId);
}
