package it.unisa.storage.dao;

import java.sql.SQLException;
import java.util.Collection;

import it.unisa.storage.model.ImmagineBean;

public interface ImmaginiDao {
	
	public void doSave(ImmagineBean image) throws SQLException;
	
	public boolean doUpdate(String pathname) throws SQLException;
	
	public boolean doDelete(String pathname) throws SQLException;
	
	public Collection<ImmagineBean> doRetrieveAllByIdProdotto(String id_prodotto) throws SQLException;
}
