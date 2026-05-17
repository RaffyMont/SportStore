package it.unisa.storage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import it.unisa.storage.model.VestitiBean;

public class VestitiDaoImpl implements VestitiDao{
	
	private static final String TABLE_NAME = "Vestiti";
	private DataSource ds = null;

    public VestitiDaoImpl(DataSource ds) {
        this.ds = ds;
    }
    
    public synchronized void doSave(VestitiBean vestito) throws SQLException
    {
    	String insertSQL = "INSERT INTO " + TABLE_NAME
                + " (id_prodotto, tipo_vita, tessuto, stagione, categoria, tipo_collo, manica, gamba) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, vestito.getId_prodotto());
            preparedStatement.setString(2, vestito.getTipovita());
            preparedStatement.setString(3, vestito.getTessuto());
            preparedStatement.setString(4, vestito.getStagione());
            preparedStatement.setString(5, vestito.getCategoria().name().toLowerCase());
            preparedStatement.setString(6, vestito.getTipo_collo());
            preparedStatement.setString(7, vestito.getManica().name().toLowerCase());
            preparedStatement.setString(8, vestito.getGamba().name().toLowerCase());
            preparedStatement.executeUpdate();
        }
    }
    
    public boolean doUpdate(VestitiBean vestito) throws SQLException
    {
    	String sql = "UPDATE " + TABLE_NAME + " SET tipo_vita=?, tessuto=?, stagione=?, categoria=?, tipo_collo=?, manica=?, gamba=? WHERE id_prodotto = ?";
        try (Connection conn = ds.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, vestito.getTipovita());
            ps.setString(2, vestito.getTessuto());
            ps.setString(3, vestito.getStagione());
            ps.setString(4, vestito.getCategoria().name().toLowerCase());
            ps.setString(5, vestito.getTipo_collo());
            ps.setString(6, vestito.getManica().name().toLowerCase());
            ps.setString(7, vestito.getGamba().name().toLowerCase());
            ps.setString(8, vestito.getId_prodotto());
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated != 0;
        }
    }

    public boolean doDelete(String id_prodotto) throws SQLException;

    public VestitiBean doRetrieveByKey(String id_prodotto) throws SQLException;
}
