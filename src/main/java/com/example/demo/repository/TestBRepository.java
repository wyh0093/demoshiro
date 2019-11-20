package com.example.demo.repository;

import com.example.demo.entity.TestB;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: demo
 * @description:
 * @author: wyh
 * @create: 2019-11-16 11:07
 **/
@Repository
public interface TestBRepository  extends JpaRepository<TestB,String>{

//    @Query(value = "SELECT c.name,b.parent_id from testc c ,test_b b where b.id=c.id and c.id=:unitId")
//    List<Object[]> findTestBAndTestCMsg(@Param("unitId") Integer unitId);

}
