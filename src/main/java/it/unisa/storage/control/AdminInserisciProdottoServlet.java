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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.sql.DataSource;

import it.unisa.storage.dao.ColoreDao;
import it.unisa.storage.dao.ColoreDaoImpl;
import it.unisa.storage.dao.ProdottoDao;
import it.unisa.storage.dao.ProdottoDaoImpl;
import it.unisa.storage.dao.SupportoColoreDao;
import it.unisa.storage.dao.SupportoColoreDaoImpl;
import it.unisa.storage.dao.SupportoTagliaDao;
import it.unisa.storage.dao.SupportoTagliaDaoImpl;
import it.unisa.storage.dao.TagliaDao;
import it.unisa.storage.dao.TagliaDaoImpl;
import it.unisa.storage.model.ProdottoBean.Categoria;
import it.unisa.storage.model.ProdottoBean.Genere;
import it.unisa.storage.model.SupportoColoreBean;
import it.unisa.storage.model.SupportoTagliaBean;
import it.unisa.storage.model.ProdottoBean;
import it.unisa.storage.model.UtenteBean;

/**
 * Servlet implementation class AdminInserisciProdottoServlet
 */
@WebServlet("/admin/InserisciProdotto")
public class AdminInserisciProdottoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DataSource ds;
    private ProdottoDao prodottoDao;
    private SupportoTagliaDao supportoTagliaDao;
    private TagliaDao tagliaDao;
    private SupportoColoreDao supportoColoreDao;
    private ColoreDao coloreDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminInserisciProdottoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init(ServletConfig config) throws ServletException {
    	 super.init(config);
    	 ds = (DataSource) getServletContext().getAttribute("DataSource");
    	 if (ds == null) throw new ServletException("DataSource non disponibile");
         prodottoDao = new ProdottoDaoImpl(ds);
         supportoTagliaDao = new SupportoTagliaDaoImpl(ds);
	     tagliaDao = new TagliaDaoImpl(ds);
	     supportoColoreDao = new SupportoColoreDaoImpl(ds);
	     coloreDao = new ColoreDaoImpl(ds);
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
		
		UtenteBean utente = (UtenteBean) session.getAttribute("utente");
		request.setAttribute("ctx",          ctx);
        request.setAttribute("paginaAttiva", "admin_catalogo");
        request.setAttribute("utente",       utente);
        request.setAttribute("totaleArticoli", 0);
        
        request.getRequestDispatcher("/WEB-INF/views/admin/AdminInserisciProdotto.jsp").forward(request, response);

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
		
		request.setCharacterEncoding("UTF-8");
		
		String modello     = request.getParameter("modello");
        String descrizione = request.getParameter("descrizione");
        String marca       = request.getParameter("marca");
        String prezzoStr   = request.getParameter("prezzo");
        String stockStr    = request.getParameter("stock");
        String categoriaStr= request.getParameter("categoria");
        String genereStr   = request.getParameter("genere");
        double prezzo = Double.parseDouble(prezzoStr.replace(",", "."));;
        int stock = Integer.parseInt(stockStr);;
        Categoria categoria = Categoria.valueOf(categoriaStr.toUpperCase());
        Genere genere = Genere.valueOf(genereStr.toUpperCase());
        
        try{
        	String id;
        	do {
        		id = "PRD" + UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
        	}while(prodottoDao.doRetrieveByKey(id) != null);
        
	        ProdottoBean p = new ProdottoBean();
	        p.setId_prodotto(id);
	        p.setModello(modello.trim());
	        p.setDescrizione(descrizione != null ? descrizione.trim() : "");
	        p.setMarca(marca.trim());
	        p.setPrezzo(prezzo);
	        p.setStock(stock);
	        p.setCategoria(categoria);
	        p.setGenere(genere);
	        p.setAttivo(true);
	        prodottoDao.doSave(p);

	        String[] taglie;
	        if (categoria == Categoria.SCARPA) {
	            taglie = new String[]{"38", "39", "40", "41", "42", "43", "44", "45"};
	        } else if (categoria == Categoria.VESTITO) {
	            taglie = new String[]{"XS", "S", "M", "L", "XL", "XXL"};
	        } else {
	            taglie = new String[]{"TU"};
	        }

	        for (String t : taglie) {
	            SupportoTagliaBean st = new SupportoTagliaBean();
	            st.setId_prodotto(p);
	            st.setTaglia(tagliaDao.doRetrieveByKey(t));
	            supportoTagliaDao.doSave(st);
	        }

	        String[] coloriDisponibili = {
	            "Nero", "Bianco", "Grigio", "Rosso", "Blu",
	            "Verde", "Giallo Fluo", "Arancione", "Navy", "Celeste"
	        };

	        Random random = new Random();

	        int quantiColori = random.nextInt(10) + 1;

	        List<String> listaColori = new ArrayList<>(Arrays.asList(coloriDisponibili));
	        Collections.shuffle(listaColori);

	        for (int i = 0; i < quantiColori; i++) {
	            String nomeColore = listaColori.get(i);

	            SupportoColoreBean sc = new SupportoColoreBean();
	            sc.setId_prodotto(p);
	            sc.setNome(coloreDao.doRetrieveByKey(nomeColore));

	            supportoColoreDao.doSave(sc);
	        }
	        
	        response.sendRedirect(ctx + "/admin/CatalogoCompleto?successo=Prodotto+inserito+con+successo");
        }
        catch(SQLException e)
        {
        	e.printStackTrace();
        	request.setAttribute("errore", "Errore durante il salvataggio. Riprova.");
        	request.getRequestDispatcher("/WEB-INF/views/admin/AdminInserisciProdotto.jsp").forward(request, response);
        }

	}

}
