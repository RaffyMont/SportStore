package it.unisa.storage.model;

import java.io.Serializable;

public class TagliaBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String taglia;
	
	public TagliaBean()
	{
		
	}

	public String getTaglia() {
		return taglia;
	}

	public void setTaglia(String taglia) {
		this.taglia = taglia;
	}
	

}
