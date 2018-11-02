package com.excilys.cdb2.persistence;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb2.exception.ValidationException;
import com.excilys.cdb2.mapper.ComputerMapper;
import com.excilys.cdb2.model.Computer;
import com.excilys.cdb2.model.QCompany;
import com.excilys.cdb2.model.QComputer;
import com.google.common.base.Supplier;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.hibernate.HibernateQueryFactory;

/**
 * This class does all the functionnalities about computers
 * @author Nassim BOUKHARI
 */
@Repository
@Transactional
public class ComputerDao {
	private static final String GET_ALL = "select computer.id, computer.name, computer.introduced, computer.discontinued, company.name from computer LEFT JOIN company on company.id = computer.company_id LIMIT ?,?";
	private static final String SEARCH = "select computer.id, computer.name, computer.introduced, computer.discontinued, company.name from computer LEFT JOIN company on company.id = computer.company_id WHERE computer.name LIKE ? OR company.name LIKE ? LIMIT ?,?";
	private static final String GET_ONE = "select computer.id, computer.name, computer.introduced, computer.discontinued, company.name from computer LEFT JOIN company on company.id = computer.company_id WHERE computer.id =?;";
	private static final String INSERT = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?);";
	private static final String UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id =?;";
	private static final String DELETE = "DELETE FROM computer WHERE id = ?;";
	private static final String DELETE_COMPUTERS_COMPANY = "DELETE FROM computer WHERE company_id =?";
	private static final String SEARCH_COUNT = "SELECT COUNT(*) FROM computer LEFT JOIN company on company.id = computer.company_id WHERE computer.name LIKE ? OR company.name LIKE ?";
	private static final String COUNT = "SELECT COUNT(*) FROM computer";
	
	JdbcTemplate jdbcTemplate;

	private SessionFactory sessionFactory;
	private static QComputer qcomputer = QComputer.computer;
	private static QCompany qcompany = QCompany.company;
	private Supplier<HibernateQueryFactory> queryFactory =
			() -> new HibernateQueryFactory(sessionFactory.getCurrentSession());
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Autowired
	public ComputerDao(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/**
	 * This method displays all the computers
	 * @author Nassim BOUKHARI
	 */
	public List<Computer> getAllComputers(String NumberOfPage, String LimitData){


			int numPage = ComputerMapper.numberOfPage(NumberOfPage, LimitData);
			long limitPage = Integer.parseInt(LimitData);
			
			List<Computer> computers = jdbcTemplate.query(GET_ALL,
	                preparedStatement -> {
	                    preparedStatement.setInt(1, numPage);
	                    preparedStatement.setLong(2, limitPage);
	                }, (resultSet, rowNum) -> {
	                    return retrieveComputerFromQuery(resultSet);
	                });
		return computers;

	}
	
	/**
	 * This method displays all the computers
	 * @author Nassim BOUKHARI
	 */
	public List<Computer> searchComputers(String search, String numberOfPage, String limitData){


			int numPage = ComputerMapper.numberOfPage(numberOfPage, limitData);
			long limitPage = Integer.parseInt(limitData);
			List<Computer> computers = jdbcTemplate.query(SEARCH,
	                preparedStatement -> {
	                	preparedStatement.setString(1, "%"+search+"%");
	                	preparedStatement.setString(2, "%"+search+"%");
	                    preparedStatement.setInt(3, numPage);
	                    preparedStatement.setLong(4, limitPage);
	                }, (resultSet, rowNum) -> {
	                    return retrieveComputerFromQuery(resultSet);
	                });
		return computers;

	}

	/**
	 * This method displays all the details about a computer
	 * @author Nassim BOUKHARI
	 */
	public Computer getComputerDetails(String idPC){
		
		long id = Integer.parseInt(idPC);
		return jdbcTemplate.queryForObject(GET_ONE, new Object[] {id}, (resultSet, rowNum) -> retrieveComputerFromQuery(resultSet));
		
	}

	/**
	 * This method creates a computer
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws ValidationException 
	 * @throws ClassNotFoundException 
	 */
	public void setComputer(String namePC, String introducedStr, String discontinuedStr, String companyNameStr) throws IOException, ParseException, ClassNotFoundException {

			String companyName = ComputerMapper.enterCompanyName(companyNameStr);
			LocalDate introducedPC = ComputerMapper.enterDate(introducedStr);
			LocalDate discontinuedPC = ComputerMapper.enterDate(discontinuedStr);
			Computer computer = ComputerMapper.updatePC(namePC, introducedPC, discontinuedPC,companyNameStr);
			sessionFactory.getCurrentSession().save(computer);
	
	}


	/**
	 * This method updates a computer
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws ClassNotFoundException 
	 */
	public void updateComputer(String idPC, String namePC, String introducedStr, String discontinuedStr, String companyNameStr) throws ParseException {


			long newIdPC = Integer.parseInt(idPC);
			Optional<LocalDate> newDateDebut = ComputerMapper.enterDate(introducedStr);
			Optional<LocalDate> newDateEnd = ComputerMapper.enterDate(discontinuedStr);
			Optional<String> newCompany = ComputerMapper.enterCompanyName(companyNameStr);
			Computer computer = new Computer(newIdPC, namePC, newDateDebut, newDateEnd,newCompany);
			
			queryFactory.get().update(qcomputer)
			 .where(qcomputer.id.eq(newIdPC))
			 .set(qcomputer.name, computer.getName())
			 .set(qcomputer.introduced, computer.getIntroduced())
			 .set(qcomputer.discontinued, computer.getDiscontinued())
			 .set(qcomputer.companyName, computer.getCompanyName())
			 .execute();
	
	}


	/**
	 * This method deletes a computer
	 * @author Nassim BOUKHARI
	 */
	public void removeComputer(List<Long> ids) {

		for (Long id : ids) {
			queryFactory.get().delete(qcomputer).where(qcomputer.id.eq(id)).execute();
		}
	}
	
	/**
	 * This method deletes a computer that is in a company
	 * @author Nassim BOUKHARI
	 */
	public void removeComputerFromCompany(long idC) {
		
		jdbcTemplate.update(DELETE_COMPUTERS_COMPANY);
	}
	

	/**
	 * This method displays number of computers
	 * @author Nassim BOUKHARI
	 */
	public int getComputersCount() {

		return (int) queryFactory.get().select(qcomputer).from(qcomputer).fetchCount();
	}
	
	/**
	 * This method displays computers from the search
	 * @author Nassim BOUKHARI
	 */
	public int getComputersCountFromSearch(String search){

		return (int) queryFactory.get().select(qcomputer).from(qcomputer)
				.where(qcomputer.name.like("%"+search + "%"))
				.fetchCount();
	}
	
	private Computer retrieveComputerFromQuery(ResultSet rs) throws SQLException {
        return ComputerMapper.updatePC(rs.getLong(1), rs.getString(2), rs.getDate(3), rs.getDate(4), rs.getString(5));
    }
}