package com.excilys.cdb2.persistence;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
//import org.apache.log4j.Logger;

import com.excilys.cdb2.exception.ValidationException;
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
	private static final String GET_ID_COMPANY = "SELECT id FROM company WHERE name =?";
	//private final static Logger LOGGER = Logger.getLogger(CompanyDao.class);

	/**
	 * This method displays all the companies
	 * @author Nassim BOUKHARI
	 * @return 
	 * @throws ValidationException 
	 * @throws ClassNotFoundException 
	 */
	public static List<Company> getAllCompanies() throws IOException, ValidationException, ClassNotFoundException {
		Company company;
		ArrayList<Company> companies = new ArrayList<Company>();
		CompanyBuilder companyBuilder = new CompanyBuilder();

		try (Connection cn = ConnectionDAO.getConnection()){

			PreparedStatement ppdStmt = cn.prepareStatement(GET_ALL);
			ResultSet rs = ppdStmt.executeQuery(GET_ALL);
			while(rs.next()) {
				long compId = rs.getLong("id");
				String name = rs.getString("name");
				companyBuilder.setId(compId);
				companyBuilder.setName(name);
				company = companyBuilder.build();
				companies.add(company);
			}
			rs.close();

		} catch (SQLException e) {
			//LOGGER.error("Une erreur SQL est survenue, voici la cause : "+e);
			throw new ValidationException("Une erreur est survenue lors de l'affichage des ordinateurs.");
		}
		return companies;
	}
	
	/**
	 * This method get the Id of the company from his name
	 * @author Nassim BOUKHARI
	 * @throws ValidationException 
	 * @throws ClassNotFoundException 
	 */
	public static long getCompanyId(String companyName) throws SQLException, IOException, ValidationException, ClassNotFoundException {
		int idComp = 0;
		try (Connection cn = ConnectionDAO.getConnection()){
			Optional<String> newCompany = ComputerMapper.enterCompanyName(companyName);
			Computer computer = ComputerMapper.compName(newCompany);
			PreparedStatement ppdStmt = cn.prepareStatement(GET_ID_COMPANY);
			if(computer.getCompanyName().isPresent())
				ppdStmt.setString(1, computer.getCompanyName().get().toString());
			else
				ppdStmt.setInt(1, 0);
			ResultSet rs = ppdStmt.executeQuery();
			if(rs.next()) {
				idComp = rs.getInt(1);
			}
			else {
				//LOGGER.error("Aucune entreprise n'a été trouvé.");
			}
		}

		catch (SQLException e) {
			//LOGGER.error("Une erreur SQL est survenue, voici la cause : "+e);
			throw new ValidationException("Une erreur est survenue lors de l'affichage des ordinateurs.");
		}
		return idComp;
	}

}