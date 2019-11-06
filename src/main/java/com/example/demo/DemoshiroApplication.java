package com.example.demo;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class DemoshiroApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoshiroApplication.class, args);


//		init();
	}

	public static void init(){
		String resource = "init/activiti-context.xml";//设置xml文件的名字
		String beanName = "ProcessEngineConfiguration";//设置id
		ProcessEngineConfiguration conf = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource(resource, beanName);
		ProcessEngine processEngine = conf.buildProcessEngine();
	}

}
