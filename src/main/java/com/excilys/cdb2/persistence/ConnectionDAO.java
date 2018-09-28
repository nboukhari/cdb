package com.excilys.cdb2.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionDAO {

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
			String URL = prop.getProperty("URL");
			String LOGIN = prop.getProperty("LOGIN");
			String PASSWORD = prop.getProperty("PASSWORD");
			cn = DriverManager.getConnection(URL, LOGIN, PASSWORD);
			return cn;
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
		return cn;
	}
}
