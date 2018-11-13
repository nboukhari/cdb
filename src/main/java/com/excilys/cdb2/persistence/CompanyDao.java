package com.excilys.cdb2.persistence;

import java.io.IOException;

import java.sql.SQLException;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.excilys.cdb2.exception.ValidationException;
import com.excilys.cdb2.model.Company;
import com.excilys.cdb2.model.QCompany;
import com.excilys.cdb2.model.QComputer;


/**
 * This class does all the functionnalities about companies
 * @author Nassim BOUKHARI
 */
@Repository
@Transactional
public class CompanyDao {
	
	private QCompany qcompany;
	private QComputer qcomputer;
	private EntityManager entityManager;

	@Autowired
	public CompanyDao(EntityManagerFactory entityManagerFactory) {
		qcompany = QCompany.company;
		this.entityManager = entityManagerFactory.createEntityManager();
	}
	
	/**
	 * This method displays all the companies
	 * @author Nassim BOUKHARI
	 * @return 
	 * @throws ValidationException 
	 * @throws ClassNotFoundException 
	 */
	public List<Company> getAllCompanies() throws IOException, ValidationException, ClassNotFoundException {
		JPAQueryFactory query = new JPAQueryFactory(entityManager);
		List<Company> companies = query.selectFrom(qcompany).fetch();
		return companies;
	}
	
	/**
	 * This method get the company from his id
	 * @author Nassim BOUKHARI
	 */
	public Company getCompanyById(int id) {
		Company company = new Company();
		company.setId(id);
		JPAQueryFactory query = new JPAQueryFactory(entityManager);
		return company = query.selectFrom(qcompany).where(qcompany.id.eq(company.getId())).fetchOne();
	}
	
	/**
	 * This method deletes a company
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 * @throws ValidationException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public void removeCompany(long id) throws IOException, ValidationException, ClassNotFoundException, SQLException {
		
		JPAQueryFactory query = new JPAQueryFactory(entityManager);
		
		entityManager.getTransaction().begin();
		query.delete(qcomputer).where((qcomputer.company.id.eq(id))).execute();
		entityManager.getTransaction().commit();
		
		entityManager.getTransaction().begin();
		query.delete(qcompany).where((qcompany.id.eq(id))).execute();
		entityManager.getTransaction().commit();
		
	}
}