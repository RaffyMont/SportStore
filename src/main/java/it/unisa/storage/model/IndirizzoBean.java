package it.unisa.storage.model;

import java.io.Serializable;

public class IndirizzoBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int id_indirizzo;
    private String provincia;
    private String stato;
	private String citta;
    private String CAP;
    private String via;
    private String civico;
    
    public IndirizzoBean()
    {
    	
    }

	public int getId_indirizzo() {
		return id_indirizzo;
	}

	public void setId_indirizzo(int id_indirizzo) {
		this.id_indirizzo = id_indirizzo;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public String getCAP() {
		return CAP;
	}

	public void setCAP(String cAP) {
		CAP = cAP;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public String getCivico() {
		return civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}
}
