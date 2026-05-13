package it.unisa.storage.model;

import java.io.Serializable;

public class OrdineBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id_ordine;
	private String data_ordine;
	private enum Stato{CONSEGNATO, IN_SPEDIZIONE, ANNULLATO, IN_PREPARAZIONE};
	private Stato stato;
	private double prezzo_totale;
	private UtenteBean id_utente;
	private IndirizzoBean id_indirizzo;
	
	
	public OrdineBean()
	{
		
	}

	public String getId_ordine() {
		return id_ordine;
	}

	public void setId_ordine(String id_ordine) {
		this.id_ordine = id_ordine;
	}

	public String getData_ordine() {
		return data_ordine;
	}

	public void setData_ordine(String data_ordine) {
		this.data_ordine = data_ordine;
	}

	public Stato getStato() {
		return stato;
	}

	public void setStato(Stato stato) {
		this.stato = stato;
	}

	public double getPrezzo_totale() {
		return prezzo_totale;
	}

	public void setPrezzo_totale(double prezzo_totale) {
		this.prezzo_totale = prezzo_totale;
	}

	public UtenteBean getId_utente() {
		return id_utente;
	}

	public void setId_utente(UtenteBean id_utente) {
		this.id_utente = id_utente;
	}

	public IndirizzoBean getId_indirizzo() {
		return id_indirizzo;
	}

	public void setId_indirizzo(IndirizzoBean id_indirizzo) {
		this.id_indirizzo = id_indirizzo;
	}
	
	
}
