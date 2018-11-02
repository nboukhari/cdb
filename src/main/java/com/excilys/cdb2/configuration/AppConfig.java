package com.excilys.cdb2.configuration;

import java.util.Properties;
import java.util.ResourceBundle;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages ={"com.excilys.cdb2.configuration", "com.excilys.cdb2.controller", "com.excilys.cdb2.mapper", "com.excilys.cdb2.persistence",
"com.excilys.cdb2.service", "com.excilys.cdb2.ui", "com.excilys.cdb2.model", "com.excilys.cdb2.exception"})
@PropertySource("classpath:config.properties")
public class AppConfig {

	@Autowired
	Environment environment;

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
	
	Properties hibernateProperties() {
		//ResourceBundle bundle = ResourceBundle.getBundle("hibernate");		
		return new Properties() {
			private static final long serialVersionUID = 1L;
			{
				   setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLInnoDBDialect");
				    setProperty("hibernate.connection.datasource", "java:comp/env/jdbc/test");
				    setProperty("hibernate.order_updates", "true");
	         }
		};
	}
	
	@Bean
	public LocalSessionFactoryBean getSessionFactory(DataSource datasource) {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(datasource);
		sessionFactory.setPackagesToScan(new String[] { "com.excilys.cdb2.model" });
		sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
	}
	
	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager manager = new HibernateTransactionManager();
		manager.setSessionFactory(sessionFactory);
		return manager;	
	}
}