package com.lft.rbac.aspectj;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class RequestLogAspect {
    private final static Logger LOGGER = LoggerFactory.getLogger(RequestLogAspect.class);
    
    @Pointcut ("execution(* com.lft.rbac.controller.*.*(..))")
    public void requestServer() {
    }
    
    @Around ("requestServer()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        
        Object result = proceedingJoinPoint.proceed();
        
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setIp(request.getRemoteAddr());
        requestInfo.setUrl(request.getRequestURL().toString());
        requestInfo.setHttpMethod(request.getMethod());
        requestInfo.setClassMethod(String.format("%s.%s", proceedingJoinPoint.getSignature().getDeclaringTypeName(),
                                                 proceedingJoinPoint.getSignature().getName()));
        requestInfo.setRequestParams(getRequestParamsByProceedingJoinPoint(proceedingJoinPoint));
        requestInfo.setResult(result);
        requestInfo.setTimeCost(System.currentTimeMillis() - start);
        LOGGER.info("Request Info      : {}", JSON.toJSONString(requestInfo));
        
        return result;
    }
    
    @AfterThrowing (pointcut = "requestServer()", throwing = "e")
    public void doAfterThrow(JoinPoint joinPoint, RuntimeException e) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        RequestErrorInfo requestErrorInfo = new RequestErrorInfo();
        requestErrorInfo.setIp(request.getRemoteAddr());
        requestErrorInfo.setUrl(request.getRequestURL().toString());
        requestErrorInfo.setHttpMethod(request.getMethod());
        requestErrorInfo.setClassMethod(String.format("%s.%s", joinPoint.getSignature().getDeclaringTypeName(),
                                                      joinPoint.getSignature().getName()));
        requestErrorInfo.setRequestParams(getRequestParamsByJoinPoint(joinPoint));
        requestErrorInfo.setException(e);
        LOGGER.info("Error Request Info      : {}", JSON.toJSONString(requestErrorInfo));
    }
    
    /**
     * ????????????
     */
    private Map<String, Object> getRequestParamsByProceedingJoinPoint(ProceedingJoinPoint proceedingJoinPoint) {
        //?????????
        String[] paramNames = ((MethodSignature) proceedingJoinPoint.getSignature()).getParameterNames();
        //?????????
        Object[] paramValues = proceedingJoinPoint.getArgs();
        
        return buildRequestParam(paramNames, paramValues);
    }
    
    private Map<String, Object> getRequestParamsByJoinPoint(JoinPoint joinPoint) {
        //?????????
        String[] paramNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        //?????????
        Object[] paramValues = joinPoint.getArgs();
        
        return buildRequestParam(paramNames, paramValues);
    }
    
    private Map<String, Object> buildRequestParam(String[] paramNames, Object[] paramValues) {
        Map<String, Object> requestParams = new HashMap<>();
        for (int i = 0; i < paramNames.length; i++) {
            Object value = paramValues[i];
            
            //?????????????????????
            if (value instanceof MultipartFile) {
                MultipartFile file = (MultipartFile) value;
                //???????????????
                value = file.getOriginalFilename();
            }
            requestParams.put(paramNames[i], value);
        }
        return requestParams;
    }
    
    public class RequestInfo {
        private String ip;
        private String url;
        private String httpMethod;
        private String classMethod;
        private Object requestParams;
        private Object result;
        private Long timeCost;
        
        public String getIp() {
            return ip;
        }
        
        public void setIp(String ip) {
            this.ip = ip;
        }
        
        public String getUrl() {
            return url;
        }
        
        public void setUrl(String url) {
            this.url = url;
        }
        
        public String getHttpMethod() {
            return httpMethod;
        }
        
        public void setHttpMethod(String httpMethod) {
            this.httpMethod = httpMethod;
        }
        
        public String getClassMethod() {
            return classMethod;
        }
        
        public void setClassMethod(String classMethod) {
            this.classMethod = classMethod;
        }
        
        public Object getRequestParams() {
            return requestParams;
        }
        
        public void setRequestParams(Object requestParams) {
            this.requestParams = requestParams;
        }
        
        public Object getResult() {
            return result;
        }
        
        public void setResult(Object result) {
            this.result = result;
        }
        
        public Long getTimeCost() {
            return timeCost;
        }
        
        public void setTimeCost(Long timeCost) {
            this.timeCost = timeCost;
        }
        
    }
    
    public class RequestErrorInfo {
        private String ip;
        private String url;
        private String httpMethod;
        private String classMethod;
        private Object requestParams;
        private RuntimeException exception;
        
        public String getIp() {
            return ip;
        }
        
        public void setIp(String ip) {
            this.ip = ip;
        }
        
        public String getUrl() {
            return url;
        }
        
        public void setUrl(String url) {
            this.url = url;
        }
        
        public String getHttpMethod() {
            return httpMethod;
        }
        
        public void setHttpMethod(String httpMethod) {
            this.httpMethod = httpMethod;
        }
        
        public String getClassMethod() {
            return classMethod;
        }
        
        public void setClassMethod(String classMethod) {
            this.classMethod = classMethod;
        }
        
        public Object getRequestParams() {
            return requestParams;
        }
        
        public void setRequestParams(Object requestParams) {
            this.requestParams = requestParams;
        }
        
        public RuntimeException getException() {
            return exception;
        }
        
        public void setException(RuntimeException exception) {
            this.exception = exception;
        }
    }
}
