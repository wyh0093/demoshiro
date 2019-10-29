package com.example.demo.service.impl;

import com.example.demo.entity.Department;
import com.example.demo.entity.User;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qwe on 2019/7/1.
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;
    @Override
    public Page<Department> findAllByPage(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "id");
        return departmentRepository.findAll(pageable);
    }

    public Specification<Department> findCriteria(String keyword){

        return new Specification<Department>(){
            @Override
            public Predicate toPredicate(Root<Department> root, CriteriaQuery<?> query, CriteriaBuilder cr) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtils.isEmpty(keyword)){
                    Predicate name = cr.like(root.get("name").as(String.class),"%"+keyword+"%" );
                    list.add(cr.or(name));
                }
                Predicate[] p = new Predicate[list.size()];
                return cr.and(list.toArray(p));
            }
        };

    }

    @Override
    public List<Department> findByCName(String cName) {
        return departmentRepository.findByCName(cName);
    }

    @Override
    public List<Department> findAll(Pageable pageable,String keyword,boolean flag) {
        List<Department> all = new ArrayList<>();
        if(flag){
            all = departmentRepository.findAll(findCriteria(keyword),pageable).getContent();
        }else {
            all = departmentRepository.findAll(findCriteria(keyword),Sort.by(Sort.Direction.ASC, "id"));
        }
        return all;
    }

    @Override
    public Department findById(Integer id) {
        return departmentRepository.findById(id).get();
    }

    @Override
    public void save(Department department) {
        departmentRepository.save(department);
    }

    @Override
    public void deleteById(Integer id) {
        departmentRepository.deleteById(id);
    }
}
