package it.unisa.storage.dao;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import it.unisa.storage.model.SupportoTagliaBean;

public class SupportoTagliaDaoImpl {
	private static final String TABLE_NAME = "SupportoTaglia";
	private DataSource ds = null;

    public SupportoTagliaDaoImpl(DataSource ds) {
        this.ds = ds;
    }
    
    public synchronized void doSave(SupportoTagliaBean supporto) throws SQLException;

    public synchronized boolean doUpdate(SupportoTagliaBean indirizzo) throws SQLException;

    public synchronized boolean doDelete(String id_prodotto, String taglia) throws SQLException;

    public synchronized SupportoTagliaBean doRetrieveByProdotto(String id_prodotto) throws SQLException;
    
    public synchronized List<SupportoTagliaBean> doRetrieveAllByProtto() throws SQLException;
}
