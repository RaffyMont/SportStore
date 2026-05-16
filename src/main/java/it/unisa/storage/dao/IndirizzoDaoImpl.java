package it.unisa.storage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import it.unisa.storage.model.IndirizzoBean;

public class IndirizzoDaoImpl implements IndirizzoDao{
	
	private static final String TABLE_NAME = "Indirizzo";
	private DataSource ds = null;

    public IndirizzoDaoImpl(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public synchronized void doSave(IndirizzoBean indirizzo) throws SQLException {
        String insertSQL = "INSERT INTO " + TABLE_NAME
                + " (provincia, stato, citta, CAP, via, civico) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, indirizzo.getProvincia());
            preparedStatement.setString(2, indirizzo.getStato());
            preparedStatement.setString(3, indirizzo.getCitta());
            preparedStatement.setString(4, indirizzo.getCAP());
            preparedStatement.setString(5, indirizzo.getVia());
            preparedStatement.setString(6, indirizzo.getCivico());
            preparedStatement.executeUpdate();
        }
    }
    
    public synchronized boolean doUpdate(IndirizzoBean indirizzo) throws SQLException {
        String sql = "UPDATE " + TABLE_NAME + " SET provincia = ?, stato = ?, citta = ?, CAP = ?, via = ?, civico = ?, WHERE code = ?";
        try (Connection conn = ds.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, indirizzo.getProvincia());
            ps.setString(2, indirizzo.getStato());
            ps.setString(3, indirizzo.getCitta());
            ps.setString(4, indirizzo.getCAP());
            ps.setString(5, indirizzo.getVia());
            ps.setString(6, indirizzo.getCivico());
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated != 0;
        }
    }
    
    public synchronized boolean doDelete(int code) throws SQLException {
        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE code = ?";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setInt(1, code);
            int result = preparedStatement.executeUpdate();
            return result != 0;
        }
    }
    
    public synchronized IndirizzoBean doRetrieveByKey(int code) throws SQLException {
        IndirizzoBean bean = new IndirizzoBean();
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE code = ?";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, code);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    bean.setProvincia(rs.getString("provincia"));
                    bean.setStato(rs.getString("stato"));
                    bean.setCitta(rs.getString("citta"));
                    bean.setCAP(rs.getString("CAP"));
                    bean.setVia(rs.getString("via"));
                    bean.setCivico(rs.getString("civico"));
                }
            }
        }
        return bean;
    }

}
