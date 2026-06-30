package it.unisa.storage.control;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import it.unisa.storage.dao.AccessoriDao;
import it.unisa.storage.dao.AccessoriDaoImpl;
import it.unisa.storage.dao.ImmaginiDao;
import it.unisa.storage.dao.ImmaginiDaoImpl;
import it.unisa.storage.dao.ProdottoDao;
import it.unisa.storage.dao.ProdottoDaoImpl;
import it.unisa.storage.dao.ScarpeDao;
import it.unisa.storage.dao.ScarpeDaoImpl;
import it.unisa.storage.dao.VestitiDao;
import it.unisa.storage.dao.VestitiDaoImpl;
import it.unisa.storage.model.AccessoriBean;
import it.unisa.storage.model.ImmagineBean;
import it.unisa.storage.model.ProdottoBean;
import it.unisa.storage.model.ScarpeBean;
import it.unisa.storage.model.ProdottoBean.Categoria;
import it.unisa.storage.model.ProdottoBean.Genere;
import it.unisa.storage.model.UtenteBean;
import it.unisa.storage.model.VestitiBean;

/**
 * Servlet implementation class Ad
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 25 * 1024 * 1024
)
@WebServlet("/admin/ModificaProdotto")
public class AdminModificaProdottoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DataSource ds;
    private ProdottoDao prodottoDao;
    private ImmaginiDao immaginiDao;
    private ScarpeDao scarpaDao;
    private VestitiDao vestitiDao;
    private AccessoriDao accessoriDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminModificaProdottoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    	ds = (DataSource) getServletContext().getAttribute("DataSource");
    	if (ds == null) throw new ServletException("DataSource non disponibile");
        prodottoDao = new ProdottoDaoImpl(ds);
        immaginiDao = new ImmaginiDaoImpl(ds);
        scarpaDao = new ScarpeDaoImpl(ds);
	    vestitiDao = new VestitiDaoImpl(ds);
	    accessoriDao = new AccessoriDaoImpl(ds);
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
	        session.setAttribute("idProdotto", idProdotto);
	        
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

	    String idProdotto = (String) session.getAttribute("idProdotto");
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
            
            if (categoria == Categoria.SCARPA) {
	            ScarpeBean scarpa = new ScarpeBean();
	            scarpa.setId_prodotto(idProdotto);
	            scarpa.setTipo_suola(request.getParameter("tipo_suola"));
	            scarpa.setMateriale(request.getParameter("materiale_scarpa"));
	            scarpaDao.doUpdate(scarpa);
	            
	        } else if (categoria == Categoria.VESTITO) {
	            VestitiBean vestito = new VestitiBean();
	            vestito.setId_prodotto(idProdotto);
	            vestito.setTipovita(request.getParameter("tipo_vita"));
	            vestito.setTessuto(request.getParameter("tessuto"));
	            vestito.setStagione(request.getParameter("stagione"));
	            vestito.setTipo_collo(request.getParameter("tipo_collo"));
	
	            String catVestito = request.getParameter("categoriaVestito");
	            if (catVestito != null && !catVestito.isBlank())
	                vestito.setCategoriaVestito(VestitiBean.CategoriaVestiti.valueOf(catVestito.toUpperCase()));
	
	            String manica = request.getParameter("manica");
	            if (manica != null && !manica.isBlank())
	                vestito.setManica(VestitiBean.Manica.valueOf(manica.toUpperCase()));
	
	            String gamba = request.getParameter("gamba");
	            if (gamba != null && !gamba.isBlank())
	                vestito.setGamba(VestitiBean.Gamba.valueOf(gamba.toUpperCase()));
	
	            vestitiDao.doUpdate(vestito);
	        
	        }else if (categoria == Categoria.ACCESSORIO) {
	            AccessoriBean accessorio = new AccessoriBean();
	            accessorio.setId_prodotto(idProdotto);
	            accessorio.setTipo_accessori(request.getParameter("tipo_accessorio"));
	            accessorio.setMateriali(request.getParameter("materiale_accessorio"));
	            accessoriDao.doUpdate(accessorio);
	        }
        
            try {
	            salvaImmagini(request, idProdotto);
	        } catch (Exception e) {
	            log("Errore salvataggio immagini: " + e.getMessage());
	        }
            
            response.sendRedirect(ctx + "/admin/CatalogoCompleto?successo=Prodotto+modificato+con+successo");
        }
        catch(SQLException e)
        {
        	e.printStackTrace();
        	request.setAttribute("errore", "Errore durante la modifica. Riprova.");
        	doGet(request, response);
        }
	}
	
	private void salvaImmagini(HttpServletRequest request, String idProdotto) throws Exception {

	    String uploadDir = getServletContext().getRealPath(File.separator + "img" + File.separator + "prodotti");
	    File folder = new File(uploadDir);
	    if (!folder.exists()) folder.mkdirs();

	    List<String> tipiAccettati = Arrays.asList("image/jpeg", "image/png", "image/webp", "image/gif");
	    List<ImmagineBean> immaginiAttuali = (List<ImmagineBean>) immaginiDao.doRetrieveAllByIdProdotto(idProdotto);

	    for (int i = 1; i <= 3; i++) {

	        Part part = request.getPart("immagine" + i);

	        if (part == null || part.getSize() == 0
	                || part.getSubmittedFileName() == null
	                || part.getSubmittedFileName().isBlank()) continue;

	        String mimeType = part.getContentType();
	        if (mimeType == null || !tipiAccettati.contains(mimeType.toLowerCase())) continue;

	        String nomeOriginale = Paths.get(part.getSubmittedFileName()).getFileName().toString();
	        String estensione = "";
	        int dot = nomeOriginale.lastIndexOf('.');
	        if (dot > 0) estensione = nomeOriginale.substring(dot).toLowerCase();

	        String nomeFile = "img_" + System.currentTimeMillis() + "_" + idProdotto + estensione;
	        String nuovoPathname = "img/prodotti/" + nomeFile;

	        part.write(uploadDir + File.separator + nomeFile);

	        if (immaginiAttuali != null && immaginiAttuali.size() >= i) {
	            // Esiste immagine in posizione i → sostituisci
	            String vecchioPathname = immaginiAttuali.get(i - 1).getPathname();

	            // Elimina vecchio file fisico
	            File vecchioFile = new File(getServletContext().getRealPath("/" + vecchioPathname));
	            if (vecchioFile.exists()) vecchioFile.delete();

	            // Aggiorna pathname nel DB (oldPathname → newPathname)
	            immaginiDao.doUpdate(vecchioPathname, nuovoPathname);
	        } else {
	            // Non esiste immagine in posizione i → inserisci
	            immaginiDao.doSave(new ImmagineBean() {{
	                setPathname(nuovoPathname);
	                setMime_type(mimeType);
	                setId_prodotto(prodottoDao.doRetrieveByKey(idProdotto));
	            }}, idProdotto);
	        }
	    }
	}
}
