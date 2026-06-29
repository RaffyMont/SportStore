package it.unisa.storage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import it.unisa.storage.model.ImmagineBean;
import it.unisa.storage.model.ProdottoBean;

public class ImmaginiDaoImpl implements ImmaginiDao{
	
	private static final String TABLE_NAME = "Immagine";
	private DataSource ds = null;
	private ProdottoDao prodottoDao;
	
	public ImmaginiDaoImpl(DataSource ds)
	{
		this.ds = ds;
		this.prodottoDao = new ProdottoDaoImpl(ds);
	}
	
	public synchronized void doSave(ImmagineBean image, String id_prodotto) throws SQLException
	{
		String insertSQL = "INSERT INTO " + TABLE_NAME + " (pathname, mime_type, id_prodotto) VALUES (?, ?, ?)";

		try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, image.getPathname());
            preparedStatement.setString(2, image.getMime_type());
            preparedStatement.setString(3, id_prodotto);
            preparedStatement.executeUpdate();
        }

	}
	
	public synchronized boolean doUpdate(String oldPathname, String newPathname) throws SQLException
	{
		String sql = "UPDATE " + TABLE_NAME + " SET pathname = ? WHERE pathname = ?";
        try (Connection conn = ds.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newPathname);
            ps.setString(2, oldPathname);
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated != 0;
        }
	}
	
	public synchronized boolean doDelete(String pathname, String id_prodotto) throws SQLException
	{
		String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE pathname = ? && id_prodotto = ?";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setString(1, pathname);
            preparedStatement.setString(2, id_prodotto);
            int result = preparedStatement.executeUpdate();
            return result != 0;
        }
	}
	
	public synchronized List<ImmagineBean> doRetrieveAllByIdProdotto(String id_prodotto) throws SQLException
	{
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id_prodotto = ?";
    	List<ImmagineBean> immagini = new LinkedList<ImmagineBean>();
    	
    	try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
    		preparedStatement.setString(1, id_prodotto);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                	ImmagineBean bean = new ImmagineBean();
                	
                	bean.setPathname(rs.getString("pathname"));
                	bean.setMime_type(rs.getString("mime_type"));
                	
                	String id = rs.getString("id_prodotto");
                	ProdottoBean prodotto = prodottoDao.doRetrieveByKey(id);
                	bean.setId_prodotto(prodotto);
                   
                    immagini.add(bean);
                }
            }
            
         return immagini;
         
    	}
	}

}
