package com.example.demo.service;

import com.example.demo.entity.ActivityType;

import java.util.List;

/**
 * Created by qwe on 2019/8/15.
 */
public interface ActivityTypeService {

    List<ActivityType> findAll();

    ActivityType getOne(Integer id);

    void save(ActivityType activityType);
}
