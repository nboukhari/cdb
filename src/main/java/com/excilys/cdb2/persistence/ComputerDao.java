package com.excilys.cdb2.persistence;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb2.exception.ValidationException;
import com.excilys.cdb2.mapper.ComputerMapper;
import com.excilys.cdb2.model.Company;
import com.excilys.cdb2.model.Computer;
import com.excilys.cdb2.model.QCompany;
import com.excilys.cdb2.model.QComputer;
import com.google.common.base.Supplier;
import com.querydsl.jpa.hibernate.HibernateQuery;
import com.querydsl.jpa.hibernate.HibernateQueryFactory;
//import com.querydsl.jpa.hibernate.HibernateQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;

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
	
	
	private EntityManager entityManager; 
	
	
	public HibernateQueryFactory queryFactory;
	private QComputer qcomputer;
	private QCompany qcompany;

	@Autowired
	private PlatformTransactionManager tx;
	
	@Autowired
	public ComputerDao(EntityManagerFactory entityManagerFactory) {
		qcomputer = QComputer.computer;
		qcompany = QCompany.company;
		this.entityManager = entityManagerFactory.createEntityManager();
	}

	/**
	 * This method displays all the computers
	 * @author Nassim BOUKHARI
	 */
	public List<Computer> getAllComputers(String numberOfPage, String limitData){
		
		JPAQueryFactory query = new JPAQueryFactory(entityManager);
			long numPage = ComputerMapper.numberOfPage(numberOfPage, limitData);
			long limitPage = Integer.parseInt(limitData);
			List<Computer> computers = query.selectFrom(qcomputer).limit(numPage).offset(limitPage).fetch();
		return computers;
	}
	
	/**
	 * This method displays all the computers
	 * @author Nassim BOUKHARI
	 */
	public List<Computer> searchComputers(String search, String numberOfPage, String limitData){
		JPAQueryFactory query = new JPAQueryFactory(entityManager);
			long numPage = ComputerMapper.numberOfPage(numberOfPage, limitData);
			long limitPage = Integer.parseInt(limitData);
			
			List<Computer> computers = query.selectFrom(qcomputer).where(qcomputer.name.like("%"+ search + "%").or(qcomputer.company.name.like("%"+ search + "%"))).limit(numPage).offset(limitPage).fetch();
		return computers;
	}

	/**
	 * This method displays all the details about a computer
	 * @author Nassim BOUKHARI
	 */
	public Computer getComputerDetails(String idPC){
		JPAQueryFactory query = new JPAQueryFactory(entityManager);
		long id = Integer.parseInt(idPC);
		return query.selectFrom(qcomputer)
				.leftJoin(qcompany)
				.on(qcompany.id.eq(qcomputer.company.id))
				.where(qcomputer.id.eq(id))
				.fetchOne();
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
			LocalDate introducedPC = ComputerMapper.enterDate(introducedStr);
			LocalDate discontinuedPC = ComputerMapper.enterDate(discontinuedStr);
			
			Computer computer = ComputerMapper.StringPC(namePC, introducedPC, discontinuedPC,companyNameStr);
			entityManager.getTransaction().begin();
			entityManager.persist(computer);
			entityManager.getTransaction().commit();
	
	}


	/**
	 * This method updates a computer
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws ClassNotFoundException 
	 */
	public void updateComputer(String idPC, String namePC, String introducedStr, String discontinuedStr, String companyNameStr) throws ParseException {
		JPAQueryFactory query = new JPAQueryFactory(entityManager);
			Computer computer = ComputerMapper.StringToComputer(idPC, namePC, introducedStr, discontinuedStr, companyNameStr);
			entityManager.getTransaction().begin();
			query.update(qcomputer).where(qcomputer.id.eq(computer.getId()))
			 .set(qcomputer.name, computer.getName())
			 .set(qcomputer.introduced, computer.getIntroduced())
			 .set(qcomputer.discontinued, computer.getDiscontinued())
			 .set(qcomputer.company, computer.getCompany())
			 .execute();
			entityManager.getTransaction().commit();

	
	}


	/**
	 * This method deletes a computer
	 * @author Nassim BOUKHARI
	 */
	public void removeComputer(List<Long> ids) {

		for (Long id : ids) {
			JPAQueryFactory query = new JPAQueryFactory(entityManager);
			entityManager.getTransaction().begin();
			query
			.delete(qcomputer)
			.where((qcomputer.id.eq(id)))
			.execute();
			entityManager.getTransaction().commit();
		}
	}

	/**
	 * This method displays number of computers
	 * @author Nassim BOUKHARI
	 */
	public int getComputersCount() {
		JPAQueryFactory query = new JPAQueryFactory(entityManager);
		return  (int) query.selectFrom(qcomputer).fetchCount();
	}
	
	/**
	 * This method displays computers from the search
	 * @author Nassim BOUKHARI
	 */
	public int getComputersCountFromSearch(String search){

		JPAQueryFactory query = new JPAQueryFactory(entityManager);
		return  (int) query.selectFrom(qcomputer)
				.where(qcomputer.name
						.like("%"+search + "%")
						.or(qcomputer.company.name
						.like("%"+search + "%")))
				.fetchCount();
	}
}