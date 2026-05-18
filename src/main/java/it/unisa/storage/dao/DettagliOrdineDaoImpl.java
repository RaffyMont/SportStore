package it.unisa.storage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import it.unisa.storage.model.DettagliOrdineBean;
import it.unisa.storage.model.OrdineBean;
import it.unisa.storage.model.ProdottoBean;
import it.unisa.storage.model.ProdottoBean.Categoria;
import it.unisa.storage.model.ProdottoBean.Genere;

public class DettagliOrdineDaoImpl implements DettagliOrdineDao{
	
	private static final String TABLE_NAME = "DettagliOrdine";
	private DataSource ds = null;

    public DettagliOrdineDaoImpl(DataSource ds) {
        this.ds = ds;
    }
    
    public synchronized void doSave(DettagliOrdineBean dettagli) throws SQLException
    {
    	String insertSQL = "INSERT INTO " + TABLE_NAME + " (quantità, prezzo_unitario, id_ordine, id_prodotto) VALUES (?, ?, ?, ?)";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setInt(1, dettagli.getQuantita());
            preparedStatement.setDouble(2, dettagli.getPrezzo_unitario());
            preparedStatement.setString(3, dettagli.getId_ordine().getId_ordine());
            preparedStatement.setString(4, dettagli.getId_prodotto().getId_prodotto());
            preparedStatement.executeUpdate();
        }
    }
	
	public synchronized boolean doUpdate(DettagliOrdineBean dettagli) throws SQLException
	{
		String sql = "UPDATE " + TABLE_NAME + " SET quantità = ?, prezzo_unitario = ? WHERE id_ordine = ? AND id_prodotto = ?";
        try (Connection conn = ds.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, dettagli.getQuantita());
            ps.setDouble(2, dettagli.getPrezzo_unitario());
            ps.setString(3, dettagli.getId_ordine().getId_ordine());
            ps.setString(4, dettagli.getId_prodotto().getId_prodotto());
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated != 0;
        }
	}

    public synchronized boolean doDelete(String id_ordine, String id_prodotto) throws SQLException
    {
    	String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE id_ordine = ? AND id_prodotto = ?";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setString(1, id_ordine);
            preparedStatement.setString(2, id_prodotto);
            int result = preparedStatement.executeUpdate();
            return result != 0;
        }
    }

    public synchronized DettagliOrdineBean doRetrieveByKey(String id_ordine, String id_prodotto) throws SQLException
    {
    	DettagliOrdineBean bean = new DettagliOrdineBean();
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " d" + " JOIN Prodotto p ON d.id_prodotto = p.id_prodotto " + "WHERE d.id_ordine = ? AND d.id_prodotto = ?";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, id_ordine);
            preparedStatement.setString(2, id_prodotto);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                	bean.setQuantita(rs.getInt("quantità"));
                    bean.setPrezzo_unitario(rs.getDouble("prezzo_unitario"));
                    
                    OrdineBean ordine = new OrdineBean();
                    ordine.setId_ordine(rs.getString("id_ordine"));
                    bean.setId_ordine(ordine);
                    
                    ProdottoBean prodotto = new ProdottoBean();
                    prodotto.setId_prodotto(rs.getString("id_prodotto"));
                    prodotto.setModello(rs.getString("modello"));
                    prodotto.setDescrizione(rs.getString("descrizione"));
                    prodotto.setPrezzo(rs.getDouble("prezzo"));
                    prodotto.setAttivo(rs.getBoolean("attivo"));
                    prodotto.setMarca(rs.getString("marca"));
                    prodotto.setCategoria(Categoria.valueOf(rs.getString("categoria").toUpperCase()));
                    prodotto.setGenere(Genere.valueOf(rs.getString("genere").toUpperCase()));
                    prodotto.setStock(rs.getInt("stock"));
                    bean.setId_prodotto(prodotto);
                }	
            }
        }
        return bean;
    }
    
    public synchronized List<DettagliOrdineBean> doRetrieveAllByOrdine(String id_ordine) throws SQLException
    {
    	List<DettagliOrdineBean> dettagli = new LinkedList<DettagliOrdineBean>();
    	String selectSQL = "SELECT * FROM " + TABLE_NAME + " d" + " JOIN Prodotto p ON d.id_prodotto = p.id_prodotto " + "WHERE d.id_ordine = ?";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, id_ordine);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                	DettagliOrdineBean bean = new DettagliOrdineBean();
                	bean.setQuantita(rs.getInt("quantità"));
                    bean.setPrezzo_unitario(rs.getDouble("prezzo_unitario"));
                    
                    OrdineBean ordine = new OrdineBean();
                    ordine.setId_ordine(rs.getString("id_ordine"));
                    bean.setId_ordine(ordine);
                    
                    ProdottoBean prodotto = new ProdottoBean();
                    prodotto.setId_prodotto(rs.getString("id_prodotto"));
                    prodotto.setModello(rs.getString("modello"));
                    prodotto.setDescrizione(rs.getString("descrizione"));
                    prodotto.setPrezzo(rs.getDouble("prezzo"));
                    prodotto.setAttivo(rs.getBoolean("attivo"));
                    prodotto.setMarca(rs.getString("marca"));
                    prodotto.setCategoria(Categoria.valueOf(rs.getString("categoria").toUpperCase()));
                    prodotto.setGenere(Genere.valueOf(rs.getString("genere").toUpperCase()));
                    prodotto.setStock(rs.getInt("stock"));
                    bean.setId_prodotto(prodotto);
                    dettagli.add(bean);
                }	
            }
        }
        return dettagli;
    }
}
