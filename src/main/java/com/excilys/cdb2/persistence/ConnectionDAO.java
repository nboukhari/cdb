package com.excilys.cdb2.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionDAO {
	
	//private final static Logger logger = LoggerFactory.getLogger("ConnectionDAO");
	private static HikariConfig config;
	private static HikariDataSource datasource;
	
	/**
	 * This method connects to the database
	 * @author Nassim BOUKHARI
	 */
	

	static {
		config = new HikariConfig();
		config.setJdbcUrl( "jdbc:mysql://localhost/computer-database-db" );
        config.setUsername( "admincdb" );
        config.setPassword( "qwerty1234" );
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setMaxLifetime(6000);
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        datasource = new HikariDataSource( config );
	}
	
	 private ConnectionDAO() {
	 }
	 
	public static Connection getConnection() throws IOException, SQLException {
		
		return datasource.getConnection();
	}
}