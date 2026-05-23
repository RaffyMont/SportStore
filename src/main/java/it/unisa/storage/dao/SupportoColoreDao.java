package it.unisa.storage.dao;

import java.sql.SQLException;
import java.util.Collection;

import it.unisa.storage.model.SupportoColoreBean;

public interface SupportoColoreDao {
	public void doSave(SupportoColoreBean supporto) throws SQLException;
    
    public Collection<SupportoColoreBean> doRetrieveAllByProdotto(String id_prodotto) throws SQLException;
}
