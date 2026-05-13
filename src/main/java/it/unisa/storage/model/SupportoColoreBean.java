package it.unisa.storage.model;

import java.io.Serializable;

public class SupportoColoreBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private ProdottoBean id_prodotto;
	private ColoreBean nome;
	
	public SupportoColoreBean()
	{
		
	}

	public ProdottoBean getId_prodotto() {
		return id_prodotto;
	}

	public void setId_prodotto(ProdottoBean id_prodotto) {
		this.id_prodotto = id_prodotto;
	}

	public ColoreBean getNome() {
		return nome;
	}

	public void setNome(ColoreBean nome) {
		this.nome = nome;
	}
	
	
}
