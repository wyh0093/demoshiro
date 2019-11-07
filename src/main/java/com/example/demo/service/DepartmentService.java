package com.example.demo.service;

import com.example.demo.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by qwe on 2019/7/1.
 */
public interface DepartmentService {

    Page<Department> findAllByPage(Integer page, Integer size);


    List<Department> findByCName(String cName);


    List<Department> findAll(Pageable pageable, String keyword,String flag);

    Department findById(Integer id);

    void save(Department department);

    void deleteById(Integer id);

}
