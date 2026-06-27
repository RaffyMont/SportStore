package it.unisa.storage.control;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import javax.sql.DataSource;

import it.unisa.storage.dao.OrdineDao;
import it.unisa.storage.dao.OrdineDaoImpl;
import it.unisa.storage.model.CarrelloBean;
import it.unisa.storage.model.OrdineBean;
import it.unisa.storage.model.UtenteBean;

/**
 * Servlet implementation class ProfiloServlet
 */
@WebServlet("/Profilo")
public class ProfiloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DataSource ds;
	private OrdineDao ordineDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfiloServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    	ds = (DataSource) getServletContext().getAttribute("DataSource");
    	if (ds == null) throw new ServletException("DataSource non disponibile");
		ordineDao = new OrdineDaoImpl(ds);
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ctx = request.getContextPath();
		HttpSession session = request.getSession(false);
		String token = (session != null) ? (String) session.getAttribute("token") : null;
	    if (token == null) {
	        response.sendRedirect(ctx + "/Login");
	         return;
	    }
	 
	    UtenteBean utente = (UtenteBean) session.getAttribute("utente");
		
		String sezione = request.getParameter("sezione");
		if(sezione == null || sezione.isBlank())
			sezione = "dati";
		
		CarrelloBean carrello = (CarrelloBean) session.getAttribute("carrello");
		int totaleArticoli = 0;
		
		if(carrello != null)
			for(int quantita = 0; quantita < carrello.getNumeroArticoli(); quantita++)
				totaleArticoli += quantita;
		
		Collection<OrdineBean> ordini = null;
		String dbError = null;
		
		if("ordini".equals(sezione))
		{
			try
			{
				ordini = ordineDao.doRetrieveAllByUser(utente.getId_utente());
			}
			catch(SQLException e)
			{
				e.printStackTrace();
				dbError = "Impossibile caricare gli ordini. Riprova più tardi.";
			}
		}
		
		request.setAttribute("ctx", ctx);
		request.setAttribute("paginaAttiva", "profilo");
		request.setAttribute("utente", utente);
		request.setAttribute("sezione", sezione);
		request.setAttribute("ordini", ordini);
		request.setAttribute("dbError", dbError);
		request.setAttribute("totaleArticoli", totaleArticoli);
		
		request.getRequestDispatcher("/WEB-INF/views/common/Profilo.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
