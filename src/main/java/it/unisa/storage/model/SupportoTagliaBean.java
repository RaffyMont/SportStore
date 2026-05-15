package it.unisa.storage.model;

import java.io.Serializable;

public class SupportoTagliaBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private ProdottoBean id_prodotto;
	private TagliaBean taglia;
	
	public SupportoTagliaBean()
	{
	    this.taglia = new TagliaBean();
	}

	public ProdottoBean getId_prodotto() {
		return id_prodotto;
	}

	public void setId_prodotto(ProdottoBean id_prodotto) {
		this.id_prodotto = id_prodotto;
	}

	public TagliaBean getTaglia() {
		return taglia;
	}

	public void setTaglia(TagliaBean taglia) {
		this.taglia = taglia;
	}
	
	
}
