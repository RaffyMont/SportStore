package it.unisa.storage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import it.unisa.storage.model.VestitiBean;
import it.unisa.storage.model.VestitiBean.CategoriaVestiti;
import it.unisa.storage.model.VestitiBean.Gamba;
import it.unisa.storage.model.VestitiBean.Manica;


public class VestitiDaoImpl implements VestitiDao{
	
	private static final String TABLE_NAME = "Vestiti";
	private DataSource ds = null;

    public VestitiDaoImpl(DataSource ds) {
        this.ds = ds;
    }
    
    public synchronized void doSave(VestitiBean vestito) throws SQLException
    {
    	String insertSQL = "INSERT INTO " + TABLE_NAME
                + " (id_prodotto, tipo_vita, tessuto, stagione, categoria, tipo_collo, manica, gamba) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, vestito.getId_prodotto());
            preparedStatement.setString(2, vestito.getTipovita());
            preparedStatement.setString(3, vestito.getTessuto());
            preparedStatement.setString(4, vestito.getStagione());
            preparedStatement.setString(5, vestito.getCategoriaVestito().name().toLowerCase());
            preparedStatement.setString(6, vestito.getTipo_collo());
            preparedStatement.setString(7, vestito.getManica().name().toLowerCase());
            preparedStatement.setString(8, vestito.getGamba().name().toLowerCase());
            preparedStatement.executeUpdate();
        }
    }
    
    public synchronized boolean doUpdate(VestitiBean vestito) throws SQLException
    {
    	String sql = "UPDATE " + TABLE_NAME + " SET tipo_vita=?, tessuto=?, stagione=?, categoria=?, tipo_collo=?, manica=?, gamba=? WHERE id_prodotto = ?";
        try (Connection conn = ds.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, vestito.getTipovita());
            ps.setString(2, vestito.getTessuto());
            ps.setString(3, vestito.getStagione());
            ps.setString(4, vestito.getCategoriaVestito().name().toLowerCase());
            ps.setString(5, vestito.getTipo_collo());
            ps.setString(6, vestito.getManica().name().toLowerCase());
            ps.setString(7, vestito.getGamba().name().toLowerCase());
            ps.setString(8, vestito.getId_prodotto());
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated != 0;
        }
    }

    public synchronized boolean doDelete(String id_prodotto) throws SQLException
    {
    	String sql = "UPDATE " + TABLE_NAME + " SET attivo = false WHERE id_prodotto = ? AND attivo = true";
        try (Connection conn = ds.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)) {
        	ps.setString(1, id_prodotto);
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated != 0;
        }
    }

    public VestitiBean doRetrieveByKey(String id_prodotto) throws SQLException
    {
    	VestitiBean bean = new VestitiBean();
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE id_prodotto = ?";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, id_prodotto);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                	bean.setId_prodotto(rs.getString("id_prodotto"));
                    bean.setTipovita(rs.getString("tipo_vita"));
                    bean.setTessuto(rs.getString("tessuto"));
                    bean.setStagione(rs.getString("stagione"));
                    bean.setCategoriaVestito(CategoriaVestiti.valueOf(rs.getString("categoria").toUpperCase()));
                    bean.setTipo_collo(rs.getString("tipo_collo"));
                    bean.setManica(Manica.valueOf(rs.getString("manica").toUpperCase()));
                    bean.setGamba(Gamba.valueOf(rs.getString("gamba").toUpperCase()));
                }
            }
        }
        return bean;
    }
    
    public synchronized List<VestitiBean> doRetrieveByCategoriaVestiti(String categoria) throws SQLException
    {
    	List<VestitiBean> vestiti = new LinkedList<VestitiBean>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE categoria = ? AND attivo = true";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, categoria);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                	VestitiBean bean = new VestitiBean();
                	bean.setId_prodotto(rs.getString("id_prodotto"));
                    bean.setTipovita(rs.getString("tipo_vita"));
                    bean.setTessuto(rs.getString("tessuto"));
                    bean.setStagione(rs.getString("stagione"));
                    bean.setCategoriaVestito(CategoriaVestiti.valueOf(rs.getString("categoria").toUpperCase()));
                    bean.setTipo_collo(rs.getString("tipo_collo"));
                    bean.setManica(Manica.valueOf(rs.getString("manica").toUpperCase()));
                    bean.setGamba(Gamba.valueOf(rs.getString("gamba").toUpperCase()));
                    vestiti.add(bean);
                }
            }
        }
        return vestiti;
    }
    
    public synchronized List<VestitiBean> doRetrieveAll(String order) throws SQLException
    {
    	List<VestitiBean> vestiti = new LinkedList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME;
        if (order != null && !order.isEmpty()) {
            selectSQL += " ORDER BY " + order;
        }
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        		ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                VestitiBean bean = new VestitiBean();
                bean.setId_prodotto(rs.getString("id_prodotto"));
                bean.setTipovita(rs.getString("tipo_vita"));
                bean.setTessuto(rs.getString("tessuto"));
                bean.setStagione(rs.getString("stagione"));
                bean.setCategoriaVestito(CategoriaVestiti.valueOf(rs.getString("categoria").toUpperCase()));
                bean.setTipo_collo(rs.getString("tipo_collo"));
                bean.setManica(Manica.valueOf(rs.getString("manica").toUpperCase()));
                bean.setGamba(Gamba.valueOf(rs.getString("gamba").toUpperCase()));
                vestiti.add(bean);
            }
        }
        return vestiti;
    }
}
