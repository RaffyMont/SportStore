package it.unisa.storage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

import javax.sql.DataSource;

import it.unisa.storage.model.ProdottoBean;

public class ProdottoDaoImpl implements ProdottoDao{
	
	private static final String TABLE_NAME = "Prodotto";
	private DataSource ds = null;

    public ProdottoDaoImpl(DataSource ds) {
        this.ds = ds;
    }

	
	public synchronized void doSave(ProdottoBean prodotto) throws SQLException
	{
		String insertSQL = "INSERT INTO " + TABLE_NAME
                + " (id_prodotto, modello, descrizione, prezzo, attivo, marca, categoria, genere, stock) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, prodotto.getId_prodotto());
            preparedStatement.setString(2, prodotto.getModello());
            preparedStatement.setString(3, prodotto.getDescrizione());
            preparedStatement.setDouble(4, prodotto.getPrezzo());
            preparedStatement.setBoolean(5, prodotto.isAttivo());
            preparedStatement.setString(6, prodotto.getMarca());
            preparedStatement.setString(7, "" + prodotto.getCategoria());
            preparedStatement.setString(8, "" + prodotto.getGenere());
            preparedStatement.setInt(9, prodotto.getStock());
            preparedStatement.executeUpdate();
        }
	}
	/*
	public boolean doUpdate(ProdottoBean utente) throws SQLException;
	
	public boolean doDelete(String id_utente) throws SQLException;
	
	public ProdottoBean doRetrieveByKey(String id_prodotto) throws SQLException;
	
	public Collection<ProdottoBean> doRetrieveAll(String order) throws SQLException;
	
	public Collection<ProdottoBean> doRetrieveByCategoria(String categoria) throws SQLException;
	
	public Collection<ProdottoBean> doRetrieveByGenere(String genere) throws SQLException;
	
	public boolean doUpdateStock(String id_utente, int stock) throws SQLException;
	
	public Collection<ProdottoBean> doRetrieveActive(String order) throws SQLException;	
	*/
}
