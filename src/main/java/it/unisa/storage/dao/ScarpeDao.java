package it.unisa.storage.dao;

import java.sql.SQLException;
import java.util.Collection;

import it.unisa.storage.model.ScarpeBean;

public interface ScarpeDao {
	
	public void doSave(ScarpeBean scarpe) throws SQLException;

    public boolean doUpdate(ScarpeBean scarpe) throws SQLException;

    public boolean doChangeStatus(ScarpeBean scarpe, boolean status) throws SQLException;

    public ScarpeBean doRetrieveByKey(int id_scarpa) throws SQLException;
    
    public Collection<ScarpeBean> doRetrieveAll() throws SQLException;
}
