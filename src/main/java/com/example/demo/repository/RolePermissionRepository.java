package com.example.demo.repository;

import com.example.demo.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by qwe on 2019/7/14.
 */
@Repository("rolePermissionRepository")
public interface RolePermissionRepository extends JpaRepository<RolePermission,Integer>,JpaSpecificationExecutor<RolePermission> {

    @Query(value = "delete from RolePermission where role.id = ?1")
    void delete(Integer rId);

    @Query(value = "from RolePermission where role.id = ?1")
    List<RolePermission> findByRoleId(Integer rId);

}
