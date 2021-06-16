package com.lft.rbac.controller;

import com.lft.rbac.entity.Permission;
import com.lft.rbac.service.PermissionService;
import com.lft.rbac.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping ("permission")
public class PermissionController {
    
    @Autowired
    private PermissionService permissionService;
    
    @RequestMapping ("index")
    public String index() {
        return "permission/index";
    }
    
    @ResponseBody
    @RequestMapping ("loadData")
    public Object loadData() {
        // 模拟数据
        // Permission root = new Permission();
        // root.setName("顶级节点");
        //
        // Permission child = new Permission();
        // child.setName("子节点");
        //
        // root.getChildren().add(child);
        //
        // permissionList.add(root);
        
        // 读取树形结构
        // Permission root = permissionService.queryRootPermission();
        // List<Permission> childlist = permissionService.queryChildPermissions(root.getId());
        // for (Permission permission : childlist) {
        //     permission.setChildren(permissionService.queryChildPermissions(permission.getId()));
        // }
        // root.setChildren(childlist);
        // permissionList.add(root);
        
        // 递归查询数据
        // Permission root = new Permission();
        // root.setId(0L);
        // queryChildPermissions(root);
        
        List<Permission> permissionList = new ArrayList<>();
        
        //
        // // 查询所有的许可数据
        List<Permission> ps = permissionService.queryAllPermissions();
        
        // // 遍历所有的子节点
        // for (Permission permission : ps) {
        //     if (permission.getPid() == 0) {
        //         permissionList.add(permission);
        //     } else {
        //         // 遍历所有的父节点
        //         for (Permission p : ps) {
        //             if (permission.getPid().equals(p.getId())) {
        //                 // 组合关系
        //                 p.getChildren().add(permission);
        //                 break;
        //             }
        //         }
        //     }
        // }
        
        Map<Long, Permission> permissionMap = new HashMap<>();
        for (Permission p : ps) {
            permissionMap.put(p.getId(), p);
        }
        for (Permission p : ps) {
            if (p.getPid() == 0) {
                permissionList.add(p);
            } else {
                permissionMap.get(p.getPid()).getChildren().add(p);
            }
        }
        return permissionList;
    }
    
    /**
     * 递归查询许可信息
     * 1. 方法自己调用自己
     * 2. 方法一定要存在跳出逻辑
     * 3. 方法调用时，参数之间应该有规律
     * 4. 递归算法效率较低。
     * @param root
     */
    private void queryChildPermissions(Permission root) {
        List<Permission> childlist = permissionService.queryChildPermissions(root.getId());
        if (!childlist.isEmpty()) {
            for (Permission permission : childlist) {
                queryChildPermissions(permission);
            }
        }
        root.setChildren(childlist);
    }
    
    @ResponseBody
    @RequestMapping ("loadAssignData")
    public Object loadAssignData(Long roleId) {
        // 用于返回的数据
        List<Permission> result = new ArrayList<>();
        
        // 查询所有的许可数据
        List<Permission> ps = permissionService.queryAllPermissions();
        
        // 查询出当前角色已经有的许可信息
        List<Long> permissionIds = permissionService.queryPermissionIdByRoleId(roleId);
        
        Map<Long, Permission> permissionMap = new HashMap<>();
        for (Permission p : ps) {
            if (!permissionIds.isEmpty()) {
                p.setChecked(permissionIds.contains(p.getId()));
            }
            permissionMap.put(p.getId(), p);
        }
        for (Permission p : ps) {
            if (p.getPid() == 0) {
                result.add(p);
            } else {
                permissionMap.get(p.getPid()).getChildren().add(p);
            }
        }
        return result;
    }
    
    @RequestMapping ("add")
    public String add() {
        return "permission/add";
    }
    
    @ResponseBody
    @RequestMapping ("insert")
    public R insert(Permission permission) {
        try {
            Integer result = permissionService.insertPermission(permission);
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
    
    @RequestMapping ("update")
    public String update(Long id, Model model) {
        Permission permission = permissionService.queryPermissionById(id);
        model.addAttribute("permission", permission);
        return "permission/update";
    }
    
    @ResponseBody
    @RequestMapping ("edit")
    public R edit(Permission permission) {
        try {
            Integer result = permissionService.updatePermission(permission);
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
    @RequestMapping ("delete")
    public R delete(Long id) {
        try {
            Boolean result = permissionService.deletePermissionById(id);
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
    
}

