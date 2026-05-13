package it.unisa.storage.model;

import java.io.Serializable;

public class DettagliOrdineBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private int quantita;
	private double prezzo_unitario;
	private OrdineBean id_ordine;
	private ProdottoBean id_prodotto;
	
	public DettagliOrdineBean()
	{
		quantita = 0;
		prezzo_unitario = 0.0;
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	public double getPrezzo_unitario() {
		return prezzo_unitario;
	}

	public void setPrezzo_unitario(double prezzo_unitario) {
		this.prezzo_unitario = prezzo_unitario;
	}

	public OrdineBean getId_ordine() {
		return id_ordine;
	}

	public void setId_ordine(OrdineBean id_ordine) {
		this.id_ordine = id_ordine;
	}

	public ProdottoBean getId_prodotto() {
		return id_prodotto;
	}

	public void setId_prodotto(ProdottoBean id_prodotto) {
		this.id_prodotto = id_prodotto;
	}
	
}
