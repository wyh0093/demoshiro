package com.example.demo.service.impl;

import com.example.demo.entity.ActiviHistory;
import com.example.demo.repository.ActivityHistoryRepository;
import com.example.demo.service.ActivityHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: demoshiro
 * @description:
 * @author: wyh
 * @create: 2019/11/10 22:50
 **/
@Service
public class ActivityHistoryServiceImpl implements ActivityHistoryService {

    @Autowired
    private ActivityHistoryRepository activityHistoryRepository;

    @Override
    public void save(ActiviHistory activiHistory) {

    }
}
