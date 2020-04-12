package com.orhon.smartcampus.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import javax.sql.DataSource;
import java.util.Properties;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EntityScan(basePackages = "com.orhon.smartcampus.modules.core.graphql.orm.entity")
public class HibernateConfig {

    @Autowired
    private DataSource dataSource;


    @Bean
    public Object testBean(PlatformTransactionManager platformTransactionManager){
        System.out.println(">>>>>>>>>>" + platformTransactionManager.getClass().getName());
        return new Object();
    }

    @Bean
    public LocalSessionFactoryBean factoryBean(){
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setDataSource(this.dataSource);
        factoryBean.setPackagesToScan(
                new String[] {
                        "com.orhon.smartcampus.modules.core.graphql.orm" ,
                        "com.orhon.smartcampus.modules.core.graphql.orm.entity" ,
                });
        //factoryBean.set
        factoryBean.setHibernateProperties(hibernateProperties());
        return factoryBean;
    }


     //如果没有这个函数……
     //PlatformTransactionManager将会是org.springframework.jdbc.datasource.DataSourceTransactionManager
     //如果这个函数存在
     ///PlatformTransactionManager将会是org.springframework.orm.hibernate4.HibernateTransactionManager


    @Bean
    public HibernateTransactionManager transactionManager(){
        HibernateTransactionManager manager = new HibernateTransactionManager();
        manager.setDataSource(this.dataSource);
        manager.setSessionFactory(factoryBean().getObject());
        return manager;
    }



    Properties hibernateProperties() {
        return new Properties() {
            {
                setProperty("hibernate.hbm2ddl.auto", "none");
                setProperty("hibernate.dialect","org.hibernate.dialect.MySQL5Dialect");
                setProperty("hibernate.globally_quoted_identifiers","false");
                setProperty("hibernate.show_sql" , "true");
                setProperty("hibernate.current_session_context_class" , "org.springframework.orm.hibernate4.SpringSessionContext");
            }
        };
    }


}
