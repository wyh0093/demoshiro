package com.example.demo.repository;

import com.example.demo.entity.ActiviHistory;
import com.example.demo.entity.ActivityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @program: demoshiro
 * @description:
 * @author: wyh
 * @create: 2019/11/10 22:48
 **/
@Repository
public interface ActivityHistoryRepository extends JpaRepository<ActiviHistory,Integer>,JpaSpecificationExecutor<ActiviHistory> {
}
