package it.unisa.storage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import it.unisa.storage.model.AccessoriBean;
import it.unisa.storage.model.ProdottoBean.Categoria;
import it.unisa.storage.model.ProdottoBean.Genere;

public class AccessoriDaoImpl implements AccessoriDao {
	
	private static final String TABLE_NAME = "Accessori";
	private DataSource ds = null;

    public AccessoriDaoImpl(DataSource ds) {
        this.ds = ds;
    }
	
	public synchronized void doSave(AccessoriBean accessori) throws SQLException
	{
		ProdottoDaoImpl productDao = new ProdottoDaoImpl(ds);
		productDao.doSave(accessori);
		
		String insertSQL = "INSERT INTO " + TABLE_NAME
                + " (id_prodotto, tipo_accessorio, materiale) VALUES (?, ?, ?)";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, accessori.getId_prodotto());
            preparedStatement.setString(2, accessori.getTipo_accessori());
            preparedStatement.setString(3, accessori.getMateriali());
            preparedStatement.executeUpdate();
        }
	}

    public synchronized boolean doUpdate(AccessoriBean accessori) throws SQLException
    {
    	ProdottoDaoImpl productDao = new ProdottoDaoImpl(ds);
		productDao.doUpdate(accessori);
    	
    	String sql = "UPDATE " + TABLE_NAME + " SET tipo_accessorio = ?, materiale = ? WHERE id_accessorio = ?";
        try (Connection conn = ds.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, accessori.getTipo_accessori());
            ps.setString(2, accessori.getMateriali());
            ps.setString(3, accessori.getId_prodotto());

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated != 0;
        }
    }

    public synchronized boolean doChangeStatus(AccessoriBean accessori, boolean status) throws SQLException
    {
    	ProdottoDaoImpl productDao = new ProdottoDaoImpl(ds);
		return productDao.doChangeStatus(accessori.getId_prodotto(), status);
    }

    public synchronized AccessoriBean doRetrieveByKey(int id_accessorio) throws SQLException
    {
    	
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " acc JOIN Prodotto prod ON acc.id_prodotto = prod.id_prodotto WHERE acc.id_accessorio = ?";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, id_accessorio);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                	AccessoriBean bean = new AccessoriBean();
                	
                	bean.setId_prodotto(rs.getString("id_prodotto"));
                	bean.setModello(rs.getString("modello"));
                	bean.setDescrizione(rs.getString("descrizione"));
                	bean.setPrezzo(rs.getDouble("prezzo"));
                	bean.setAttivo(rs.getBoolean("attivo"));
                	bean.setMarca(rs.getString("marca"));
                	bean.setCategoria(Categoria.valueOf(rs.getString("categoria").toUpperCase()));
                	bean.setGenere(Genere.valueOf(rs.getString("genere").toUpperCase()));
                	bean.setStock(rs.getInt("stock"));
                	bean.setId_accessorio(rs.getInt("id_accessorio"));
                	bean.setTipo_accessori(rs.getString("tipo_accessorio"));
                	bean.setMateriali(rs.getString("materiale"));
                	return bean;
                }
            }
        }
        return null;
    }
    
    public synchronized List<AccessoriBean> doRetrieveAll() throws SQLException
    {
    	List<AccessoriBean> accessori = new LinkedList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME;
 
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        		ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                AccessoriBean bean = new AccessoriBean();
                bean.setId_prodotto(rs.getString("id_prodotto"));
                bean.setModello(rs.getString("modello"));
                bean.setDescrizione(rs.getString("descrizione"));
                bean.setPrezzo(rs.getDouble("prezzo"));
                bean.setAttivo(rs.getBoolean("attivo"));
                bean.setMarca(rs.getString("marca"));
                bean.setCategoria(Categoria.valueOf(rs.getString("categoria").toUpperCase()));
                bean.setGenere(Genere.valueOf(rs.getString("genere").toUpperCase()));
                bean.setStock(rs.getInt("stock"));
                bean.setId_accessorio(rs.getInt("id_accessorio"));
                bean.setTipo_accessori(rs.getString("tipo_accessorio"));
            	bean.setMateriali(rs.getString("materiale"));
                accessori.add(bean);
            }
        }
        return accessori;
    }
}
