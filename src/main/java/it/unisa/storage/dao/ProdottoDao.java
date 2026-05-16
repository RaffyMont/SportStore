package it.unisa.storage.dao;

import java.sql.SQLException;
import java.util.Collection;

import it.unisa.storage.model.ProdottoBean;

public interface ProdottoDao {
	
	public void doSave(ProdottoBean utente) throws SQLException;
	
	public boolean doUpdate(ProdottoBean utente) throws SQLException;
	
	public boolean doDelete(String id_utente) throws SQLException;
	
	public ProdottoBean doRetrieveByKey(String id_prodotto) throws SQLException;
	
	public Collection<ProdottoBean> doRetrieveAll(String order) throws SQLException;
	
	public Collection<ProdottoBean> doRetrieveByCategoria(String categoria) throws SQLException;
	
	public Collection<ProdottoBean> doRetrieveByGenere(String genere) throws SQLException;
	
	public boolean doUpdateStock(String id_utente, int stock) throws SQLException;
	
	public Collection<ProdottoBean> doRetrieveActive(String order) throws SQLException;
}
