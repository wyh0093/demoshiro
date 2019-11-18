package com.example.demo.service.impl;

import com.example.demo.entity.TestB;
import com.example.demo.repository.TestBRepository;
import com.example.demo.service.TestBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: demo
 * @description:
 * @author: Yunhuan Wang
 * @create: 2019/11/16 11:11
 **/
@Service
public class TestBServiceImpl implements TestBService {

    @Autowired
    private TestBRepository testBRepository;
    @Override
    public void save(TestB testB) {

    }
}
