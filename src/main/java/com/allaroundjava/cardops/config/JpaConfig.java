package com.allaroundjava.cardops.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class JpaConfig {
//    @Bean
//    LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        LocalContainerEntityManagerFactoryBean emf =
//                new LocalContainerEntityManagerFactoryBean();
//        emf.setPackagesToScan("com.allaroundjava.cardops.adapters.db");
//        emf.setDataSource(createDataSource());
//        emf.setJpaVendorAdapter(createJpaVendorAdapter());
//        emf.setJpaProperties(createHibernateProperties());
//        emf.afterPropertiesSet();
//        return emf;
//    }
//
//    @Bean
//    protected DataSource createDataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("org.h2.Driver");
//        dataSource.setUrl("jdbc:h2:tcp://localhost/~/test1");
//        dataSource.setUsername("sa");
//        dataSource.setPassword("");
//
//        return dataSource;
//    }
//
//    private JpaVendorAdapter createJpaVendorAdapter() {
//        return new HibernateJpaVendorAdapter();
//    }
//
//    private Properties createHibernateProperties() {
//        Properties properties = new Properties();
//        properties.setProperty("javax.persistence.schema-generation.database.action", "drop-and-create");
//        properties.setProperty(
//                "hibernate.dialect", "org.hibernate.dialect.H2Dialect");
//        return properties;
//    }
}
