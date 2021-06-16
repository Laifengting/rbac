package com.lft.rbac.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServerStartupListener implements ServletContextListener {
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 将 Web 应用名称（路径）保存到 application 范围中
        ServletContext servletContext = sce.getServletContext();
        String path = servletContext.getContextPath();
        servletContext.setAttribute("APP_PATH", path);
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    
    }
}
