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
import java.util.Map;

import javax.sql.DataSource;

import it.unisa.storage.dao.DettagliOrdineDao;
import it.unisa.storage.dao.DettagliOrdineDaoImpl;
import it.unisa.storage.dao.OrdineDao;
import it.unisa.storage.dao.OrdineDaoImpl;
import it.unisa.storage.model.DettagliOrdineBean;
import it.unisa.storage.model.OrdineBean;
import it.unisa.storage.model.UtenteBean;

/**
 * Servlet implementation class DettaglioOrdineServlet
 */
@WebServlet("/DettaglioOrdine")
public class DettaglioOrdineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DettaglioOrdineServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ctx = request.getContextPath();
		HttpSession session = request.getSession(false);
		UtenteBean utente = null;
		
		if(session != null)
			utente = (UtenteBean) session.getAttribute("utente");
		
		if(utente == null)
		{
			response.sendRedirect(ctx + "/Login");
			return;
		}
		
		String idOrdine = request.getParameter("id");
		if(idOrdine == null || idOrdine.isBlank())
		{
			response.sendRedirect(ctx + "/Home");
			return;
		}
		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		OrdineDao ordineDao = new OrdineDaoImpl(ds);
		DettagliOrdineDao dettaglioDao = new DettagliOrdineDaoImpl(ds);
		
		try
		{
			OrdineBean ordine = null;
			Collection<OrdineBean> ordini = ordineDao.doRetrieveAll();
			
			for(OrdineBean o : ordini)
			{
				if(o.getId_ordine().equals(idOrdine)) {
					ordine = o;
					break;
				}
			}
			
			if(ordine == null)
			{
				response.sendRedirect(ctx + "/Home");
				return;
			}
			
			Collection<DettagliOrdineBean> dettagli = dettaglioDao.doRetrieveAllByOrdine(idOrdine);
			
			@SuppressWarnings("unchecked")
			Map<String, Integer> carrello = (Map<String, Integer>) session.getAttribute("carrello");
			int totaleArticoli = 0;
			if(carrello != null)
				for(int quantita : carrello.values())
					totaleArticoli += quantita;
			
			request.setAttribute("ctx", ctx);
            request.setAttribute("paginaAttiva", "ordini");
            request.setAttribute("utente", utente);
            request.setAttribute("ordine", ordine);
            request.setAttribute("dettagli", dettagli);
            request.setAttribute("totaleArticoli", totaleArticoli);

            request.getRequestDispatcher("/WEB-INF/views/common/DettaglioOrdine.jsp").forward(request, response);
		}
		catch(SQLException e)
		{
			request.setAttribute("errore", "Errore nel caricamento dell'ordine.");
            request.getRequestDispatcher("/WEB-INF/views/common/DettaglioOrdine.jsp").forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
