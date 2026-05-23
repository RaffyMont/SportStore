package it.unisa.storage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;

import javax.sql.DataSource;

import it.unisa.storage.model.OrdineBean;
import it.unisa.storage.model.OrdineBean.Stato;

public class OrdineDaoImpl implements OrdineDao{
	
	private static final String TABLE_NAME = "Ordine";
	private DataSource ds = null;
	
	public OrdineDaoImpl(DataSource ds)
	{
		this.ds = ds;
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
	
	public boolean doUpdate(OrdineBean ordine) throws SQLException;

	public Collection<OrdineBean> doRetrieveAll() throws SQLException;
	
	public Collection<OrdineBean> doRetrieveAllByUser(String id_utente) throws SQLException;
	
	public boolean setStatusOrdine(String id_ordine, Stato stato_ordine) throws SQLException;

}
