package com.excilys.cdb2.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb2.model.QRole;
import com.excilys.cdb2.model.QUser;
import com.excilys.cdb2.model.User;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
@Transactional
public class UserDao {
	private EntityManager entityManager;

	private QRole qrole;
	private QUser quser;

	@Autowired
	public UserDao(EntityManagerFactory entityManagerFactory) {
		qrole = QRole.role;
		quser = QUser.user;
		this.entityManager = entityManagerFactory.createEntityManager();
	}
	
	public List<User> getAllUsers() {

		JPAQueryFactory query = new JPAQueryFactory(entityManager);
		List<User> users = query.selectFrom(quser)
										.fetch();
		return users;
	}
}
