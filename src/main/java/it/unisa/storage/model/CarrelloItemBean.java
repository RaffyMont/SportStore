package it.unisa.storage.model;

import java.io.Serializable;

public class CarrelloItemBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id_prodotto;
    private String modello;
    private String marca;
    private double prezzo;
    private int quantita;
    private String colore;
    private String taglia;
    private String immagine;

    public CarrelloItemBean(){}

    public String getId_prodotto(){ 
    	return id_prodotto; 
    }
    
    public void setId_prodotto(String id_prodotto){ 
    	this.id_prodotto = id_prodotto; 
    }

    public String getModello(){
    	return modello;
    }
    
    public void setModello(String modello){ 
    	this.modello = modello; 
    }

    public String getMarca(){ 
    	return marca; 
    }
    
    public void setMarca(String marca){ 
    	this.marca = marca; 
    }

    public double getPrezzo(){ 
    	return prezzo; 
    }
    
    public void setPrezzo(double prezzo){ 
    	this.prezzo = prezzo; 
    }

    public int getQuantita(){ 
    	return quantita; 
    }
    
    public void setQuantita(int quantita){ 
    	this.quantita = quantita; 
    }

    public String getColore(){ 
    	return colore; 
    }
    
    public void setColore(String colore){ 
    	this.colore = colore; 
    }

    public String getTaglia(){ 
    	return taglia; 
    }
    
    public void setTaglia(String taglia){ 
    	this.taglia = taglia; 
    }

    public String getImmagine(){ 
    	return immagine; 
    }
    
    public void setImmagine(String immagine){ 
    	this.immagine = immagine; 
    }

    public double getTotale(){ 
    	return prezzo * quantita; 
    }

    public String getChiave(){
        return id_prodotto + "_" + (colore != null ? colore : "") + "_" + (taglia != null ? taglia : "");
    }
}
