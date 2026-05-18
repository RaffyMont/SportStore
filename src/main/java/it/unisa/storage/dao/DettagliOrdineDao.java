package it.unisa.storage.dao;

import java.sql.SQLException;
import java.util.Collection;

import it.unisa.storage.model.DettagliOrdineBean;


public interface DettagliOrdineDao {
	
	public void doSave(DettagliOrdineBean dettagli) throws SQLException;
	
	public boolean doUpdate(DettagliOrdineBean dettagli) throws SQLException;

    public boolean doDelete(String id_ordine, String id_prodotto) throws SQLException;

    public DettagliOrdineBean doRetrieveByKey(String id_ordine, String id_prodotto) throws SQLException;
    
    public Collection<DettagliOrdineBean> doRetrieveAllByOrdine(String id_ordine) throws SQLException;
}
