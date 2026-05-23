package it.unisa.storage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import it.unisa.storage.model.OrdineBean;
import it.unisa.storage.model.OrdineBean.Stato;

public class OrdineDaoImpl implements OrdineDao{
	
	private static final String TABLE_NAME = "Ordine";
	private DataSource ds = null;
	private IndirizzoDaoImpl indirizzoDao;
	private UtenteDaoImpl utenteDao;
	
	public OrdineDaoImpl(DataSource ds)
	{
		this.ds = ds;
		this.indirizzoDao = new IndirizzoDaoImpl(ds);
		this.utenteDao = new UtenteDaoImpl(ds);
	}
	
	public synchronized void doSave(OrdineBean ordine) throws SQLException
	{
		String insertSQL = "INSERT INTO " + TABLE_NAME
                + " (id_ordine, data_ordine, stato_ordine, prezzo_totale, id_utente, id_indirizzo) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, ordine.getId_ordine());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(ordine.getData_ordine()));
            preparedStatement.setString(3, ordine.getStato().name().toLowerCase());
            preparedStatement.setDouble(4, ordine.getPrezzo_totale());
            preparedStatement.setString(5,  ordine.getId_utente().getId_utente());
            preparedStatement.setInt(6, ordine.getId_indirizzo().getId_indirizzo());
            preparedStatement.executeUpdate();
        }
	}
	
	public synchronized boolean doUpdate(OrdineBean ordine) throws SQLException
	{
		String sql = "UPDATE " + TABLE_NAME + " SET data_ordine = ?, stato_ordine = ?, prezzo_totale = ?, id_utente = ?, id_indirizzo = ? WHERE id_ordine = ?";
        try (Connection conn = ds.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)) {
        	ps.setTimestamp(1, Timestamp.valueOf(ordine.getData_ordine()));
            ps.setString(2, ordine.getStato().name().toLowerCase());
            ps.setDouble(3, ordine.getPrezzo_totale());
            ps.setString(4, ordine.getId_utente().getId_utente());
            ps.setInt(5, ordine.getId_indirizzo().getId_indirizzo());
            ps.setString(6, ordine.getId_ordine());
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated != 0;
        }
	}

	public synchronized List<OrdineBean> doRetrieveAll() throws SQLException
	{
		List<OrdineBean> ordini = new LinkedList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME;
        
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        		ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                OrdineBean bean = new OrdineBean();
                bean.setId_ordine(rs.getString("id_ordine"));
                bean.setData_ordine(rs.getTimestamp("data_ordine").toLocalDateTime());
                bean.setStato(Stato.valueOf(rs.getString("stato_ordine").toUpperCase()));
                bean.setPrezzo_totale(rs.getDouble("prezzo_totale"));
                bean.setId_utente(utenteDao.doRetrieveByKey(rs.getString("id_utente")));
                bean.setId_indirizzo(indirizzoDao.doRetrieveByKey(rs.getInt("id_indirizzo")));;
                ordini.add(bean);
            }
        }
        return ordini;
	}
	
	public synchronized List<OrdineBean> doRetrieveAllByUser(String id_utente) throws SQLException
	{
		List<OrdineBean> ordini = new LinkedList<>();
		String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE id_utente = ?";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, id_utente);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                	OrdineBean bean = new OrdineBean();
                    bean.setId_ordine(rs.getString("id_ordine"));
                    bean.setData_ordine(rs.getTimestamp("data_ordine").toLocalDateTime());
                    bean.setStato(Stato.valueOf(rs.getString("stato_ordine").toUpperCase()));
                    bean.setPrezzo_totale(rs.getDouble("prezzo_totale"));
                    bean.setId_utente(utenteDao.doRetrieveByKey(rs.getString("id_utente")));
                    bean.setId_indirizzo(indirizzoDao.doRetrieveByKey(rs.getInt("id_indirizzo")));;
                    ordini.add(bean);
                }
            }
        }
        return ordini;
	}
	}
	
	public boolean setStatusOrdine(String id_ordine, Stato stato_ordine) throws SQLException;

}
