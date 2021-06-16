package com.lft.rbac.interceptor;

import com.lft.rbac.entity.Permission;
import com.lft.rbac.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AuthInterceptor extends HandlerInterceptorAdapter {
    
    @Autowired
    private PermissionService permissionService;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取当前用户的请求地址
        String uri = request.getRequestURI();
        HttpSession session = request.getSession();
        
        // 判断当前的路径是否需要进行权限验证
        // 查询所有需要验证的路径集合。
        List<Permission> permissionList = permissionService.queryAllPermissions();
        Set<String> uriSet = new HashSet<>();
        for (Permission permission : permissionList) {
            if (permission.getUrl() != null && !"".equals(permission.getUrl())) {
                // 注意，数据库中存储的路径是不带项目名的。
                uriSet.add(session.getServletContext().getContextPath() + permission.getUrl());
            }
        }
        if (uriSet.contains(uri)) {
            // 权限验证
            // 判断当前用户是否有权限
            Set<String> authUriSet = (Set<String>) session.getAttribute("authUriSet");
            if (authUriSet.contains(uri)) {
                return true;
            } else {
                String path = session.getServletContext().getContextPath();
                response.sendRedirect(path + "/error");
                return false;
            }
        } else {
            return true;
        }
        
    }
}
