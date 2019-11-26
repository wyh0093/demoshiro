package com.example.demo.repository;

import com.example.demo.entity.JobLog;
import com.example.demo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by qwe on 2019/6/30.
 */
@Repository
public interface JobLogRepository extends JpaRepository<JobLog,Integer>,JpaSpecificationExecutor<JobLog> {

}
