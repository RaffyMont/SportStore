package it.unisa.storage.model;

import java.io.Serializable;

public class ProdottoBean implements Serializable{
	private String id_prodotto;
    private String modello;
    private String descrizione;
    private double prezzo; 
    private String marca;
    private enum Genere{uomo, donna, bambino, unisex};
    private Genere genere;
    private int stock;
    
    public ProdottoBean()
    {
    	prezzo = 0.0;
    	genere = Genere.unisex;
    	stock = 0;
    }

	public String getId_prodotto() {
		return id_prodotto;
	}

	public void setId_prodotto(String id_prodotto) {
		this.id_prodotto = id_prodotto;
	}

	public String getModello() {
		return modello;
	}

	public void setModello(String modello) {
		this.modello = modello;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public Genere getGenere() {
		return genere;
	}

	public void setGenere(Genere genere) {
		this.genere = genere;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
   
    
}
