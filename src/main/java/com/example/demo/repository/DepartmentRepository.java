package com.example.demo.repository;

import com.example.demo.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by qwe on 2019/7/1.
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department,Integer>,JpaSpecificationExecutor<Department> {


    @Query(value = "from Department where name like '%'+?1+'%' ")
    List<Department> findByCName(String cName);
}
