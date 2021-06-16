package com.lft.rbac.controller;

import com.lft.rbac.entity.Page;
import com.lft.rbac.entity.Role;
import com.lft.rbac.entity.User;
import com.lft.rbac.service.RoleService;
import com.lft.rbac.service.UserService;
import com.lft.rbac.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping ("user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private RoleService roleService;
    
    /**
     * 用户首页
     * @return
     */
    @RequestMapping ("indexOld")
    public String indexOld(@RequestParam (required = false, defaultValue = "1") Integer pageNo, @RequestParam (required = false,
            defaultValue = "10") Integer pageSize, Model model) {
        // 分页查询
        // limit start,size
        Map<String, Object> map = new HashMap<>();
        map.put("start", (pageNo - 1) * pageSize);
        map.put("size", pageSize);
        
        model.addAttribute("curPage", pageNo);
        // 总数据条数
        Integer size = userService.queryUsersCount(map);
        
        model.addAttribute("size", size);
        // 最大页码（总页码数）
        int totalPage;
        if (size % pageSize == 0) {
            totalPage = size / pageSize;
        } else {
            totalPage = size / pageSize + 1;
        }
        model.addAttribute("totalPage", totalPage);
        
        List<User> userList = userService.queryDataPage(map);
        model.addAttribute("userList", userList);
        
        System.out.println(userList);
        return "user/index";
    }
    
    /**
     * 用户首页
     * @return
     */
    @RequestMapping ("index")
    public String index() {
        return "user/index";
    }
    
    /**
     * 分页查询
     */
    @ResponseBody
    @RequestMapping ("pageQuery")
    public R pageQuery(Integer pageNo, Integer pageSize, String queryText) {
        try {
            // 分页查询
            // limit start,size
            Map<String, Object> map = new HashMap<>();
            map.put("start", (pageNo - 1) * pageSize);
            map.put("size", pageSize);
            if (queryText != null && !"".equals(queryText)) {
                map.put("queryText", queryText);
            }
            
            // 查询出当前条件下的所有用户
            List<User> userList = userService.queryDataPage(map);
            // 总数据条数
            Integer size = userService.queryUsersCount(map);
            
            // 最大页码（总页码数）
            int totalPage;
            if (size % pageSize == 0) {
                totalPage = size / pageSize;
            } else {
                totalPage = size / pageSize + 1;
            }
            Page<User> page = new Page<>();
            page.setStartPage((pageNo - 1) * pageSize);
            page.setCurrentPage(pageNo);
            page.setSizeOfPage(pageSize);
            page.setTotalPages(totalPage);
            page.setData(userList);
            
            return R.OK().data("page", page);
        } catch (Exception e) {
            e.printStackTrace();
            return R.ERROR();
        }
    }
    
    /**
     * 跳转到添加用户页面
     */
    @RequestMapping ("add")
    public String addUser() {
        return "user/add";
    }
    
    /**
     * 插入用户
     */
    @ResponseBody
    @RequestMapping ("insert")
    public R insertUser(User user) {
        user.setUserpwd("123456");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        user.setGmtCreated(sdf.format(new Date()));
        try {
            Integer result = userService.insertUser(user);
            if (result != 1) {
                return R.ERROR();
            } else {
                return R.OK();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.ERROR();
        }
    }
    
    @RequestMapping ("edit")
    public String goEditPage(@RequestParam ("id") Long userId, Model model) {
        User user = userService.queryUserById(userId);
        model.addAttribute("user", user);
        return "user/edit";
    }
    
    /**
     * 插入用户
     */
    @ResponseBody
    @RequestMapping ("update")
    public R updateUser(User user) {
        try {
            Integer result = userService.updateUser(user);
            if (result != 1) {
                return R.ERROR();
            } else {
                return R.OK();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.ERROR();
        }
    }
    
    @ResponseBody
    @RequestMapping ("delete")
    public R deleteUserById(Long id) {
        try {
            Boolean result = userService.deleteUserById(id);
            if (!result) {
                return R.ERROR();
            } else {
                return R.OK();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.ERROR();
        }
    }
    
    @ResponseBody
    @RequestMapping ("batchDelete")
    public R deleteUserById(Integer[] userId) {
        Map<String, Object> map = new HashMap();
        map.put("ids", userId);
        try {
            Boolean result = userService.batchDeleteUsersByIds(map);
            if (!result) {
                return R.ERROR();
            } else {
                return R.OK();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.ERROR();
        }
    }
    
    @RequestMapping ("assign")
    public String assign(Long id, Model model) {
        User user = userService.queryUserById(id);
        model.addAttribute("user", user);
        List<Role> roleList = roleService.queryAllRoles();
        List<Role> assignedRoles = new ArrayList<>();
        List<Role> unassignedRoles = new ArrayList<>();
        
        // 获取用户角色关系表中的数据
        List<Long> roleIds = userService.queryRolesByUserId(id);
        for (Role role : roleList) {
            if (roleIds.contains(role.getId())) {
                assignedRoles.add(role);
            } else {
                unassignedRoles.add(role);
            }
        }
        
        model.addAttribute("assignedRoles", assignedRoles);
        model.addAttribute("unassignedRoles", unassignedRoles);
        
        return "user/assign";
    }
    
    @ResponseBody
    @RequestMapping ("doassign")
    public R doassign(Long userId, Integer[] unAssignedRoleIds) {
        try {
            // 增加关系表数据
            Map<String, Object> map = new HashMap<>();
            map.put("userId", userId);
            map.put("roleIds", unAssignedRoleIds);
            Integer result = userService.insertUserRoles(map);
            if (result == 0) {
                return R.ERROR();
            } else {
                return R.OK();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.ERROR();
        }
    }
    
    @ResponseBody
    @RequestMapping ("doUnassign")
    public R doUnassign(Long userId, Integer[] assignedRoleIds) {
        try {
            // 删除关系表数据
            Map<String, Object> map = new HashMap<>();
            map.put("userId", userId);
            map.put("roleIds", assignedRoleIds);
            Integer result = userService.deleteUserRoles(map);
            if (result == 0) {
                return R.ERROR();
            } else {
                return R.OK();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.ERROR();
        }
    }
    
}
