package com.example.demo.conf;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.spring.SpringAsyncExecutor;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.AbstractProcessEngineAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * Created by qwe on 2019/8/7.
 */
@Configuration
public class ActivitiConfig extends AbstractProcessEngineAutoConfiguration {

    public void initActivityTable(){
        //创建一个流程成引擎对像
        ProcessEngineConfiguration conf = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration();
        //设置数据源
        conf.setJdbcDriver("com.mysql.jdbc.Driver");
        conf.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/oa2");
        conf.setJdbcUsername("root");
        conf.setJdbcPassword("1234");

        //设置自动创建表
        conf.setDatabaseSchemaUpdate("true");
        //在创建引擎对象的时候自动创建表
        ProcessEngine processEngine = conf.buildProcessEngine();
    }

    //注入数据源和事务管理器
    @Bean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration(
            @Qualifier("dataSource") DataSource dataSource,
            @Qualifier("transactionManager") PlatformTransactionManager transactionManager,
            SpringAsyncExecutor springAsyncExecutor) throws IOException {
        return this.baseSpringProcessEngineConfiguration(dataSource, transactionManager, springAsyncExecutor);
    }

    @Bean(name = "dataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource activitiDataSource() {
        return DataSourceBuilder.create().build();
    }
}
