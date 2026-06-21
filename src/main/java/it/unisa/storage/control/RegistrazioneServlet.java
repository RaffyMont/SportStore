package it.unisa.storage.control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.UUID;

import javax.sql.DataSource;

import it.unisa.storage.dao.IndirizzoDao;
import it.unisa.storage.dao.IndirizzoDaoImpl;
import it.unisa.storage.dao.UtenteDao;
import it.unisa.storage.dao.UtenteDaoImpl;
import it.unisa.storage.model.IndirizzoBean;
import it.unisa.storage.model.UtenteBean;
import it.unisa.storage.model.UtenteBean.Ruolo;

/**
 * Servlet implementation class RegistrazioneServlet
 */
@WebServlet("/Registrazione")
public class RegistrazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrazioneServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		String token = (session != null) ? (String) session.getAttribute("token") : null;
	    if (token != null) {
	         response.sendRedirect(request.getContextPath() + "/Home");
	         return;
	    }
	    request.setAttribute("paginaAttiva", "registrazione");
		request.getRequestDispatcher("/WEB-INF/views/common/Registrazione.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String nome = request.getParameter("nome");
		String cognome = request.getParameter("cognome");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String conferma = request.getParameter("conferma");
		String cellulare = request.getParameter("cellulare");
		String via = request.getParameter("via");
		String civico = request.getParameter("civico");
		String citta = request.getParameter("citta");
		String cap = request.getParameter("cap");
		String provincia = request.getParameter("provincia");
		String stato = request.getParameter("stato");
		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		UtenteDao utenteDao = new UtenteDaoImpl(ds);
		IndirizzoDao indirizzoDao = new IndirizzoDaoImpl(ds);
		
		try
		{
			UtenteBean utente = utenteDao.doRetrieveByEmail(email);
			if(utente != null)
			{
				request.setAttribute("errore", "email gia registrata");
				request.getRequestDispatcher("/WEB-INF/views/common/Registrazione.jsp").forward(request, response);
				return;
			}
			
			IndirizzoBean indirizzo = new IndirizzoBean();
			indirizzo.setProvincia(provincia);
			indirizzo.setStato(stato);
			indirizzo.setCitta(citta);
			indirizzo.setCAP(cap);
			indirizzo.setVia(via);
			indirizzo.setCivico(civico);
			indirizzoDao.doSave(indirizzo);
			
			UtenteBean utente2 = new UtenteBean();
			
			String id;
			do {
			    id = UUID.randomUUID().toString()
			             .replace("-", "")
			             .substring(0, 6);
			} while (utenteDao.doRetrieveByKey(id) != null);

			utente2.setId_utente(id);
			utente2.setNome(nome);
			utente2.setCognome(cognome);
			utente2.setEmail(email);
			utente2.setPassword(password);
			utente2.setRuolo(Ruolo.COMMON);
			utente2.setCellulare(cellulare);
			utente2.setIndirizzo(indirizzo);
			utenteDao.doSave(utente2);
			
			UtenteBean utenteSalvato = utenteDao.doRetrieveByEmail(email);
			
			HttpSession session = request.getSession(true);
			session.setAttribute("utente", utenteSalvato);
			session.setAttribute("token", utenteSalvato.getId_utente());
			session.setAttribute("role", utenteSalvato.getRuolo().name().toLowerCase());
			response.sendRedirect(request.getContextPath() + "/Home");
		}
		catch(SQLException e)
		{
			e.printStackTrace(); 
			request.setAttribute("errore", e.getMessage());
			request.setAttribute("paginaAttiva", "registrazione");
			request.getRequestDispatcher("/WEB-INF/views/common/Registrazione.jsp").forward(request, response);
		}
	}

}
