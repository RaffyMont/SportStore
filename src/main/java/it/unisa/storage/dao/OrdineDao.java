package it.unisa.storage.dao;

import java.sql.SQLException;
import java.util.Collection;

import it.unisa.storage.model.OrdineBean;
import it.unisa.storage.model.OrdineBean.Stato;

public interface OrdineDao {
	
	public void doSave(OrdineBean ordine) throws SQLException;
	
	public boolean doUpdate(OrdineBean ordine) throws SQLException;

	public Collection<OrdineBean> doRetrieveAll() throws SQLException;
	
	public Collection<OrdineBean> doRetrieveAllByUser(String id_utente) throws SQLException;
	
	public boolean setStatusOrdine(String id_ordine, Stato stato_ordine) throws SQLException;

}
