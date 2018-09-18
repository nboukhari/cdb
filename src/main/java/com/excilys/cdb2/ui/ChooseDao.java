package com.excilys.cdb2.ui;

public enum ChooseDao {
	QUIT(0),
	MODIFY_NAME(1),
	MODIFY_DEBUT(2),
	MODIFY_END(3);
	
	private int nbChoice;
	
	ChooseDao(int nbChoice) {
		this.nbChoice = nbChoice;
	}
	
	public int getInt() {
		return nbChoice;
	}
}