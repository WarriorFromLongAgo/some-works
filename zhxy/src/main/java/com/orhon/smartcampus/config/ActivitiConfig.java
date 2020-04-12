package com.orhon.smartcampus.config;


import org.activiti.engine.*;
import org.activiti.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.orhon.smartcampus.modules.activiti.CustomGroupEntityManager;
import com.orhon.smartcampus.modules.activiti.CustomGroupEntityManagerFactory;
import com.orhon.smartcampus.modules.activiti.CustomUserEntityManager;
import com.orhon.smartcampus.modules.activiti.CustomUserEntityManagerFactory;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

@Configuration
public class ActivitiConfig {

    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private CustomGroupEntityManagerFactory customGroupEntityManagerFactory;
    
    @Autowired
    private CustomUserEntityManagerFactory customUserEntityManagerFactory;
    

    @Bean
    public StandaloneProcessEngineConfiguration processEngineConfiguration(){
        StandaloneProcessEngineConfiguration configuration = new StandaloneInMemProcessEngineConfiguration();
        configuration.setDataSource(this.dataSource);
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        configuration.setAsyncExecutorActivate(false);
        List<SessionFactory> factory = new ArrayList<SessionFactory>();
        factory.add(customGroupEntityManagerFactory);
        factory.add(customUserEntityManagerFactory);
        configuration.setCustomSessionFactories(factory);
        return configuration;
    }


    @Bean
    public ProcessEngine processEngine(){
        return processEngineConfiguration().buildProcessEngine();
    }


    @Bean
    public RepositoryService repositoryService(){
        return processEngine().getRepositoryService();
    }

    @Bean
    public RuntimeService runtimeService(){
        return processEngine().getRuntimeService();
    }

    @Bean
    public TaskService taskService(){
        return processEngine().getTaskService();
    }
    
    @Bean
    public HistoryService historyService(){
        return processEngine().getHistoryService();
    }






}
