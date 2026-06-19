package it.unisa.storage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import it.unisa.storage.model.IndirizzoBean;
import it.unisa.storage.model.UtenteBean;
import it.unisa.storage.model.UtenteBean.Ruolo;

public class UtenteDaoImpl implements UtenteDao{
	
	private static final String TABLE_NAME = "Utente";
	private DataSource ds = null;
	private IndirizzoDao indirizzoDAO;

    public UtenteDaoImpl(DataSource ds) {
        this.ds = ds;
        this.indirizzoDAO = new IndirizzoDaoImpl(ds);
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
            preparedStatement.setString(6, utente.getRuolo().name().toLowerCase());
            preparedStatement.setString(7, utente.getCellulare());
            preparedStatement.setInt(8, utente.getIndirizzo().getId_indirizzo());
            int r = preparedStatement.executeUpdate();
            System.out.println(r);
        }
	}
        
	public synchronized boolean doUpdate(UtenteBean utente) throws SQLException
	{
		String sql = "UPDATE " + TABLE_NAME + " SET nome = ?, cognome = ?, ruolo = ?, cellulare = ?, id_indirizzo = ? WHERE id_utente = ?";
        try (Connection conn = ds.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, utente.getNome());
            ps.setString(2, utente.getCognome());
            ps.setString(3, utente.getRuolo().name().toLowerCase());
            ps.setString(4, utente.getCellulare());
            ps.setInt(5, utente.getIndirizzo().getId_indirizzo());
            ps.setString(6, utente.getId_utente());
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated != 0;
        }
	}
	
	public synchronized boolean doDelete(String id_utente) throws SQLException
	{
		String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE id_utente = ?";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setString(1, id_utente);
            int result = preparedStatement.executeUpdate();
            return result != 0;
        }
	}
	
	public synchronized UtenteBean doRetrieveByKey(String id_utente) throws SQLException
	{
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE id_utente = ?";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, id_utente);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                	UtenteBean bean = new UtenteBean();
                	bean.setId_utente(rs.getString("id_utente"));
                    bean.setNome(rs.getString("nome"));
                    bean.setCognome(rs.getString("cognome"));
                    bean.setEmail(rs.getString("email"));
                    bean.setPassword(rs.getString("pwd"));
                    bean.setRuolo(Ruolo.valueOf(rs.getString("ruolo").toUpperCase()));
                    bean.setCellulare(rs.getString("cellulare"));
                    
                    int idIndirizzo = rs.getInt("id_indirizzo");
                    IndirizzoBean indirizzo = indirizzoDAO.doRetrieveByKey(idIndirizzo);
                    bean.setIndirizzo(indirizzo);

                    return bean;
                }
            }
        }
        return null;
	}
	
	public synchronized UtenteBean doRetrieveByEmail(String email) throws SQLException
	{
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE email = ?";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, email);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                	UtenteBean bean = new UtenteBean();
                	bean.setId_utente(rs.getString("id_utente"));
                    bean.setNome(rs.getString("nome"));
                    bean.setCognome(rs.getString("cognome"));
                    bean.setEmail(rs.getString("email"));
                    bean.setPassword(rs.getString("pwd"));
                    bean.setRuolo(Ruolo.valueOf(rs.getString("ruolo").toUpperCase()));
                    bean.setCellulare(rs.getString("cellulare"));
                    
                    int idIndirizzo = rs.getInt("id_indirizzo");
                    IndirizzoBean indirizzo = indirizzoDAO.doRetrieveByKey(idIndirizzo);
                    bean.setIndirizzo(indirizzo);
                    
                    return bean;

                }
            }
        }
        return null;
	}
	
	public synchronized UtenteBean doAuthenticate(String email, String pwd) throws SQLException
	{
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE email = ? AND pwd = ?";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, pwd);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                	UtenteBean bean = new UtenteBean();
                	bean.setId_utente(rs.getString("id_utente"));
                    bean.setNome(rs.getString("nome"));
                    bean.setCognome(rs.getString("cognome"));
                    bean.setEmail(rs.getString("email"));
                    bean.setPassword(rs.getString("pwd"));
                    bean.setRuolo(Ruolo.valueOf(rs.getString("ruolo").toUpperCase()));
                    bean.setCellulare(rs.getString("cellulare"));
                    
                    int idIndirizzo = rs.getInt("id_indirizzo");
                    IndirizzoBean indirizzo = indirizzoDAO.doRetrieveByKey(idIndirizzo);
                    bean.setIndirizzo(indirizzo);
                    
                    return bean;

                }
            }
        }
        return null;
	}
	
	public synchronized List<UtenteBean> doRetrieveAll() throws SQLException 
	{
		List<UtenteBean> utenti = new LinkedList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME;
        
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        		ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                UtenteBean bean = new UtenteBean();
                bean.setId_utente(rs.getString("id_utente"));
                bean.setNome(rs.getString("nome"));
                bean.setCognome(rs.getString("cognome"));
                bean.setEmail(rs.getString("email"));
                bean.setPassword(rs.getString("pwd"));
                bean.setRuolo(Ruolo.valueOf(rs.getString("ruolo").toUpperCase()));
                bean.setCellulare(rs.getString("cellulare"));
                
                int idIndirizzo = rs.getInt("id_indirizzo");
                IndirizzoBean indirizzo = indirizzoDAO.doRetrieveByKey(idIndirizzo);
                bean.setIndirizzo(indirizzo);
                utenti.add(bean);
            }
        }
        return utenti;
    }
	
	public synchronized boolean doUpdatePwd(String id_utente, String newPwd) throws SQLException{
		String sqlUpdate = "UPDATE " + TABLE_NAME + " SET pwd = ? WHERE id_utente = ?";
		
		try(Connection connection = ds.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate)){
				
				preparedStatement.setString(1, newPwd);
				preparedStatement.setString(2, id_utente);
				
				return preparedStatement.executeUpdate() > 0;
		}
	}
	
	public synchronized boolean doUpdateRuolo(String id_utente, Ruolo newRuolo) throws SQLException{
		String sqlUpdate = "UPDATE " + TABLE_NAME + " SET ruolo = ? WHERE id_utente = ?";
		
		try(Connection connection = ds.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate)){
				
				preparedStatement.setString(1, newRuolo.name().toLowerCase());
				preparedStatement.setString(2, id_utente);
				
				return preparedStatement.executeUpdate() > 0;
		}
	}
}
