package it.unisa.storage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import it.unisa.storage.model.AccessoriBean;

public class AccessoriDaoImpl implements AccessoriDao {
	
	private static final String TABLE_NAME = "Accessori";
	private DataSource ds = null;

    public AccessoriDaoImpl(DataSource ds) {
        this.ds = ds;
    }
	
	public synchronized void doSave(AccessoriBean accessori) throws SQLException
	{
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
    	String sql = "UPDATE " + TABLE_NAME + " SET tipo_accessorio = ?, materiale = ? WHERE id_prodotto = ?";
        try (Connection conn = ds.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, accessori.getTipo_accessori());
            ps.setString(2, accessori.getMateriali());
            ps.setString(3, accessori.getId_prodotto());

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

    public synchronized AccessoriBean doRetrieveByKey(String id_prodotto) throws SQLException
    {
    	AccessoriBean bean = new AccessoriBean();
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE id_prodotto = ?";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, id_prodotto);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                	bean.setId_prodotto(rs.getString("id_prodotto"));
                	bean.setTipo_accessori(rs.getString("tipo_accessorio"));
                	bean.setMateriali(rs.getString("materiale"));
                }
            }
        }
        return bean;
    }
    
    public synchronized List<AccessoriBean> doRetrieveAll(String order) throws SQLException
    {
    	List<AccessoriBean> accessori = new LinkedList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME;
        if (order != null && !order.isEmpty()) {
            selectSQL += " ORDER BY " + order;
        }
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        		ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                AccessoriBean bean = new AccessoriBean();
                bean.setId_prodotto(rs.getString("id_prodotto"));
                bean.setTipo_accessori(rs.getString("tipo_accessorio"));
            	bean.setMateriali(rs.getString("materiale"));
                accessori.add(bean);
            }
        }
        return accessori;
    }
}
