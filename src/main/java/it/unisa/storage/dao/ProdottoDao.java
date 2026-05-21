package it.unisa.storage.dao;

import java.sql.SQLException;
import java.util.Collection;

import it.unisa.storage.model.ProdottoBean;

public interface ProdottoDao {
	
	public void doSave(ProdottoBean prodotto) throws SQLException;
	
	public boolean doUpdate(ProdottoBean prodotto) throws SQLException;
	
	public boolean doChangeStatus(String id_prodotto, boolean status) throws SQLException;
	
	public ProdottoBean doRetrieveByKey(String id_prodotto) throws SQLException;
	
	public Collection<ProdottoBean> doRetrieveAll() throws SQLException;
	
	public Collection<ProdottoBean> doRetrieveByCategoria(String categoria) throws SQLException;
	
	public Collection<ProdottoBean> doRetrieveByGenere(String genere) throws SQLException;
	
	public boolean doUpdateStock(String id_prodotto, int stock) throws SQLException;
	
	public Collection<ProdottoBean> doRetrieveActive() throws SQLException;
}
