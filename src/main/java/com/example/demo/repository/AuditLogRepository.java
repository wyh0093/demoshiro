package com.example.demo.repository;

import com.example.demo.entity.ActivityType;
import com.example.demo.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @program: demo
 * @description:
 * @author: Yunhuan Wang
 * @create: 2019/11/5 17:30
 **/
@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog,Integer>, JpaSpecificationExecutor<AuditLog> {

}
