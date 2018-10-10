package com.excilys.cdb2.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionDAO {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(ConnectionDAO.class);
	 
	/**
	 * This method connects to the database
	 * @author Nassim BOUKHARI
	 */
	public static Connection getConnection() throws IOException, SQLException {
		Connection cn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} 
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		try {
			Properties prop = new Properties();
			InputStream inputStream = ComputerDao.class.getClassLoader().getResourceAsStream("config.properties");
			prop.load(inputStream);
			String url = prop.getProperty("URL");
			String login = prop.getProperty("LOGIN");
			String password = prop.getProperty("PASSWORD");
			cn = DriverManager.getConnection(url, login, password);
			return cn;
		} catch (Exception e) {
			 LOGGER.error("Exception ", e);
		}
		return cn;
	}
}
