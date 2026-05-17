package it.unisa.storage.dao;

import java.sql.SQLException;
import java.util.Collection;

import it.unisa.storage.model.TagliaBean;

public interface TagliaDao {

	public void doSave(TagliaBean taglia) throws SQLException;

    public boolean doDelete(String taglia) throws SQLException;

    public TagliaBean doRetrieveByKey(String taglia) throws SQLException;
    
    public Collection<TagliaBean> doRetrieveAll(String order) throws SQLException;
}
