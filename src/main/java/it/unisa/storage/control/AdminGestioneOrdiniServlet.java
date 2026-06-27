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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import it.unisa.storage.dao.OrdineDao;
import it.unisa.storage.dao.OrdineDaoImpl;
import it.unisa.storage.dao.UtenteDao;
import it.unisa.storage.dao.UtenteDaoImpl;
import it.unisa.storage.model.OrdineBean;
import it.unisa.storage.model.UtenteBean;

/**
 * Servlet implementation class AdminGestioneOrdiniServlet
 */
@WebServlet("/admin/GestioneOrdini")
public class AdminGestioneOrdiniServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DataSource ds;
	private OrdineDao ordineDao;
	private UtenteDao utenteDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminGestioneOrdiniServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    	ds = (DataSource) getServletContext().getAttribute("DataSource");
    	if (ds == null) throw new ServletException("DataSource non disponibile");
		ordineDao = new OrdineDaoImpl(ds);
		utenteDao = new UtenteDaoImpl(ds);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ctx = request.getContextPath();
		HttpSession session = request.getSession(false);
		String token = (session != null) ? (String) session.getAttribute("token") : null;
		if (token == null) {
		    response.sendRedirect(ctx + "/Login");  
		    return;
		}
		UtenteBean utente = (UtenteBean) session.getAttribute("utente");
		
		String dataInizioStr = request.getParameter("dataInizio");
		String dataFineStr = request.getParameter("dataFine");
		String idCliente = request.getParameter("idCliente");
		LocalDateTime dataInizio = null;
		LocalDateTime dataFine = null;
		
		try
		{
			if(dataInizioStr != null && !dataInizioStr.isBlank())
				dataInizio = LocalDate.parse(dataInizioStr).atStartOfDay();
			if(dataFineStr != null && !dataFineStr.isBlank())
				dataFine = LocalDate.parse(dataFineStr).atTime(23, 59, 59);
		}
		catch(DateTimeParseException e)
		{
			request.setAttribute("errFiltro", "Formato data non valido (usare gg/mm/aaaa).");
		}
		
		List<OrdineBean> ordiniFiltrati = new ArrayList<>();
		Collection<UtenteBean> clienti = null;
		String dbError = null;
		
		try
		{
			Collection<OrdineBean> ordini;
			
			if(idCliente != null && !idCliente.isBlank())
				ordini = ordineDao.doRetrieveAllByUser(idCliente);
			else
				ordini = ordineDao.doRetrieveAll();
			
			for(OrdineBean o : ordini) {
				boolean dopoInizio = (dataInizio == null || !o.getData_ordine().isBefore(dataInizio));
				boolean primaFine = (dataFine == null || !o.getData_ordine().isAfter(dataFine));
				if(dopoInizio && primaFine)
					ordiniFiltrati.add(o);
			}
			
			clienti = utenteDao.doRetrieveAll();
		}
		catch(SQLException e){
			dbError = "Impossibile caricare gli ordini.";
		}
		
		 request.setAttribute("ctx",            ctx);
	     request.setAttribute("paginaAttiva",   "admin_ordini");
	     request.setAttribute("utente",         utente);
	     request.setAttribute("ordini",         ordiniFiltrati);
	     request.setAttribute("clienti",        clienti);
	     request.setAttribute("dataInizio",     dataInizioStr);
	     request.setAttribute("dataFine",       dataFineStr);
	     request.setAttribute("idClienteSel",   idCliente);
	     request.setAttribute("dbError",        dbError);
	     request.setAttribute("totaleArticoli", 0);
	     
	     request.getRequestDispatcher("/WEB-INF/views/admin/AdminGestioneOrdini.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
