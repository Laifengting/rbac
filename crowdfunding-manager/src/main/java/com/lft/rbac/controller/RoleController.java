package com.lft.rbac.controller;

import com.lft.rbac.entity.Page;
import com.lft.rbac.entity.Role;
import com.lft.rbac.service.RoleService;
import com.lft.rbac.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping ("role")
public class RoleController {
    
    @Autowired
    private RoleService roleService;
    
    /**
     * 角色首页
     * @return
     */
    @RequestMapping ("index")
    public String index() {
        return "role/index";
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
            List<Role> roleList = roleService.queryDataPage(map);
            // 总数据条数
            Integer size = roleService.queryRolesCount(map);
            
            // 最大页码（总页码数）
            int totalPage;
            if (size % pageSize == 0) {
                totalPage = size / pageSize;
            } else {
                totalPage = size / pageSize + 1;
            }
            Page<Role> page = new Page<>();
            page.setStartPage((pageNo - 1) * pageSize);
            page.setCurrentPage(pageNo);
            page.setSizeOfPage(pageSize);
            page.setTotalPages(totalPage);
            page.setData(roleList);
            
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
    public String addRole() {
        return "role/add";
    }
    
    /**
     * 插入用户
     */
    @ResponseBody
    @RequestMapping ("insert")
    public R insertUser(Role role) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        role.setGmtCreated(sdf.format(new Date()));
        try {
            Integer result = roleService.insertRole(role);
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
    public String goEditPage(@RequestParam ("id") Long roleId, Model model) {
        Role role = roleService.queryRoleById(roleId);
        model.addAttribute("role", role);
        return "role/edit";
    }
    
    /**
     * 插入用户
     */
    @ResponseBody
    @RequestMapping ("update")
    public R updateUser(Role role) {
        try {
            Integer result = roleService.updateRole(role);
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
            Boolean result = roleService.deleteRoleById(id);
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
    public R deleteRoleById(Integer[] roleId) {
        Map<String, Object> map = new HashMap();
        map.put("ids", roleId);
        try {
            Boolean result = roleService.batchDeleteRolesByIds(map);
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
    public String assign(Long id) {
        
        return "role/assign";
    }
    
    
    @ResponseBody
    @RequestMapping ("doAssign")
    public R doAssign(Long roleId, Long[] permissionIds) {
        Map<String, Object> map = new HashMap<>();
        map.put("roleId", roleId);
        map.put("permissionIds", permissionIds);
        try {
            Integer result = roleService.doAssign(map);
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
