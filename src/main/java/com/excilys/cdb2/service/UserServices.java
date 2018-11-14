package com.excilys.cdb2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.cdb2.model.User;
import com.excilys.cdb2.persistence.UserDao;

public class UserServices {

	@Autowired
	private UserDao userDao;
	
	/**
	 * This method displays all the users
	 * @author Nassim BOUKHARI
	 */
	public List<User> showUsers(){
		List<User> users = userDao.getAllUsers();
		return users;
	}
}
