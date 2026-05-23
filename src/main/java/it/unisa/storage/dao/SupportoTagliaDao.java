package it.unisa.storage.dao;

import java.sql.SQLException;
import java.util.Collection;

import it.unisa.storage.model.SupportoTagliaBean;


public interface SupportoTagliaDao {
	 	public void doSave(SupportoTagliaBean supporto) throws SQLException;
	    
	    public Collection<SupportoTagliaBean> doRetrieveAllByProdotto(String id_prodotto) throws SQLException;
}	
