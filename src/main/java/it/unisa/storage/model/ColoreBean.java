package it.unisa.storage.model;

import java.io.Serializable;

public class ColoreBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private String colore;
	
	public ColoreBean()
	{
		
	}

	public String getColore() {
		return colore;
	}

	public void setColore(String colore) {
		this.colore = colore;
	}

	
}
