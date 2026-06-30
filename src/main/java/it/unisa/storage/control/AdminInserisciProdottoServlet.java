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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.sql.DataSource;

import it.unisa.storage.dao.AccessoriDao;
import it.unisa.storage.dao.AccessoriDaoImpl;
import it.unisa.storage.dao.ColoreDao;
import it.unisa.storage.dao.ColoreDaoImpl;
import it.unisa.storage.dao.ImmaginiDao;
import it.unisa.storage.dao.ImmaginiDaoImpl;
import it.unisa.storage.dao.ProdottoDao;
import it.unisa.storage.dao.ProdottoDaoImpl;
import it.unisa.storage.dao.ScarpeDao;
import it.unisa.storage.dao.ScarpeDaoImpl;
import it.unisa.storage.dao.SupportoColoreDao;
import it.unisa.storage.dao.SupportoColoreDaoImpl;
import it.unisa.storage.dao.SupportoTagliaDao;
import it.unisa.storage.dao.SupportoTagliaDaoImpl;
import it.unisa.storage.dao.TagliaDao;
import it.unisa.storage.dao.TagliaDaoImpl;
import it.unisa.storage.dao.VestitiDao;
import it.unisa.storage.dao.VestitiDaoImpl;
import it.unisa.storage.model.ProdottoBean.Categoria;
import it.unisa.storage.model.ProdottoBean.Genere;
import it.unisa.storage.model.ScarpeBean;
import it.unisa.storage.model.SupportoColoreBean;
import it.unisa.storage.model.SupportoTagliaBean;
import it.unisa.storage.model.AccessoriBean;
import it.unisa.storage.model.ImmagineBean;
import it.unisa.storage.model.ProdottoBean;
import it.unisa.storage.model.UtenteBean;
import it.unisa.storage.model.VestitiBean;

/**
 * Servlet implementation class AdminInserisciProdottoServlet
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 25 * 1024 * 1024
)
@WebServlet("/admin/InserisciProdotto")
public class AdminInserisciProdottoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DataSource ds;
    private ProdottoDao prodottoDao;
    private SupportoTagliaDao supportoTagliaDao;
    private TagliaDao tagliaDao;
    private SupportoColoreDao supportoColoreDao;
    private ColoreDao coloreDao;
    private ImmaginiDao immaginiDao;
    private ScarpeDao scarpaDao;
    private VestitiDao vestitiDao;
    private AccessoriDao accessoriDao;
       
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
	        
	        if (categoria == Categoria.SCARPA) {
	            ScarpeBean scarpa = new ScarpeBean();
	            scarpa.setId_prodotto(id);
	            scarpa.setTipo_suola(request.getParameter("tipo_suola"));
	            scarpa.setMateriale(request.getParameter("materiale_scarpa"));
	            scarpaDao.doSave(scarpa);
	            
	        } else if (categoria == Categoria.VESTITO) {
	            VestitiBean vestito = new VestitiBean();
	            vestito.setId_prodotto(id);
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
	
	            vestitiDao.doSave(vestito);
	        
	        }else if (categoria == Categoria.ACCESSORIO) {
	            AccessoriBean accessorio = new AccessoriBean();
	            accessorio.setId_prodotto(id);
	            accessorio.setTipo_accessori(request.getParameter("tipo_accessorio"));
	            accessorio.setMateriali(request.getParameter("materiale_accessorio"));
	            accessoriDao.doSave(accessorio);
	        }
	        
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
	        try {
	            salvaImmagini(request, id);
	        } catch (Exception e) {
	            log("Errore salvataggio immagini: " + e.getMessage());
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
	
	private void salvaImmagini(HttpServletRequest request, String idProdotto) throws Exception {

	    /* Cartella fisica dove salvare i file */
	    String uploadDir = getServletContext().getRealPath(
	            File.separator + "img" + File.separator + "prodotti");
	    File folder = new File(uploadDir);
	    if (!folder.exists()) folder.mkdirs();

	    /* Tipi MIME accettati */
	    List<String> tipiAccettati = Arrays.asList("image/jpeg", "image/png", "image/webp", "image/gif");

	    for (int i = 1; i <= 3; i++) {

	        Part part = request.getPart("immagine" + i);

	        /* Salta se il campo è vuoto o non è stato inviato */
	        if (part == null
	                || part.getSize() == 0
	                || part.getSubmittedFileName() == null
	                || part.getSubmittedFileName().isBlank()) {
	            continue;
	        }

	        /* Controlla tipo MIME */
	        String mimeType = part.getContentType();
	        if (mimeType == null || !tipiAccettati.contains(mimeType.toLowerCase())) {
	            log("salvaImmagini: tipo non accettato per immagine" + i + " → " + mimeType);
	            continue;
	        }

	        /* Ricava estensione dal nome originale */
	        String nomeOriginale = Paths.get(part.getSubmittedFileName())
	                                    .getFileName().toString();
	        String estensione = "";
	        int dot = nomeOriginale.lastIndexOf('.');
	        if (dot > 0) estensione = nomeOriginale.substring(dot).toLowerCase();

	        /* Nome univoco: timestamp + idProdotto */
	        String nomeFile = "img_" + System.currentTimeMillis() + "_" + idProdotto + estensione;

	        /* Salva fisicamente il file */
	        String percorsoAssoluto = uploadDir + File.separator + nomeFile;
	        part.write(percorsoAssoluto);

	        /* Salva il path relativo nel DB */
	        ImmagineBean img = new ImmagineBean();
	        img.setPathname("img/prodotti/" + nomeFile);
	        immaginiDao.doSave(img, idProdotto);
	    }
	}

}
