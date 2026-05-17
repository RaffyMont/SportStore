package it.unisa.storage.dao;

import java.sql.SQLException;
import java.util.Collection;

import javax.sql.DataSource;

import it.unisa.storage.model.AccessoriBean;

public class AccessoriDaoImpl {
	
	private static final String TABLE_NAME = "Scarpe";
	private DataSource ds = null;

    public AccessoriDaoImpl(DataSource ds) {
        this.ds = ds;
    }
	
	public void doSave(AccessoriBean scarpe) throws SQLException;

    public boolean doUpdate(AccessoriBean scarpe) throws SQLException;

    public boolean doDelete(String id_prodotto) throws SQLException;

    public AccessoriBean doRetrieveByKey(String id_prodotto) throws SQLException;
    
    public Collection<AccessoriBean> doRetrieveAll(String order) throws SQLException;
}
