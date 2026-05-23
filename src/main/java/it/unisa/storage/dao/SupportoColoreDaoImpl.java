package it.unisa.storage.dao;

import java.sql.SQLException;
import java.util.Collection;

import javax.sql.DataSource;

import it.unisa.storage.model.SupportoColoreBean;

public class SupportoColoreDaoImpl implements SupportoColoreDao{
	private static final String TABLE_NAME = "SupportoColore";
	private DataSource ds = null;
	private ColoreDaoImpl coloreDao;

    public SupportoColoreDaoImpl(DataSource ds) {
        this.ds = ds;
        this.coloreDao = new ColoreDaoImpl(ds);
    }
    
    public void doSave(SupportoColoreBean supporto) throws SQLException;
    
    public Collection<SupportoColoreBean> doRetrieveAllByProdotto(String id_prodotto) throws SQLException;
}
