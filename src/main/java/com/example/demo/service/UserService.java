package com.example.demo.service;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.entityModel.UserModel;
import com.example.demo.util.PageInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by qwe on 2019/6/27.
 */
public interface UserService {

    User findUserById(Integer id);

    User findUserByUserName(String userName);

    User findUserByUserNamePassword(String userName, String password);



    List<User> findAllByPage(Pageable pageable, String keyword);


    PageInfo<User> findAll(Pageable pageable, String keyword, Integer status, boolean flag);


    User findManagerByDepart(Integer id);

    User findBoss();

    User save(User user);

    void del(Integer id);
    @Query(value = "")
    User getApplyPerson(String executionId);

}
