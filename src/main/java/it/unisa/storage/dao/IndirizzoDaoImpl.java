package it.unisa.storage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

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

}
