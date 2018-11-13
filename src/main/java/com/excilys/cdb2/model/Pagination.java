package com.excilys.cdb2.model;

import org.springframework.stereotype.Repository;

@Repository
public class Pagination {

	public static int numberOfPage(String page, String limit) {
		int nbP = Integer.parseInt(page);
		int limitNumber = Integer.parseInt(limit);
		nbP = (nbP-1)*limitNumber;
		return nbP;
	}
	
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
