package org.example;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@Profile("embedded")
public class HibernateConfig {

    @Autowired
    JdbcTemplate jdbc;

    @Bean(name = "sessionFactory")
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean bean = new LocalSessionFactoryBean();
        bean.setDataSource(jdbc.getDataSource());
        return bean;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory factory) {
        HibernateTransactionManager manager = new HibernateTransactionManager();
        manager.setSessionFactory(factory);
        return manager;
    }
}
