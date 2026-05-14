package it.unisa.storage.model;

import java.io.Serializable;

public abstract class ProdottoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id_prodotto;
    private String modello;
    private String descrizione;
    private double prezzo; 
    private String marca;
    public enum Categoria{VESTITO, SCARPA, ACCESSORIO};
    private Categoria categoria;
    public enum Genere{UOMO, DONNA, BAMBINO, UNISEX};
    private Genere genere;
    private int stock;
    private boolean attivo;
    
    public ProdottoBean()
    {
   
    }

	public boolean isAttivo() {
		return attivo;
	}

	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
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
