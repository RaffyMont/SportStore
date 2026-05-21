package it.unisa.storage.dao;

import java.sql.SQLException;
import java.util.Collection;

import it.unisa.storage.model.IndirizzoBean;

public interface IndirizzoDao {

    public void doSave(IndirizzoBean indirizzo) throws SQLException;

    public boolean doUpdate(IndirizzoBean indirizzo) throws SQLException;

    public boolean doDelete(int id_indirizzo) throws SQLException;

    public IndirizzoBean doRetrieveByKey(int id_indirizzo) throws SQLException;
    
    public Collection<IndirizzoBean> doRetrieveAll() throws SQLException;

}