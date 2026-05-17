package it.unisa.storage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import it.unisa.storage.model.ScarpeBean;

public class ScarpeDaoImpl implements ScarpeDao{
	
	private static final String TABLE_NAME = "Scarpe";
	private DataSource ds = null;

    public ScarpeDaoImpl(DataSource ds) {
        this.ds = ds;
    }
	
	public synchronized void doSave(ScarpeBean scarpe) throws SQLException
	{
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
    	String sql = "UPDATE " + TABLE_NAME + " SET tipo_suolo = ?, materiale = ? WHERE id_prodotto = ?";
        try (Connection conn = ds.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, scarpe.getTipo_suola());
            ps.setString(2, scarpe.getMateriale());
            ps.setString(3, scarpe.getId_prodotto());

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

    public synchronized ScarpeBean doRetrieveByKey(String id_prodotto) throws SQLException
    {
    	ScarpeBean bean = new ScarpeBean();
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE id_prodotto = ?";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, id_prodotto);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                	bean.setId_prodotto(rs.getString("id_prodotto"));
                	bean.setTipo_suola(rs.getString("tipo_suola"));
                	bean.setMateriale(rs.getString("materiale"));
                }
            }
        }
        return bean;
    }
    
    public synchronized List<ScarpeBean> doRetrieveAll(String order) throws SQLException
    {
    	List<ScarpeBean> scarpe = new LinkedList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME;
        if (order != null && !order.isEmpty()) {
            selectSQL += " ORDER BY " + order;
        }
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        		ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                ScarpeBean bean = new ScarpeBean();
                bean.setId_prodotto(rs.getString("id_prodotto"));
            	bean.setTipo_suola(rs.getString("tipo_suola"));
            	bean.setMateriale(rs.getString("materiale"));
                scarpe.add(bean);
            }
        }
        return scarpe;
    }
}
