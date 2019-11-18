package com.example.demo.repository;

import com.example.demo.entity.TestB;
import com.example.demo.entity.TestC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @program: demo
 * @description:
 * @author: wyh
 * @create: 2019-11-16 11:07
 **/
@Repository("testCRepository")
public interface TestCRepository extends JpaRepository<TestC,Integer>, JpaSpecificationExecutor<TestC> {


}
