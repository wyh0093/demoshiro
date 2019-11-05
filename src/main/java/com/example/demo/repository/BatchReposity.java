package com.example.demo.repository;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @program: demoshiro
 * @description:
 * @author: Yunhuan Wang
 * @create: 2019/11/4 23:30
 **/
public class BatchReposity<T> {

    @PersistenceContext
    protected EntityManager em;
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void batchInsert(List<T> list){
        for (int i = 0; i <list.size()   ; i++) {
            em.persist(list.get(i));

            em.merge(list.get(i));
        }
    }
}
