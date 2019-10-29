package com.example.demo.service;

import com.example.demo.entity.Permission;
import com.example.demo.entity.User;

import java.util.List;

/**
 * Created by qwe on 2019/7/14.
 */
public interface PermissionService {

    List<Permission> findAll();

    void update(Integer rid, String[] pIds);

    List<String> findPermissionStrByUser(User user);
}
