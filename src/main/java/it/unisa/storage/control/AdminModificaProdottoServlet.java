package it.unisa.storage.control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import it.unisa.storage.dao.ProdottoDao;
import it.unisa.storage.dao.ProdottoDaoImpl;
import it.unisa.storage.model.ProdottoBean;
import it.unisa.storage.model.ProdottoBean.Categoria;
import it.unisa.storage.model.ProdottoBean.Genere;
import it.unisa.storage.model.UtenteBean;

/**
 * Servlet implementation class Ad
 */
@WebServlet("/admin/ModificaProdotto")
public class AdminModificaProdottoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminModificaProdottoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ctx = request.getContextPath();
		HttpSession session = request.getSession(false);
		String token = (session != null) ? (String) session.getAttribute("token") : null;
        
		if (token == null){
        	response.sendRedirect(ctx + "/Login"); 
        	return; 
        }
		
		String idProdotto = request.getParameter("id");
        if (idProdotto == null || idProdotto.isBlank()){
            response.sendRedirect(ctx + "/admin/CatalogoCompleto");
            return;
        }
        
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        ProdottoDao prodottoDao = new ProdottoDaoImpl(ds);
        
        try {
        	ProdottoBean prodotto = prodottoDao.doRetrieveByKey(idProdotto);
        	
        	if(prodotto == null) {
        		response.sendRedirect(ctx + "/admin/CatalogoCompleto");
        		return;
        	}
        	
        	UtenteBean utente = (UtenteBean) session.getAttribute("utente");
        	request.setAttribute("ctx", ctx);
	        request.setAttribute("paginaAttiva", "admin-catalogo");
	        request.setAttribute("utente",       utente);
	        request.setAttribute("prodotto",     prodotto);
	        request.setAttribute("totaleArticoli", 0);
	        
	        request.getRequestDispatcher("/WEB-INF/views/admin/AdminModificaProdotto.jsp").forward(request, response);
        }
        catch(SQLException e) {
        	e.printStackTrace();
        	response.sendRedirect(ctx + "/admin/CatalogoCompleto");
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ctx = request.getContextPath();
	    HttpSession session = request.getSession(false);
	    String token = (session != null) ? (String) session.getAttribute("token") : null;
	     
	    if (token == null) { 
	    	 response.sendRedirect(ctx + "/Login"); 
	    	 return;
	    }
	    
	    request.setCharacterEncoding("UTF-8");

        String idProdotto  = request.getParameter("id_prodotto");
        String modello     = request.getParameter("modello");
        String descrizione = request.getParameter("descrizione");
        String marca       = request.getParameter("marca");
        String prezzoStr   = request.getParameter("prezzo");
        String stockStr    = request.getParameter("stock");
        String categoriaStr= request.getParameter("categoria");
        String genereStr   = request.getParameter("genere");
        String attivoStr   = request.getParameter("attivo");
        double prezzo = Double.parseDouble(prezzoStr.replace(",", "."));
        int stock = Integer.parseInt(stockStr);
        Categoria categoria = Categoria.valueOf(categoriaStr.toUpperCase());
        Genere genere = Genere.valueOf(genereStr.toUpperCase());
        
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        ProdottoDao prodottoDao = new ProdottoDaoImpl(ds);
        
        try {
            ProdottoBean p = prodottoDao.doRetrieveByKey(idProdotto);
            if (p == null) {
                response.sendRedirect(ctx + "/admin/CatalogoCompleto");
                return;
            }

            p.setModello(modello.trim());
            p.setDescrizione(descrizione != null ? descrizione.trim() : "");
            p.setMarca(marca.trim());
            p.setPrezzo(prezzo);
            p.setStock(stock);
            p.setCategoria(categoria);
            p.setGenere(genere);
            p.setAttivo("true".equals(attivoStr));
            prodottoDao.doUpdate(p);
        
            response.sendRedirect(ctx + "/admin/CatalogoCompleto?successo=Prodotto+modificato+con+successo");
        }
        catch(SQLException e)
        {
        	e.printStackTrace();
        	request.setAttribute("errore", "Errore durante la modifica. Riprova.");
        	doGet(request, response);
        }
	}
}
