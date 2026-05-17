package it.unisa.storage.dao;

import java.sql.SQLException;
import java.util.Collection;

import it.unisa.storage.model.AccessoriBean;

public interface AccessoriDao {
	public void doSave(AccessoriBean accessori) throws SQLException;

    public boolean doUpdate(AccessoriBean accessori) throws SQLException;

    public boolean doDelete(String id_prodotto) throws SQLException;

    public AccessoriBean doRetrieveByKey(String id_prodotto) throws SQLException;
    
    public Collection<AccessoriBean> doRetrieveAll(String order) throws SQLException;
}
