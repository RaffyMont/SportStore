package it.unisa.storage.dao;

import javax.sql.DataSource;

public class DettagliOrdineDaoImpl implements DettagliOrdineDao{
	
	private static final String TABLE_NAME = "DettagliOrdine";
	private DataSource ds = null;

    public DettagliOrdineDaoImpl(DataSource ds) {
        this.ds = ds;
    }
}
