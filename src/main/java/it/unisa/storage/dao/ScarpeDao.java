package it.unisa.storage.dao;

import java.sql.SQLException;
import java.util.Collection;

import it.unisa.storage.model.ScarpeBean;

public interface ScarpeDao {
	
	public void doSave(ScarpeBean scarpe) throws SQLException;

    public boolean doUpdate(ScarpeBean scarpe) throws SQLException;

    public boolean doDelete(String id_prodotto) throws SQLException;

    public ScarpeBean doRetrieveByKey(String id_prodotto) throws SQLException;
    
    public Collection<ScarpeBean> doRetrieveAll(String order) throws SQLException;
}
