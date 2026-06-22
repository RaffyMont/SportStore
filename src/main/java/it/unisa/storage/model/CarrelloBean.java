package it.unisa.storage.model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class CarrelloBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Map<String, CarrelloItemBean> items = new LinkedHashMap<>();

    public CarrelloBean() {}

    public Map<String, CarrelloItemBean> getItems() { 
    	return items; 
    }

    public void aggiungi(CarrelloItemBean item) {
        String chiave = item.getChiave();
        
        if (items.containsKey(chiave))
            items.get(chiave).setQuantita(items.get(chiave).getQuantita() + item.getQuantita());
        else
            items.put(chiave, item);
    }

    public void rimuovi(String chiave) {
        items.remove(chiave);
    }

    public void aggiorna(String chiave, int quantita) {
        if (quantita <= 0)
            items.remove(chiave);
        else if (items.containsKey(chiave))
            items.get(chiave).setQuantita(quantita);
    }

    public void svuota() {
        items.clear();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int getNumeroArticoli() {
        return items.values().stream().mapToInt(item -> item.getQuantita()).sum();
    }

    public double getTotale() {
        return items.values().stream().mapToDouble(item -> item.getTotale()).sum();
    }
}