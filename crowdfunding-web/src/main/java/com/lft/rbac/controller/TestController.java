package com.lft.rbac.controller;

import com.lft.rbac.entity.User;
import com.lft.rbac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Laifengting
 */
@Controller
@RequestMapping ("test")
public class TestController {
    
    @Autowired
    private UserService userService;
    
    @RequestMapping ("/index")
    public String index() {
        return "index";
    }
    
    @ResponseBody
    @RequestMapping ("json")
    public Object json() {
        
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Tom");
        map.put("age", 20);
        
        return map;
    }
    
    @ResponseBody
    @RequestMapping ("users")
    public Object doQuery() {
        List<User> users = userService.queryAllUsers();
        return users;
    }
}
