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
	
	public int numPageMinusOne(int numPage) {
		return --numPage;
	}
	
	public int numPageMinusTwo(int numPage) {
		return numPage-=2;
	}
	
	public int numPagePlusOne(int numPage) {
		return ++numPage;
	}
	
	public int numPagePlusTwo(int numPage) {
		return numPage+=2;
	}

}
