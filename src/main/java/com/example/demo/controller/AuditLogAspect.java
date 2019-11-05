package com.example.demo.controller;

import com.example.demo.entity.AuditLog;
import com.example.demo.entity.User;
import com.example.demo.service.AuditLogService;
import com.example.demo.util.ConstantUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @program: demo
 * @description:
 * @author: Yunhuan Wang
 * @create: 2019/11/5 16:23
 **/
@Aspect
@Component
public class AuditLogAspect {

    @Pointcut("execution (* com.example.demo.controller..*.*(..)) && !execution(* com.example.demo.controller.AuditLogController.*(..)) && !execution(* com.example.demo.controller" +
            ".LoginController.*(..))")
    public void webLog() {
    }

    @Autowired
    private AuditLogService auditLogService;
    @Autowired
    private HttpServletRequest request;

    @Around("webLog()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String requestUrl = request.getServletPath();
        String path = ConstantUtil.getResourceUrl();
        Object obj = null;
        AuditLog auditLog = new AuditLog();
        try {
            obj = joinPoint.proceed();
            auditLog.setResult("success");
        } catch (Throwable throwable) {
            auditLog.setResult("failed");
            throwable.printStackTrace();
        }finally {
            Files.readAllLines(Paths.get(path+"mappingurl.csv")).stream().forEach(line ->{
                if(line.contains(",")){
                    String[] strArr = line.split(",");
                    if(requestUrl.equals(strArr[0])){
                        User user = (User)request.getSession().getAttribute("user");
                        auditLog.setUsername(user.getUserName());
                        auditLog.setIp(getIpAddr(request));
                        auditLogService.save(auditLog);
                    }
                }
            });
        }
        return obj;

    }

    public String getCliectIp() {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.trim() == "" || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.trim() == "" || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.trim() == "" || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 多个路由时，取第一个非unknown的ip
        /*final String[] arr = ip.split(",");
        for (final String str : arr) {
            if (!"unknown".equalsIgnoreCase(str)) {
                ip = str;
                break;
            }
        }*/
        return ip;
    }

    public String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress="";
        }
        // ipAddress = this.getRequest().getRemoteAddr();

        return ipAddress;
    }

}
