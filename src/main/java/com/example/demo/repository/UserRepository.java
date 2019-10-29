package com.example.demo.repository;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by qwe on 2019/6/27.
 */
@Repository("userRepository")
public interface UserRepository extends JpaRepository<User,Integer>,JpaSpecificationExecutor<User> {

    User findUserById(Integer id);

    @Query(value = " from User where userName=?1")
    User findUserByUserName(String userName);

    @Query(value = " from User where userName=?1 and password =?2")
    User findUserByUserNamePassword(String userName, String password);

}
