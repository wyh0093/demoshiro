package com.example.demo.repository;

import com.example.demo.entity.TestB;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @program: demo
 * @description:
 * @author: wyh
 * @create: 2019-11-16 11:07
 **/
@Repository("testBRepository")
public interface TestBRepository  extends JpaRepository<TestB,String>, JpaSpecificationExecutor<TestB> {


}
