package it.unisa.storage.dao;

import java.sql.SQLException;
import java.util.Collection;

import it.unisa.storage.model.SupportoTagliaBean;


public interface SupportoTagliaDao {
	 	public void doSave(SupportoTagliaBean supporto) throws SQLException;

	    public boolean doUpdate(SupportoTagliaBean indirizzo) throws SQLException;

	    public boolean doDelete(String id_prodotto, String taglia) throws SQLException;

	    public SupportoTagliaBean doRetrieveByProdotto(String id_prodotto) throws SQLException;
	    
	    public Collection<SupportoTagliaBean> doRetrieveAllByProtto() throws SQLException;
}	
