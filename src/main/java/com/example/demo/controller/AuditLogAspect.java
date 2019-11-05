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

    @Around("webLog()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String requestUrl = request.getServletPath();

        String path = ConstantUtil.getResourceUrl();
        Files.readAllLines(Paths.get(path+"mappingurl.csv")).stream().forEach(line ->{
            if(line.contains(",")){
                String[] strArr = line.split(",");
                if(requestUrl.equals(strArr[0])){
                    User user = (User)request.getSession().getAttribute("user");
                    AuditLog auditLog = new AuditLog();
                    auditLog.setUsername(user.getUserName());
//                    auditLog.set
                }
            }
        });

        System.out.println("11111111111111");
    }
}
