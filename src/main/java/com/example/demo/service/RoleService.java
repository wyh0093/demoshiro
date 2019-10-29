package com.example.demo.service;

import com.example.demo.entity.Role;
import com.example.demo.entityModel.RoleModel;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by qwe on 2019/6/30.
 */
public interface RoleService {
    Role findRoleById(Integer id);

    Page<Role> findAll(Integer page, Integer size);

    List<RoleModel> findAllCriteria(Integer currentpage, Integer size, Role role, String page);

    List<RoleModel> findAllPage(Integer page, Integer size, String name);

    void save(Role role);

    void del(Integer id);

    int totalCount(Role role);

    Role findOne(Integer id);

    List<Role> findByUserId(Integer id);

    List<Role> findAllNoCondition();
}
