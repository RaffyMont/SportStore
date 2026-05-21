package it.unisa.storage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import it.unisa.storage.model.ScarpeBean;
import it.unisa.storage.model.ProdottoBean.Categoria;
import it.unisa.storage.model.ProdottoBean.Genere;

public class ScarpeDaoImpl implements ScarpeDao{
	
	private static final String TABLE_NAME = "Scarpe";
	private DataSource ds = null;

    public ScarpeDaoImpl(DataSource ds) {
        this.ds = ds;
    }
	
	public synchronized void doSave(ScarpeBean scarpe) throws SQLException
	{
		ProdottoDaoImpl productDao = new ProdottoDaoImpl(ds);
		productDao.doSave(scarpe);
		
		String insertSQL = "INSERT INTO " + TABLE_NAME
                + " (id_prodotto, tipo_suola, materiale) VALUES (?, ?, ?)";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, scarpe.getId_prodotto());
            preparedStatement.setString(2, scarpe.getTipo_suola());
            preparedStatement.setString(3, scarpe.getMateriale());
            preparedStatement.executeUpdate();
        }
	}

    public synchronized boolean doUpdate(ScarpeBean scarpe) throws SQLException
    {
    	String sql = "UPDATE " + TABLE_NAME + " SET tipo_suolo = ?, materiale = ? WHERE id_scarpa = ?";
        try (Connection conn = ds.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, scarpe.getTipo_suola());
            ps.setString(2, scarpe.getMateriale());
            ps.setInt(3, scarpe.getId_scarpa());

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated != 0;
        }
    }

    public synchronized boolean doChangeStatus(ScarpeBean scarpe, boolean status) throws SQLException
    {
    	ProdottoDaoImpl productDao = new ProdottoDaoImpl(ds);
    	return productDao.doChangeStatus(scarpe.getId_prodotto(), status);
    }

    public synchronized ScarpeBean doRetrieveByKey(int id_scarpa) throws SQLException
    {
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " s JOIN Prodotto prod ON s.id_prodotto = prod.id_prodotto WHERE id_scarpa = ?";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, id_scarpa);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                	ScarpeBean bean = new ScarpeBean();
                	bean.setId_prodotto(rs.getString("id_prodotto"));
                    bean.setModello(rs.getString("modello"));
                    bean.setDescrizione(rs.getString("descrizione"));
                    bean.setPrezzo(rs.getDouble("prezzo"));
                    bean.setAttivo(rs.getBoolean("attivo"));
                    bean.setMarca(rs.getString("marca"));
                    bean.setCategoria(Categoria.valueOf(rs.getString("categoria").toUpperCase()));
                    bean.setGenere(Genere.valueOf(rs.getString("genere").toUpperCase()));
                    bean.setStock(rs.getInt("stock"));
                	bean.setId_scarpa(rs.getInt("id_scarpa"));
                	bean.setTipo_suola(rs.getString("tipo_suola"));
                	bean.setMateriale(rs.getString("materiale"));
                	return bean;
                }
            }
        }
        return null;
    }
    
    public synchronized List<ScarpeBean> doRetrieveAll() throws SQLException
    {
    	List<ScarpeBean> scarpe = new LinkedList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " s JOIN Prodotto prod ON s.id_prodotto = prod.id_prodotto";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        		ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                ScarpeBean bean = new ScarpeBean();
                bean.setId_prodotto(rs.getString("id_prodotto"));
                bean.setModello(rs.getString("modello"));
                bean.setDescrizione(rs.getString("descrizione"));
                bean.setPrezzo(rs.getDouble("prezzo"));
                bean.setAttivo(rs.getBoolean("attivo"));
                bean.setMarca(rs.getString("marca"));
                bean.setCategoria(Categoria.valueOf(rs.getString("categoria").toUpperCase()));
                bean.setGenere(Genere.valueOf(rs.getString("genere").toUpperCase()));
                bean.setStock(rs.getInt("stock"));
                bean.setId_scarpa(rs.getInt("id_scarpa"));
            	bean.setTipo_suola(rs.getString("tipo_suola"));
            	bean.setMateriale(rs.getString("materiale"));
                scarpe.add(bean);
            }
        }
        return scarpe;
    }
}
