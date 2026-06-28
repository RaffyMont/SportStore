package it.unisa.storage.dao;

import java.sql.SQLException;
import java.util.Collection;

import it.unisa.storage.model.ImmagineBean;

public interface ImmaginiDao {
	
	public void doSave(ImmagineBean image, String id_prodotto) throws SQLException;
	
	public boolean doUpdate(String oldPathname, String newPathname, String id_prodotto) throws SQLException;
	
	public boolean doDelete(String pathname, String id_prodotto) throws SQLException;
	
	public Collection<ImmagineBean> doRetrieveAllByIdProdotto(String id_prodotto) throws SQLException;
}
