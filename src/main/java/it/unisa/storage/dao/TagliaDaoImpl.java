package it.unisa.storage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

    public boolean doDelete(String taglia) throws SQLException;

    public TagliaBean doRetrieveByKey(String taglia) throws SQLException;
    
    public Collection<TagliaBean> doRetrieveAll(String order) throws SQLException;
}
