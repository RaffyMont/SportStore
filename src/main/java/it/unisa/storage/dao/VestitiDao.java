package it.unisa.storage.dao;

import java.sql.SQLException;
import java.util.Collection;

import it.unisa.storage.model.VestitiBean;

public interface VestitiDao {
	public void doSave(VestitiBean v) throws SQLException;

    public boolean doUpdate(VestitiBean v) throws SQLException;

    public boolean doDelete(String id_prodotto) throws SQLException;

    public VestitiBean doRetrieveByKey(String id_prodotto) throws SQLException;
    
    public Collection<VestitiBean> doRetrieveByCategoriaVestiti(String categoria) throws SQLException;
    
    public Collection<VestitiBean> doRetrieveAll(String order) throws SQLException;
}
