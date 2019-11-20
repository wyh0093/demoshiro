package com.example.demo.repository;

import com.example.demo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by qwe on 2019/6/30.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role,Integer>,JpaSpecificationExecutor<Role> {

    Role findRoleById(Integer id);

    @Query(value = "select * from Role r  where r.name like '%?3%' order by  r.id asc limit ?1,?2 ",nativeQuery = true)
    List<Role> findAllPage(Integer page, Integer size, String name);

    /*@Query(value = "from Role where user.id=?1 ")
    List<Role> findByUserId(Integer id);*/


}
