package it.unisa.storage.dao;

import java.sql.SQLException;

import java.util.Collection;

import it.unisa.storage.model.IndirizzoBean;

public interface IndirizzoDao {

    void doSave(IndirizzoBean indirizzo) throws SQLException;

    boolean doUpdate(IndirizzoBean indirizzo) throws SQLException;

    boolean doDelete(int id_indirizzo) throws SQLException;

    IndirizzoBean doRetrieveByKey(int id_indirizzo) throws SQLException;

}