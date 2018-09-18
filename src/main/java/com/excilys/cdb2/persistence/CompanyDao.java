package com.excilys.cdb2.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import model.Company;

/**
 * This class does all the functionnalities about companies
 * @author Nassim BOUKHARI
 */
public class CompanyDao {
	
	private static final String URL = "jdbc:mysql://localhost/computer-database-db";
	private static final String LOGIN = "admincdb";
	private static final String PASSWORD = "qwerty1234";
	private static Connection cn;
	private static Statement st;
	
	/**
	 * This method displays all the companies
	 * @author Nassim BOUKHARI
	 */
	public static void getAllCompanies() {
		try {
			cn = DriverManager.getConnection(URL, LOGIN, PASSWORD);
			st = cn.createStatement();
			String sql = "SELECT id,name FROM company";
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				System.out.println(rs.getInt(1)+"|"+rs.getString(2));
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}