package com.example.demo.repository;

import com.example.demo.entity.User;
import com.example.demo.entity.WorkTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by qwe on 2019/8/11.
 */
@Repository
public interface WorkTaskRepository  extends JpaRepository<WorkTask,Integer>,JpaSpecificationExecutor<WorkTask> {

    @Query(value = "from WorkTask where execution_Id =?1 ")
    WorkTask findByExecutionId(String executionId);
}
