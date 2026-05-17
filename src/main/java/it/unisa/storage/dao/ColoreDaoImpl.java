package it.unisa.storage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import javax.sql.DataSource;

import it.unisa.storage.model.ColoreBean;
import it.unisa.storage.model.ProdottoBean.Categoria;
import it.unisa.storage.model.ProdottoBean.Genere;

public class ColoreDaoImpl implements ColoreDao{
	
	private static final String TABLE_NAME = "Colore";
	private DataSource ds = null;

    public ColoreDaoImpl(DataSource ds) {
        this.ds = ds;
    }
    
    public synchronized void doSave(ColoreBean colore) throws SQLException
    {
    	String insertSQL = "INSERT INTO " + TABLE_NAME + " (nome) VALUES (?)";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, colore.getColore());
            preparedStatement.executeUpdate();
        }
    }

    public synchronized boolean doDelete(String nome) throws SQLException
    {
    	String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE nome = ?";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setString(1, nome);
            int result = preparedStatement.executeUpdate();
            return result != 0;
        }
    }

    public synchronized ColoreBean doRetrieveByKey(String nome) throws SQLException
    {
    	ColoreBean bean = new ColoreBean();
    	String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE nome = ?";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, nome);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                	bean.setColore(rs.getString("colore"));

                }
            }
        }
        return bean;
    }
    
    public Collection<ColoreBean> doRetrieveAll(String order) throws SQLException;
}
