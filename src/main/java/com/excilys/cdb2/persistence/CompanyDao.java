package com.excilys.cdb2.persistence;

import java.io.IOException;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.hibernate.HibernateQuery;
import com.querydsl.jpa.hibernate.HibernateQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.excilys.cdb2.exception.ValidationException;
import com.excilys.cdb2.mapper.CompanyMapper;
import com.excilys.cdb2.mapper.ComputerMapper;
import com.excilys.cdb2.model.Company;
import com.excilys.cdb2.model.Computer;
import com.excilys.cdb2.model.QCompany;
import com.excilys.cdb2.model.QComputer;
import com.google.common.base.Supplier;


/**
 * This class does all the functionnalities about companies
 * @author Nassim BOUKHARI
 */
@Repository
@Transactional
public class CompanyDao {

	private static final String GET_ALL = "SELECT id,name FROM company";
	private static final String GET_ID_COMPANY = "SELECT id FROM company WHERE name =?";
	private static final String DELETE = "DELETE FROM company WHERE id =?";
	
	
	private QCompany qcompany;
	private EntityManager entityManager;
//	private static QComputer qcomputer = QComputer.computer;
//	private static QCompany qcompany = QCompany.company;
//	private Supplier<HibernateQueryFactory> queryFactory =
//			() -> new HibernateQueryFactory(sessionFactory.getCurrentSession());
	
//	@Autowired
//	public void setSessionFactory(SessionFactory sessionFactory) {
//		this.sessionFactory = sessionFactory;
//	}

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
	 * This method get the Id of the company from his name
	 * @author Nassim BOUKHARI
	 */
	public long getCompanyId(String companyName) {
			String comp;
			String newCompany = ComputerMapper.enterCompanyName(companyName);
			return 1; 
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
		//queryFactory.get().delete(qcomputer).where(qcomputer.company.id.eq(id)).execute();
		//queryFactory.get().delete(qcompany).where(qcompany.id.eq(id)).execute();
	}
	
	private Company retrieveCompanyFromQuery(ResultSet rs) throws SQLException {
        return CompanyMapper.company(rs.getLong(1), rs.getString(2));
    }

}