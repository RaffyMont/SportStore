package it.unisa.storage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import it.unisa.storage.model.SupportoColoreBean;

public class SupportoColoreDaoImpl implements SupportoColoreDao{
	private static final String TABLE_NAME = "SupportoColore";
	private DataSource ds = null;
	private ColoreDaoImpl coloreDao;

    public SupportoColoreDaoImpl(DataSource ds) {
        this.ds = ds;
        this.coloreDao = new ColoreDaoImpl(ds);
    }
    
    public synchronized void doSave(SupportoColoreBean supporto) throws SQLException
    {
    	String insertSQL = "INSERT INTO " + TABLE_NAME
                + " (id_prodotto, nome) VALUES (?, ?)";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, supporto.getId_prodotto().getId_prodotto());
            preparedStatement.setString(2, supporto.getNome().getColore());
            preparedStatement.executeUpdate();
        }
    }
    
    
    public synchronized List<SupportoColoreBean> doRetrieveAllByProdotto(String id_prodotto) throws SQLException
    {
    	List<SupportoColoreBean> supporti = new LinkedList<SupportoColoreBean>();
    	String selectSQL = "SELECT c.nome FROM " + TABLE_NAME + " s JOIN Colore c ON c.nome = s.nome WHERE s.id_prodotto = ?";
    	try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, id_prodotto);
            try (ResultSet rs = preparedStatement.executeQuery()) {
	            while (rs.next()) {
	                SupportoColoreBean bean = new SupportoColoreBean();
	                bean.setNome(coloreDao.doRetrieveByKey(rs.getString("nome")));
	                supporti.add(bean);
	            	}
                }
            }
      
        return supporti;
    }
}
