package com.example.demo.service;

import com.example.demo.entity.JobLog;
import com.example.demo.entity.Role;
import com.example.demo.entityModel.JobLogModel;
import com.example.demo.entityModel.RoleModel;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by qwe on 2019/6/30.
 */
public interface JobLogService {
    JobLog findLogById(Integer id);

    List<JobLog> findAll(Integer currentpage, Integer size, JobLogModel jobLog, String page);

    void save(JobLog role);

    void del(Integer id);

}
