package com.example.demo.repository;

import com.example.demo.entity.ActivityType;
import com.example.demo.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by qwe on 2019/8/15.
 */
@Repository
public interface ActivityTypeRepository extends JpaRepository<ActivityType,Integer>,JpaSpecificationExecutor<ActivityType> {


}
