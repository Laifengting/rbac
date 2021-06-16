package com.lft.rbac.controller;

import com.lft.rbac.entity.Permission;
import com.lft.rbac.entity.User;
import com.lft.rbac.service.PermissionService;
import com.lft.rbac.service.UserService;
import com.lft.rbac.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class DispatcherController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private PermissionService permissionService;
    
    @RequestMapping ("login")
    public String login() {
        return "login";
    }
    
    @RequestMapping ("doLogin")
    public String doLogin(User user, Model model) {
        /*
         * 1. 获取表单数据
         * 1.1. HttpServletRequest
         * 1.2. 在方法形参列表中，增加表单对应的参数，名称相同。
         * 1.3. 将表单数据封装为实体类对象
         */
        /*
         * 2. 解决字符乱码
         * 2.1 手动解决
         * 2.2 过滤器解决 在 web.xml 中添加过滤器
         * 2.3 解决 URI 请求路径中的乱码 在 tomcat 配置文件 server.xml 的 Connector 标签中 添加 URIEncoding="UTF-8" 属性
     
        String loginacct = user.getLoginacct();
        byte[] bytes = loginacct.getBytes(StandardCharsets.ISO_8859_1);
        user.setLoginacct(new String(bytes, StandardCharsets.UTF_8));
         */
        
        /*
         * 3. 查询用户信息
         */
        User userFromDb = userService.query4Login(user);
        
        /*
         * 4. 判断用户信息是否存在
         */
        if (userFromDb != null) {
            // 登录成功，跳转到主页面
            return "main";
        } else {
            // 登录失败，跳转回到登录首页。提示错误信息。
            String errMsg = "登录账号或密码不正确，请重新输入";
            model.addAttribute("errMsg", errMsg);
            return "redirect:login";
        }
    }
    
    @ResponseBody
    @RequestMapping ("doAjaxLogin")
    public Object doAjaxLogin(User user, HttpSession session) {
        User userFromDb = userService.query4Login(user);
        if (userFromDb != null) {
            session.setAttribute("user", userFromDb);
            
            // 获取用户权限信息
            List<Permission> permissionList = permissionService.queryPermissionByUser(userFromDb);
            
            Map<Long, Permission> permissionMap = new HashMap<>();
            Permission root = null;
            
            Set<String> uriSet = new HashSet<>();
            
            for (Permission permission : permissionList) {
                permissionMap.put(permission.getId(), permission);
                if (permission.getUrl() != null && !"".equals(permission.getUrl())) {
                    // 注意，数据库中存储的路径是不带项目名的。
                    uriSet.add(session.getServletContext().getContextPath() + permission.getUrl());
                }
            }
            
            session.setAttribute("authUriSet", uriSet);
            
            for (Permission permission : permissionList) {
                if (permission.getPid() == 0) {
                    root = permission;
                } else {
                    permissionMap.get(permission.getPid()).getChildren().add(permission);
                }
            }
            session.setAttribute("rootPermission", root);
            
            return R.OK();
        } else {
            return R.ERROR();
        }
    }
    
    @RequestMapping ("main")
    public String main() {
        return "main";
    }
    
    @RequestMapping ("logout")
    public String logout(HttpSession session) {
        // session.removeAttribute("user");
        session.invalidate(); // 让 session 失效。
        // return "login";
        return "redirect:login";
    }
    
    @RequestMapping ("error")
    public String error() {
        return "error";
    }
    
}
