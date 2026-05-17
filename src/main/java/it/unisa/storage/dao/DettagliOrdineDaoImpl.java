package it.unisa.storage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

import javax.sql.DataSource;

import it.unisa.storage.model.DettagliOrdineBean;

public class DettagliOrdineDaoImpl implements DettagliOrdineDao{
	
	private static final String TABLE_NAME = "DettagliOrdine";
	private DataSource ds = null;

    public DettagliOrdineDaoImpl(DataSource ds) {
        this.ds = ds;
    }
    
    public void doSave(DettagliOrdineBean dettagli) throws SQLException
    {
    	String insertSQL = "INSERT INTO " + TABLE_NAME + " (quantità, prezzo_unitario, id_ordine, id_prodotto) VALUES (?, ?, ?, ?)";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setInt(1, dettagli.getQuantita());
            preparedStatement.setDouble(2, dettagli.getPrezzo_unitario());
            preparedStatement.setString(3, dettagli.getId_ordine().getId_ordine());
            preparedStatement.setString(4, dettagli.getId_prodotto().getId_prodotto());
            preparedStatement.executeUpdate();
        }
    }
	
	public boolean doUpdate(String id_ordine, String id_prodotto) throws SQLException;

    public boolean doDelete(String id_ordine, String id_prodotto) throws SQLException;

    public DettagliOrdineBean doRetrieveByKey(String id_ordine) throws SQLException;
    
    public Collection<DettagliOrdineBean> doRetrieveAll(String order) throws SQLException;
}
