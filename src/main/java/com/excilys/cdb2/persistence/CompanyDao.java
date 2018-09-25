package com.excilys.cdb2.persistence;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb2.model.Company;
import com.excilys.cdb2.model.CompanyBuilder;
import com.excilys.cdb2.persistence.ComputerDao;
//import model.Company;

/**
 * This class does all the functionnalities about companies
 * @author Nassim BOUKHARI
 */
public class CompanyDao {

	private static final String GET_ALL = "SELECT id,name FROM company";

	/**
	 * This method displays all the companies
	 * @author Nassim BOUKHARI
	 * @return 
	 */
	public static List<Company> getAllCompanies() throws IOException {
		Company company;
		ArrayList<Company> companies = new ArrayList<Company>();
		CompanyBuilder companyBuilder = new CompanyBuilder();

		try (Connection cn = ComputerDao.getConnection()){

			PreparedStatement ppdStmt = cn.prepareStatement(GET_ALL);
			ResultSet rs = ppdStmt.executeQuery(GET_ALL);
			while(rs.next()) {
				long compId = rs.getLong("id");
				String name = rs.getString("name");

				System.out.println(compId+"|"+name);

				companyBuilder.setId(compId);
				companyBuilder.setName(name);
				company = companyBuilder.build();
				companies.add(company);
			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return companies;
	}
}