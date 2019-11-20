package com.example.demo.util;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: demo
 * @description:
 * @author: wyh
 * @create: 2019/11/19 11:08
 **/
public class CopyDtoUtil {

    public  static Logger logger = LoggerFactory.getLogger(CopyDtoUtil.class);

    /**
     * 实体集合与DTO集合的转换
     * @param sourceList
     * @param beansClass
     * @param <T>
     * @return
     */
    public static <T> List<T> copyList(Object sourceList, Class<T> beansClass) {
        List<Object> sList = (List<Object>) sourceList;
        if(!CollectionUtils.isEmpty(sList)){}
        if(sList.isEmpty()){}
        List<Object> tList = new ArrayList<Object>();
        sList.forEach(object ->{
            Object dto = null;
            try {
                dto = beansClass.newInstance();
            } catch (Exception e) {
                logger.error("实体转换DTO失败！"+e.getMessage());
                e.printStackTrace();
            }
            BeanUtils.copyProperties(object, dto);
            tList.add(dto);
        });
        return (List<T>) tList;
    }

    public static <T> List<T> castEntity(List<Object[]> list,Class<T> clazz){
        List<T> returnList = new ArrayList<>();
        if(CollectionUtils.isEmpty(list)){
            return returnList;
        }
        Object[] objects = list.get(0);
        Class[] classes = new Class[objects.length];
        for (int i = 0; i < objects.length; i++) {
            if(objects[i]==null){
                classes[i] = String.class;
            }else {
                classes[i] = objects[i].getClass();
            }
        }
        return returnList;
    }
}
