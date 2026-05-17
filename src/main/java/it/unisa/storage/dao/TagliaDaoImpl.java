package it.unisa.storage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import javax.sql.DataSource;

import it.unisa.storage.model.TagliaBean;

public class TagliaDaoImpl implements TagliaDao{
	
	private static final String TABLE_NAME = "Taglia";
	private DataSource ds = null;

    public TagliaDaoImpl(DataSource ds) {
        this.ds = ds;
    }

	public synchronized void doSave(TagliaBean taglia) throws SQLException
	{
		String insertSQL = "INSERT INTO " + TABLE_NAME + " (taglia) VALUES (?)";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, taglia.getTaglia());
            preparedStatement.executeUpdate();
        }
	}

    public synchronized boolean doDelete(String taglia) throws SQLException
    {
    	String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE taglia = ?";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setString(1, taglia);
            int result = preparedStatement.executeUpdate();
            return result != 0;
        }
    }

    public synchronized TagliaBean doRetrieveByKey(String taglia) throws SQLException
    {
    	TagliaBean bean = new TagliaBean();
    	String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE taglia = ?";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, taglia);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                	bean.setTaglia(rs.getString("taglia"));

                }
            }
        }
        return bean;
    }
    
    public Collection<TagliaBean> doRetrieveAll(String order) throws SQLException;
}
