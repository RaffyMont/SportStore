package it.unisa.storage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

import javax.sql.DataSource;

import it.unisa.storage.model.AccessoriBean;

public class AccessoriDaoImpl {
	
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

    public boolean doUpdate(AccessoriBean scarpe) throws SQLException;

    public boolean doDelete(String id_prodotto) throws SQLException;

    public AccessoriBean doRetrieveByKey(String id_prodotto) throws SQLException;
    
    public Collection<AccessoriBean> doRetrieveAll(String order) throws SQLException;
}
