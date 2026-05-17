package it.unisa.storage.dao;

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

	public void doSave(TagliaBean taglia) throws SQLException;

    public boolean doDelete(String taglia) throws SQLException;

    public TagliaBean doRetrieveByKey(String taglia) throws SQLException;
    
    public Collection<TagliaBean> doRetrieveAll(String order) throws SQLException;
}
