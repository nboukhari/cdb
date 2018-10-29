package com.excilys.cdb2.model;

import org.springframework.stereotype.Repository;

@Repository
public class Pagination {

	public int nbPageMinusOne(int nbPage) {
		
		return --nbPage;
	}
	
	public int nbPageMinusTwo(int nbPage) {
		return nbPage-=2;
	}
	
	public int nbPageMoreOne(int nbPage) {
		return ++nbPage;
	}
	
	public int nbPageMoreTwo(int nbPage) {
		return nbPage+=2;
	}

}
