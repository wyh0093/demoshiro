package com.example.demo.service.impl;

import com.example.demo.entity.ActivityType;
import com.example.demo.repository.ActivityTypeRepository;
import com.example.demo.service.ActivityTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by qwe on 2019/8/15.
 */
@Service
public class ActivityTypeServiceImpl implements ActivityTypeService {

    @Autowired
    private ActivityTypeRepository activityTypeRepository;

    @Override
    public List<ActivityType> findAll() {
        return activityTypeRepository.findAll(Sort.by(Sort.Direction.ASC,"id"));
    }

    @Override
    public ActivityType getOne(Integer id) {
        return activityTypeRepository.findById(id).get();
    }

    @Override
    public void save(ActivityType activityType) {
        activityTypeRepository.save(activityType);
    }

}
