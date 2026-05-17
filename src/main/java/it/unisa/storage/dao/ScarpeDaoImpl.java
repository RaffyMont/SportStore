package it.unisa.storage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

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

    public boolean doUpdate(ScarpeBean scarpe) throws SQLException;

    public boolean doDelete(String id_prodotto) throws SQLException;

    public ScarpeBean doRetrieveByKey(String id_prodotto) throws SQLException;
    
    public Collection<ScarpeBean> doRetrieveAll(String order) throws SQLException;
}
