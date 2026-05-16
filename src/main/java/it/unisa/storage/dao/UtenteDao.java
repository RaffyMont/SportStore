package it.unisa.storage.dao;

import java.sql.SQLException;
import java.util.Collection;

import it.unisa.storage.model.UtenteBean;

public interface UtenteDao {
	
	public void doSave(UtenteBean utente) throws SQLException;
	
	public boolean doUpdate(UtenteBean utente) throws SQLException;
	
	public boolean doDelete(String id_utente) throws SQLException;
	
	public UtenteBean doRetrieveByKey(String id_utente) throws SQLException;
	
	public UtenteBean doRetreiveByEmail(String email) throws SQLException;
	
	public UtenteBean doAuthenticate(String email, String pwd) throws SQLException;
	
	public Collection<UtenteBean> doRetrieveAll(String order) throws SQLException;

}
