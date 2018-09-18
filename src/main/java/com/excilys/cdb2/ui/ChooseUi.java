package com.excilys.cdb2.ui;

public enum ChooseUi {
	QUIT(0),
	LIST_ALL_COMPUTERS(1),
	LIST_ALL_COMPANIES(2),
	GET_COMPUTER_DETAILS(3),
	CREATE_COMPUTER(4),
	MODIFY_COMPUTER(5),
	REMOVE_COMPUTER(6);
	
	private int nbChoice;
	
	ChooseUi(int nbChoice) {
		this.nbChoice = nbChoice;
	}
	
	public int getInt() {
		return nbChoice;
	}
}
