package it.unisa.storage.dao;

import java.sql.SQLException;
import java.util.Collection;

import it.unisa.storage.model.ColoreBean;

public interface ColoreDao {
	
	public void doSave(ColoreBean accessori) throws SQLException;

    public boolean doUpdate(ColoreBean accessori) throws SQLException;

    public boolean doDelete(String nome) throws SQLException;

    public ColoreBean doRetrieveByKey(String nome) throws SQLException;
    
    public Collection<ColoreBean> doRetrieveAll(String order) throws SQLException;
}
