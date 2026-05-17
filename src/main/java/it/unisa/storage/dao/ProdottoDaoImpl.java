package it.unisa.storage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import it.unisa.storage.model.ProdottoBean;
import it.unisa.storage.model.ProdottoBean.Categoria;
import it.unisa.storage.model.ProdottoBean.Genere;

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
	
	public synchronized boolean doUpdate(ProdottoBean prodotto) throws SQLException
	{
		String sql = "UPDATE " + TABLE_NAME + " SET modello = ?, descrizione = ?, prezzo = ?, attivo = ?, marca = ?, categoria = ?, genere = ?, stock = ? WHERE id_prodotto = ?";
        try (Connection conn = ds.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, prodotto.getModello());
            ps.setString(2, prodotto.getDescrizione());
            ps.setDouble(3, prodotto.getPrezzo());
            ps.setBoolean(4, prodotto.isAttivo());
            ps.setString(5, prodotto.getMarca());
            ps.setString(6, "" + prodotto.getCategoria());
            ps.setString(7, "" + prodotto.getGenere());
            ps.setInt(8, prodotto.getStock());
            ps.setString(9, prodotto.getId_prodotto());
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated != 0;
        }
	}

	 
	public synchronized boolean doDelete(String id_prodotto) throws SQLException
	{
		String sql = "UPDATE " + TABLE_NAME + " SET attivo = false WHERE id_prodotto = ? AND attivo = true";
        try (Connection conn = ds.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)) {
        	ps.setString(1, id_prodotto);
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated != 0;
        }
	}
	
	public synchronized ProdottoBean doRetrieveByKey(String id_prodotto) throws SQLException
	{
		ProdottoBean bean = new ProdottoBean();
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE id_prodotto = ?";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, id_prodotto);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                	bean.setId_prodotto(rs.getString("id_prodotto"));
                    bean.setModello(rs.getString("modello"));
                    bean.setDescrizione(rs.getString("descrizione"));
                    bean.setPrezzo(rs.getDouble("prezzo"));
                    bean.setAttivo(rs.getBoolean("attivo"));
                    bean.setMarca(rs.getString("marca"));
                    bean.setCategoria(Categoria.valueOf(rs.getString("categoria").toUpperCase()));
                    bean.setGenere(Genere.valueOf(rs.getString("genere").toUpperCase()));
                    bean.setStock(rs.getInt("stock"));

                }
            }
        }
        return bean;
	}
	
	public synchronized List<ProdottoBean> doRetrieveAll(String order) throws SQLException
	{
		List<ProdottoBean> prodotti = new LinkedList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME;
        if (order != null && !order.isEmpty()) {
            selectSQL += " ORDER BY " + order;
        }
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        		ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                ProdottoBean bean = new ProdottoBean();
                bean.setId_prodotto(rs.getString("id_prodotto"));
                bean.setModello(rs.getString("modello"));
                bean.setDescrizione(rs.getString("descrizione"));
                bean.setPrezzo(rs.getDouble("prezzo"));
                bean.setAttivo(rs.getBoolean("attivo"));
                bean.setMarca(rs.getString("marca"));
                bean.setCategoria(Categoria.valueOf(rs.getString("categoria").toUpperCase()));
                bean.setGenere(Genere.valueOf(rs.getString("genere").toUpperCase()));
                bean.setStock(rs.getInt("stock"));
                prodotti.add(bean);
            }
        }
        return prodotti;
	}
	
	public synchronized List<ProdottoBean> doRetrieveByCategoria(String categoria) throws SQLException
	{
		List<ProdottoBean> prodotti = new LinkedList<ProdottoBean>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE categoria = ? AND attivo = true";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, categoria);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                	ProdottoBean bean = new ProdottoBean();
                	bean.setId_prodotto(rs.getString("id_prodotto"));
                    bean.setModello(rs.getString("modello"));
                    bean.setDescrizione(rs.getString("descrizione"));
                    bean.setPrezzo(rs.getDouble("prezzo"));
                    bean.setAttivo(rs.getBoolean("attivo"));
                    bean.setMarca(rs.getString("marca"));
                    bean.setCategoria(Categoria.valueOf(rs.getString("categoria").toUpperCase()));
                    bean.setGenere(Genere.valueOf(rs.getString("genere").toUpperCase()));
                    bean.setStock(rs.getInt("stock"));
                    prodotti.add(bean);
                }
            }
        }
        return prodotti;
	}
	
	public synchronized List<ProdottoBean> doRetrieveByGenere(String genere) throws SQLException
	{
		List<ProdottoBean> prodotti = new LinkedList<ProdottoBean>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE genere = ? AND attivo = true";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, genere);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                	ProdottoBean bean = new ProdottoBean();
                	bean.setId_prodotto(rs.getString("id_prodotto"));
                    bean.setModello(rs.getString("modello"));
                    bean.setDescrizione(rs.getString("descrizione"));
                    bean.setPrezzo(rs.getDouble("prezzo"));
                    bean.setAttivo(rs.getBoolean("attivo"));
                    bean.setMarca(rs.getString("marca"));
                    bean.setCategoria(Categoria.valueOf(rs.getString("categoria").toUpperCase()));
                    bean.setGenere(Genere.valueOf(rs.getString("genere").toUpperCase()));
                    bean.setStock(rs.getInt("stock"));
                    prodotti.add(bean);
                }
            }
        }
        return prodotti;
	}
	
	public synchronized boolean doUpdateStock(String id_prodotto, int stock) throws SQLException
	{
		String sql = "UPDATE " + TABLE_NAME + " SET stock = ? WHERE id_prodotto = ?";
        try (Connection conn = ds.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)) {
        	  ps.setInt(1, stock);
              ps.setString(2, id_prodotto);
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated != 0;
        }
	}
	
	public synchronized List<ProdottoBean> doRetrieveActive(String order) throws SQLException
	{
		List<ProdottoBean> prodotti = new LinkedList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME + "WHERE active = true";
        if (order != null && !order.isEmpty()) {
            selectSQL += " ORDER BY " + order;
        }
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        		ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                ProdottoBean bean = new ProdottoBean();
                bean.setId_prodotto(rs.getString("id_prodotto"));
                bean.setModello(rs.getString("modello"));
                bean.setDescrizione(rs.getString("descrizione"));
                bean.setPrezzo(rs.getDouble("prezzo"));
                bean.setAttivo(rs.getBoolean("attivo"));
                bean.setMarca(rs.getString("marca"));
                bean.setCategoria(Categoria.valueOf(rs.getString("categoria").toUpperCase()));
                bean.setGenere(Genere.valueOf(rs.getString("genere").toUpperCase()));
                bean.setStock(rs.getInt("stock"));
                prodotti.add(bean);
            }
        }
        return prodotti;
	}
	
}
