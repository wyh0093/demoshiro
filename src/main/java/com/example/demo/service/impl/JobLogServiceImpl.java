package com.example.demo.service.impl;

import com.example.demo.entity.JobLog;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.entityModel.JobLogModel;
import com.example.demo.repository.JobLogRepository;
import com.example.demo.service.JobLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: demo
 * @description:
 * @author: wyh
 * @create: 2019/11/25 18:36
 **/
@Service
public class JobLogServiceImpl implements JobLogService {

    @Autowired
    private JobLogRepository jobLogRepository;

    @Autowired
    private HttpSession session;
    @Override
    public JobLog findLogById(Integer id) {
        return jobLogRepository.getOne(id);
    }

    @Override
    public List<JobLog> findAll(Integer currentpage, Integer pageSize, JobLogModel jobLog, String page) {

        Specification<JobLog> queryCondition = new Specification<JobLog>() {
            private static final long serialVersionUID = -594262632507712037L;
            @Override
            public Predicate toPredicate(Root<JobLog> root, CriteriaQuery<?> crite, CriteriaBuilder cb) {
                List<Predicate> pr = new ArrayList<>();
                if( null!=jobLog){
                    Predicate title = cb.like(root.get("title").as(String.class), "%"+jobLog.getTitle()+"%");
                    User user = (User)session.getAttribute("user");
                    Predicate author = null;
                    if(null!=user){
                        author = cb.like(root.get("author").as(String.class), "%"+jobLog.getAuthor()+"%");
                    }
                    Predicate startTime = cb.greaterThanOrEqualTo(root.get("createTime").as(String.class), "%"+jobLog.getStartTime()+"%");
                    Predicate endTime = cb.lessThanOrEqualTo(root.get("createTime").as(String.class), "%"+jobLog.getEndTime()+"%");
                    pr.add(cb.or(author,startTime,endTime));
                }
                return cb.and(pr.toArray(new Predicate[pr.size()]));
            }
        };
        List<JobLog> logList = new ArrayList<>();
        if("true".equals(page)){
            logList = jobLogRepository.findAll(queryCondition, PageRequest.of(currentpage - 1, pageSize, Sort.by(Sort.Direction.ASC, "id"))).getContent();
        }else {
            logList = jobLogRepository.findAll(queryCondition, Sort.by(Sort.Direction.ASC, "id"));
        }

        return logList;
    }


    @Override
    public void save(JobLog jobLog) {
        jobLogRepository.save(jobLog);
    }

    @Override
    public void del(Integer id) {
        jobLogRepository.deleteById(id);
    }

}
