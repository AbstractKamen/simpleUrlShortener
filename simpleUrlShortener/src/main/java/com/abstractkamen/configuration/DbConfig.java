package com.abstractkamen.configuration;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ConfigurationProperties("datasource")
public class DbConfig {

    @Value("${datasource.user}")
    private String user;

    @Value("${datasource.password}")
    private String password;

    @Value("${datasource.driverClassName}")
    private String driverClassName;

    @Value("${datasource.jdbcUrl}")
    private String jdbcUrl;

    @Bean
    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUsername(user);
        dataSource.setUrl(jdbcUrl);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean getSessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(getDataSource());
        sessionFactory.setPackagesToScan("com.abstractkamen.simpleurlshortener.dao");
        sessionFactory.setHibernateProperties(getHibernateProperties());

        return sessionFactory;

    }

    @Bean
    public PlatformTransactionManager getHibernateTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        final LocalSessionFactoryBean sessionFactory = getSessionFactory();
        transactionManager.setSessionFactory(sessionFactory.getObject());
        return transactionManager;
    }

    @ConfigurationProperties(prefix = "hb")
    public Properties getHibernateProperties() {
        return new Properties();

    }

    @ConfigurationProperties(prefix = "hb")
    public Properties getDataSourceProperties() {
        return new Properties();
    }

}

