package it.unisa.storage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

import javax.sql.DataSource;

import it.unisa.storage.model.UtenteBean;

public class UtenteDaoImpl implements UtenteDao{
	
	private static final String TABLE_NAME = "Utente";
	private DataSource ds = null;

    public UtenteDaoImpl(DataSource ds) {
        this.ds = ds;
    }

	public synchronized void doSave(UtenteBean utente) throws SQLException
	{
		String insertSQL = "INSERT INTO " + TABLE_NAME
                + " (id_utente, nome, cognome, email, pwd, ruolo, cellulare, id_indirizzo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, utente.getId_utente());
            preparedStatement.setString(2, utente.getNome());
            preparedStatement.setString(3, utente.getCognome());
            preparedStatement.setString(4, utente.getEmail());
            preparedStatement.setString(5, utente.getPassword());
            preparedStatement.setString(6, "" + utente.getRuolo());
            preparedStatement.setString(7, utente.getCellulare());
            preparedStatement.setInt(8, utente.getIndirizzo().getId_indirizzo());
            preparedStatement.executeUpdate();
        }
	}
        
	public synchronized boolean doUpdate(UtenteBean utente) throws SQLException
	{
		String sql = "UPDATE " + TABLE_NAME + " SET nome = ?, cognome = ?, email = ?, pwd = ?, ruolo = ?, cellulare = ?, id_indirizzo = ?, WHERE id_utente = ?";
        try (Connection conn = ds.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, utente.getNome());
            ps.setString(2, utente.getCognome());
            ps.setString(3, utente.getEmail());
            ps.setString(4, utente.getPassword());
            ps.setString(5, "" + utente.getRuolo());
            ps.setString(6, utente.getCellulare());
            ps.setInt(7, utente.getIndirizzo().getId_indirizzo());
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated != 0;
        }
	}
	
	public boolean doDelete(String id_utente) throws SQLException
	{
		String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE id_utente = ?";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setString(1, id_utente);
            int result = preparedStatement.executeUpdate();
            return result != 0;
        }
	}
	/*
	public UtenteBean doRetrieveByKey(String id_utente) throws SQLException;
	
	public UtenteBean doRetreiveByEmail(String email) throws SQLException;
	
	public UtenteBean doAuthenticate(String email, String pwd) throws SQLException;
	
	public Collection<UtenteBean> doRetreiveAll(String order) throws SQLException;
	*/
}
