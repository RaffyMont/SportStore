package it.unisa.storage.dao;

import java.sql.SQLException;
import java.util.Collection;

import javax.sql.DataSource;

import it.unisa.storage.model.ColoreBean;

public class ColoreDaoImpl implements ColoreDao{
	
	private static final String TABLE_NAME = "Colore";
	private DataSource ds = null;

    public ColoreDaoImpl(DataSource ds) {
        this.ds = ds;
    }
    
    public void doSave(ColoreBean accessori) throws SQLException;

    public boolean doUpdate(ColoreBean accessori) throws SQLException;

    public boolean doDelete(String nome) throws SQLException;

    public ColoreBean doRetrieveByKey(String nome) throws SQLException;
    
    public Collection<ColoreBean> doRetrieveAll(String order) throws SQLException;
}
