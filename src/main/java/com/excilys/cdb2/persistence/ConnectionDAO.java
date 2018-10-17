package com.excilys.cdb2.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.springframework.stereotype.Repository;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Repository
public class ConnectionDAO {
	
	//private final static Logger logger = LoggerFactory.getLogger("ConnectionDAO");
	private HikariConfig config;
	private HikariDataSource datasource;
	
	/**
	 * This method connects to the database
	 * @author Nassim BOUKHARI
	 */
	 public ConnectionDAO() {
			config = new HikariConfig();
			config.setJdbcUrl( "jdbc:mysql://localhost/computer-database-db" );
	        config.setUsername( "admincdb" );
	        config.setPassword( "qwerty1234" );
	        config.setDriverClassName("com.mysql.jdbc.Driver");
	        config.setMaxLifetime(60000);
	        config.addDataSourceProperty( "cachePrepStmts" , "true" );
	        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
	        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
	        datasource = new HikariDataSource( config );
	 }
	 
	public Connection getConnection() throws IOException, SQLException {
		return datasource.getConnection();
	}
}