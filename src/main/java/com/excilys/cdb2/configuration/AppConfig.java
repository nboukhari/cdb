package com.excilys.cdb2.configuration;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement(proxyTargetClass=true)
@ComponentScan(basePackages ={"com.excilys.cdb2.configuration", "com.excilys.cdb2.controller", "com.excilys.cdb2.mapper", "com.excilys.cdb2.persistence","com.excilys.cdb2.service", "com.excilys.cdb2.ui", "com.excilys.cdb2.model", "com.excilys.cdb2.exception"})
@PropertySource("classpath:config.properties")
public class AppConfig {

	@Autowired
	private Environment environment;

	private final String URL = "url";
	private final String USER = "dbuser";
	private final String DRIVER = "driver";
	private final String PASSWORD = "dbpassword";

	@Bean
	DataSource dataSource() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setUrl(environment.getProperty(URL));
		driverManagerDataSource.setUsername(environment.getProperty(USER));
		driverManagerDataSource.setPassword(environment.getProperty(PASSWORD));
		driverManagerDataSource.setDriverClassName(environment.getProperty(DRIVER));
		return driverManagerDataSource;
	}

	Properties getProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialet", "org.hibernate.dialect.MySQLDialect");
		properties.setProperty("hibernate.show_sql", "true");
		return properties;
	}

	  @Bean
	  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
	     LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
	     em.setDataSource(dataSource());
	     em.setPackagesToScan(new String[] { "com.excilys.cdb2.model" });

	     JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	     em.setJpaVendorAdapter(vendorAdapter);
	     em.setJpaProperties(getProperties());

	     return em;
	  }

	@Bean
	@Autowired
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;	
	}
}