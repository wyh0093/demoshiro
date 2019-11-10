package com.example.demo.conf;/**
 * Created by qwe on 2019/11/4.
 */

import com.example.demo.util.ConstantUtil;
import org.apache.commons.collections4.iterators.EntrySetMapIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @program: demoshiro
 * @description:
 * @author: Yunhuan Wang
 * @create: 2019-11-04 14:35
 **/
@Component
@PropertySource("classpath:activityKey.properties")
public class PropertiesConfig {

    private Logger log = LoggerFactory.getLogger(PropertiesConfig.class);



    @Bean(name = "initActivityKey")
    public String initActivityKey(){
        try {
            String path = ConstantUtil.getResourceUrl();
            Files.readAllLines(Paths.get(path+"activityKey.properties")).stream().forEach(line ->{
                if(line.contains("=")){
                    String[] strArr = line.split("=");
                    ConstantUtil.activityKey.put(strArr[0],strArr[1]);
                }
            });
            /*ConstantUtil.activityKey.forEach((k,v) ->{
                System.out.println(k+","+v);
            });*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
