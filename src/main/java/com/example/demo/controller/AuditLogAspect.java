package com.example.demo.controller;

import com.example.demo.entity.AuditLog;
import com.example.demo.entity.User;
import com.example.demo.service.AuditLogService;
import com.example.demo.util.ConstantUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.WebApplicationContext;
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

    @Pointcut("execution (* com.example.demo.controller..*.*(..)) && !execution(* com.example.demo.controller.AuditLogController..*(..)) && !execution(* com.example.demo" +
            ".controller.HtmlController.*(..)) && !execution(* com.example.demo.controller.LoginController..*(..))")
    public void webLog() {
    }

    @Pointcut("execution(* com.example.demo.controller.LoginController.*(..))")
    public void loginLog() {
    }
    @Pointcut("execution(* com.example.demo.controller.LoginController.logout())")
    public void logoutLog() {
    }

    @Autowired
    private AuditLogService auditLogService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    WebApplicationContext applicationContext;



    @Before("logoutLog()")
    public void before(JoinPoint joinPoint)throws Exception {
        String requestUrl = request.getServletPath();
        String path = ConstantUtil.getResourceUrl();
        AuditLog auditLog = new AuditLog();
        saveAuditLog(requestUrl,auditLog);
    }
    /*@After("loginLog()")
    public void after(JoinPoint joinPoint)throws Throwable {
        String requestUrl = request.getServletPath();
        String path = ConstantUtil.getResourceUrl();
        AuditLog auditLog = new AuditLog();
        User user = (User)request.getSession().getAttribute("user");
        if(user!=null && requestUrl.contains("loginValidation"))
        saveAuditLog(requestUrl,auditLog);
    }*/

    @AfterReturning(returning = "ret",pointcut = "loginLog()")
    public void doAfter(JoinPoint joinPoint,Object ret) throws Exception {
        String requestUrl = request.getServletPath();
        AuditLog auditLog = new AuditLog();
        System.out.println(ret);
        if("index".equals(ret) && requestUrl.contains("loginValidation")){
            saveAuditLog(requestUrl,auditLog);
        }
    }

    @Around("webLog()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String requestUrl = request.getServletPath();
        Object obj = null;
        AuditLog auditLog = new AuditLog();
        try {
            obj = joinPoint.proceed();
            auditLog.setResult("success");
        } catch (Throwable throwable) {
            auditLog.setResult("failed");
            throwable.printStackTrace();
        }finally {
            saveAuditLog(requestUrl,auditLog);
        }
        return obj;

    }

    /**
     * 存储日志
     * @param url
     * @param auditLog
     * @throws Exception
     */
    public void saveAuditLog(String url,AuditLog auditLog) throws Exception{
        String path = ConstantUtil.getResourceUrl();
        Files.readAllLines(Paths.get(path+"mappingurl.csv")).stream().forEach(line ->{
            if(line.contains(",")){
                String[] strArr = line.split(",");
                if(url.equals(strArr[0])){
                    User user = (User)request.getSession().getAttribute("user");
                    auditLog.setUsername(user.getUserName());
                    auditLog.setIp(getCliectIp());
                    auditLog.setBusiness(strArr[1]);
                    auditLog.setOperateType(strArr[2]);
                    auditLog.setMethodName(strArr[3]);
                    auditLog.setRequestWay(strArr[4]);
                    auditLog.setClassName(strArr[5]);
                    auditLog.setDescription(strArr[2]);
                    auditLogService.save(auditLog);
                }
            }
        });
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
        if("0:0:0:0:0:0:0:1".equals(ip)){
            ip = "127.0.0.1";
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
}
