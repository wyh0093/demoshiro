package com.example.demo.service.impl;

import com.example.demo.entity.AuditLog;
import com.example.demo.repository.AuditLogRepository;
import com.example.demo.service.AuditLogService;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: demo
 * @description:
 * @author: Yunhuan Wang
 * @create: 2019/11/5 17:33
 **/
@Service
public class AuditLogServiceImpl implements AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;
    @Override
    public void save(AuditLog auditLog) {
        auditLogRepository.save(auditLog);
    }
}
