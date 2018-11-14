package com.excilys.cdb2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.cdb2.model.Role;
import com.excilys.cdb2.persistence.RoleDao;

public class RoleServices {
	
	@Autowired
	private RoleDao roleDao;
	
	/**
	 * This method displays all the roles
	 * @author Nassim BOUKHARI
	 */
	public List<Role> showUsers(){
		List<Role> roles = roleDao.getAllRoles();
		return roles;
	}
}
