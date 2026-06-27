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

import it.unisa.storage.dao.ProdottoDao;
import it.unisa.storage.dao.ProdottoDaoImpl;
import it.unisa.storage.model.ProdottoBean;
import it.unisa.storage.model.UtenteBean;

/**
 * Servlet implementation class AdminCatalogoCompletoServlet
 */
@WebServlet("/admin/CatalogoCompleto")
public class AdminCatalogoCompletoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DataSource ds;
	private ProdottoDao prodottoDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminCatalogoCompletoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    	ds = (DataSource) getServletContext().getAttribute("DataSource");
    	if (ds == null) throw new ServletException("DataSource non disponibile");
		prodottoDao = new ProdottoDaoImpl(ds);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ctx = request.getContextPath();
		HttpSession session = request.getSession(false);
		String token = (session != null) ? (String) session.getAttribute("token") : null;
		
		if(token == null)
		{
			response.sendRedirect(ctx + "/Login");
			return;
		}
		
		UtenteBean utente = (UtenteBean) session.getAttribute("utente");
		String successo = request.getParameter("successo");
		Collection<ProdottoBean> prodotti = null;
		String dbError = null;
		
		try
		{
			prodotti = prodottoDao.doRetrieveAll();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			dbError = "Impossibile caricare il catalogo:";
		}
		
		 request.setAttribute("ctx",            ctx);
	     request.setAttribute("paginaAttiva",   "admin-catalogo");
         request.setAttribute("utente",         utente);
         request.setAttribute("prodotti",       prodotti);
         request.setAttribute("dbError",        dbError);
         request.setAttribute("successo",       successo);
         request.setAttribute("totaleArticoli", 0);
         
         request.getRequestDispatcher("/WEB-INF/views/admin/AdminCatalogo.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ctx = request.getContextPath();
		HttpSession session = request.getSession(false);
		String token = (session != null) ? (String) session.getAttribute("token") : null;
	    if (token == null){ 
	    	response.sendRedirect(ctx + "/Login"); 
	    	return; 
	    }
	    
	    String action = request.getParameter("action");
	    String idProdotto = request.getParameter("id");
	    
	    if(idProdotto == null || idProdotto.isBlank())
	    {
	    	response.sendRedirect(ctx + "/Login");
	    	return;
	    }
	    
	    DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
	    ProdottoDao prodottoDao = new ProdottoDaoImpl(ds);
	    
	    try {
	    	switch(action != null ? action : "")
	    	{
		    	case "disattiva":
		    		prodottoDao.doChangeStatus(idProdotto, false);
		    		response.sendRedirect(ctx + "/admin/CatalogoCompleto?successo=Prodotto+rimosso+dal+catalogo");
		    		break;
	    		
		    	case "riattiva":
		    		prodottoDao.doChangeStatus(idProdotto, true);
		    		response.sendRedirect(ctx + "/admin/CatalogoCompleto?successo=Prodotto+riattivato+dal+catalogo");
		    		break;
		    	
		    	default:
		    		response.sendRedirect(ctx + "/admin/CatalogoCompleto");
	    	}
	    }
	    catch(SQLException e)
	    {
	    	e.printStackTrace();
	    	response.sendRedirect(ctx + "/admin/CatalogoCompleto?errore=Errore+durante+l'operazione");
	    }

	}

}
