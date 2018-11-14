package com.excilys.cdb2.persistence;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb2.exception.ValidationException;
import com.excilys.cdb2.model.Computer;
import com.excilys.cdb2.model.QCompany;
import com.excilys.cdb2.model.QComputer;
import com.querydsl.jpa.impl.JPAQueryFactory;

/**
 * This class does all the functionnalities about computers
 * 
 * @author Nassim BOUKHARI
 */
@Repository
@Transactional
public class ComputerDao {

	private EntityManager entityManager;

	private QComputer qcomputer;
	private QCompany qcompany;

	@Autowired
	public ComputerDao(EntityManagerFactory entityManagerFactory) {
		qcomputer = QComputer.computer;
		qcompany = QCompany.company;
		this.entityManager = entityManagerFactory.createEntityManager();
	}

	/**
	 * This method displays all the computers
	 * 
	 * @author Nassim BOUKHARI
	 */
	public List<Computer> getAllComputers(long limitPage, long numPage) {

		JPAQueryFactory query = new JPAQueryFactory(entityManager);
		List<Computer> computers = query.selectFrom(qcomputer)
										.limit(limitPage)
										.offset(numPage)
										.fetch();
		return computers;
	}

	/**
	 * This method displays all the computers
	 * 
	 * @author Nassim BOUKHARI
	 */
	public List<Computer> searchComputers(String search, long limitPage, long numPage) {

		JPAQueryFactory query = new JPAQueryFactory(entityManager);
		List<Computer> computers = query.selectFrom(qcomputer)
										.where(qcomputer.name
												.like("%" + search + "%")
												.or(qcomputer.company.name
												.like("%" + search + "%")))
										.limit(limitPage)
										.offset(numPage)
										.fetch();
		return computers;

	}

	/**
	 * This method displays all the details about a computer
	 * 
	 * @author Nassim BOUKHARI
	 */
	public Computer getComputerDetails(Computer computer) {

		JPAQueryFactory query = new JPAQueryFactory(entityManager);
		return query.selectFrom(qcomputer)
					.leftJoin(qcompany)
					.on(qcompany.id.eq(qcomputer.company.id))
					.where(qcomputer.id.eq(computer.getId()))
					.fetchOne();

	}

	/**
	 * This method creates a computer
	 * 
	 * @author Nassim BOUKHARI
	 * @throws IOException
	 * @throws ParseException
	 * @throws ValidationException
	 * @throws ClassNotFoundException
	 */
	public void setComputer(Computer computer) {
		entityManager.getTransaction().begin();
		System.out.println("computer"+computer); 
		entityManager.persist(computer);
		entityManager.getTransaction().commit();
	}

	/**
	 * This method updates a computer
	 * 
	 * @author Nassim BOUKHARI
	 * @throws IOException
	 * @throws ParseException
	 * @throws ClassNotFoundException
	 */
	public void updateComputer(Computer computer) {

		JPAQueryFactory query = new JPAQueryFactory(entityManager);
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
	 * 
	 * @author Nassim BOUKHARI
	 */
	public void removeComputer(List<Long> ids) {

		for (Long id : ids) {
			JPAQueryFactory query = new JPAQueryFactory(entityManager);
			entityManager.getTransaction().begin();
			query.delete(qcomputer)
				 .where((qcomputer.id.eq(id)))
				 .execute();
			entityManager.getTransaction().commit();
		}
	}

	/**
	 * This method displays number of computers
	 * 
	 * @author Nassim BOUKHARI
	 */
	public int getComputersCount() {
		JPAQueryFactory query = new JPAQueryFactory(entityManager);
		return (int) query.selectFrom(qcomputer).fetchCount();
	}

	/**
	 * This method displays computers from the search
	 * 
	 * @author Nassim BOUKHARI
	 */
	public int getComputersCountFromSearch(String search) {

		JPAQueryFactory query = new JPAQueryFactory(entityManager);
		return (int) query.selectFrom(qcomputer)
						  .where(qcomputer.name
						  		.like("%" + search + "%")
								.or(qcomputer.company.name
								.like("%" + search + "%")))
				     	  .fetchCount();
	}
}