package com.excilys.cdb2.persistence;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.cdb2.mapper.CompanyMapper;
import com.excilys.cdb2.mapper.ComputerMapper;
import com.excilys.cdb2.model.Company;
import com.excilys.cdb2.model.CompanyBuilder;
import com.excilys.cdb2.model.Computer;

/**
 * This class does all the functionnalities about companies
 * @author Nassim BOUKHARI
 */
public class CompanyDao {

	private static final String GET_ALL = "SELECT id,name FROM company";
	private static final String GET_ID_COMPANY = "SELECT id FROM company WHERE name =?;";

	/**
	 * This method displays all the companies
	 * @author Nassim BOUKHARI
	 * @return 
	 */
	public static List<Company> getAllCompanies() throws IOException {
		Company company;
		ArrayList<Company> companies = new ArrayList<Company>();
		CompanyBuilder companyBuilder = new CompanyBuilder();

		try (Connection cn = ConnectionDAO.getConnection()){

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
	
	/**
	 * This method get the Id of the company from his name
	 * @author Nassim BOUKHARI
	 */
	public static long getCompanyId(String CompanyName) throws SQLException, IOException {
		int IdComp = 0;
		try (Connection cn = ConnectionDAO.getConnection()){
			Optional<String> newCompany = ComputerMapper.enterCompanyName(CompanyName);
			Computer computer = CompanyMapper.CompName(newCompany);
			PreparedStatement ppdStmt = cn.prepareStatement(GET_ID_COMPANY);
			if(computer.getCompanyName().isPresent())
				ppdStmt.setString(1, computer.getCompanyName().get().toString());
			else
				ppdStmt.setInt(1, 0);
			ResultSet rs = ppdStmt.executeQuery();
			if(rs.next()) {
				IdComp = rs.getInt(1);
			}
			else {
				System.out.println("Y A PAS");
			}
		}

		catch (SQLException e) {
			e.printStackTrace();
		}
		return IdComp;
	}

}