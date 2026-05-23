package it.unisa.storage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import it.unisa.storage.model.SupportoTagliaBean;

public class SupportoTagliaDaoImpl {
	private static final String TABLE_NAME = "SupportoTaglia";
	private DataSource ds = null;
	private TagliaDaoImpl tagliaDao;

    public SupportoTagliaDaoImpl(DataSource ds) {
        this.ds = ds;
        this.tagliaDao = new TagliaDaoImpl(ds);
    }
    
    public synchronized void doSave(SupportoTagliaBean supporto) throws SQLException
    {
    	String insertSQL = "INSERT INTO " + TABLE_NAME
                + " (id_prodotto, taglia) VALUES (?, ?)";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, supporto.getId_prodotto().getId_prodotto());
            preparedStatement.setString(2, supporto.getTaglia().getTaglia());
            preparedStatement.executeUpdate();
        }
    }
    
    public synchronized List<SupportoTagliaBean> doRetrieveAllByProdotto(String id_prodotto) throws SQLException
    {
    	List<SupportoTagliaBean> supporti = new LinkedList<SupportoTagliaBean>();
    	String selectSQL = "SELECT t.taglia FROM " + TABLE_NAME + " s JOIN Taglia t ON t.taglia = s.taglia WHERE s.id_prodotto = ?";
    	try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, id_prodotto);
            try (ResultSet rs = preparedStatement.executeQuery()) {
	            while (rs.next()) {
	                SupportoTagliaBean bean = new SupportoTagliaBean();
	                bean.setTaglia(tagliaDao.doRetrieveByKey(rs.getString("taglia")));
	                supporti.add(bean);
	            	}
                }
            }
      
        return supporti;
    }
}
