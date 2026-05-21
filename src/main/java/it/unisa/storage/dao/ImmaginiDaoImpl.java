package it.unisa.storage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import it.unisa.storage.model.ImmagineBean;

public class ImmaginiDaoImpl implements ImmaginiDao{
	
	private static final String TABLE_NAME = "Immagine";
	private DataSource ds = null;
	
	public ImmaginiDaoImpl(DataSource ds)
	{
		this.ds = ds;
	}
	
	public synchronized void doSave(ImmagineBean image) throws SQLException
	{
		String insertSQL = "INSERT INTO " + TABLE_NAME + " (pathname, mime_type, id_prodotto) VALUES (?, ?, ?)";

		try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, image.getPathname());
            preparedStatement.setString(2, image.getMime_type());
            preparedStatement.setString(3, image.getId_prodotto().getId_prodotto());
            preparedStatement.executeUpdate();
        }

	}
	
	public synchronized boolean doUpdate(String oldPathname, String newPathname) throws SQLException
	{
		String sql = "UPDATE " + TABLE_NAME + " SET pathname = ? WHERE pathname = ?";
        try (Connection conn = ds.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newPathname);
            ps.setString(1, oldPathname);
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated != 0;
        }
	}
	
	public synchronized boolean doDelete(String pathname) throws SQLException;
	
	public synchronized List<ImmagineBean> doRetrieveAllByIdProdotto(String id_prodotto) throws SQLException;

}
