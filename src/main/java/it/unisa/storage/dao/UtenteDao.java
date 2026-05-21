package it.unisa.storage.dao;

import java.sql.SQLException;
import java.util.Collection;

import it.unisa.storage.model.UtenteBean;
import it.unisa.storage.model.UtenteBean.Ruolo;

public interface UtenteDao {
	
	public void doSave(UtenteBean utente) throws SQLException;
	
	public boolean doUpdate(UtenteBean utente) throws SQLException;
	
	public boolean doDelete(String id_utente) throws SQLException;
	
	public UtenteBean doRetrieveByKey(String id_utente) throws SQLException;
	
	public UtenteBean doRetrieveByEmail(String email) throws SQLException;
	
	public UtenteBean doAuthenticate(String email, String pwd) throws SQLException;
	
	public Collection<UtenteBean> doRetrieveAll() throws SQLException;
	
	public boolean doUpdateRuolo(String id_utente, Ruolo newRuolo) throws SQLException;
	
	public boolean doUpdatePwd(String id_utente, String newPwd) throws SQLException;

}
