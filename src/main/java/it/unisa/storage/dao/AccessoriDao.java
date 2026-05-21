package it.unisa.storage.dao;

import java.sql.SQLException;
import java.util.Collection;

import it.unisa.storage.model.AccessoriBean;

public interface AccessoriDao {
	public void doSave(AccessoriBean accessori) throws SQLException;

    public boolean doUpdate(AccessoriBean accessori) throws SQLException;

    public boolean doChangeStatus(AccessoriBean accessori, boolean status) throws SQLException;

    public AccessoriBean doRetrieveByKey(int id_accessorio) throws SQLException;
    
    public Collection<AccessoriBean> doRetrieveAll() throws SQLException;
}
