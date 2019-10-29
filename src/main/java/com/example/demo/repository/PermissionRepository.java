package com.example.demo.repository;

import com.example.demo.entity.Permission;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by qwe on 2019/6/30.
 */
@Repository("permissionRepository")
public interface PermissionRepository extends JpaRepository<Permission,Integer>,JpaSpecificationExecutor<Permission> {


}
