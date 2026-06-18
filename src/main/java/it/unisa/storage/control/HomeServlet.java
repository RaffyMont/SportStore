package it.unisa.storage.control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import it.unisa.storage.dao.ImmaginiDao;
import it.unisa.storage.dao.ImmaginiDaoImpl;
import it.unisa.storage.dao.ProdottoDao;
import it.unisa.storage.dao.ProdottoDaoImpl;
import it.unisa.storage.model.ImmagineBean;
import it.unisa.storage.model.ProdottoBean;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/Home")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final int MAX_PER_CATEGORIA = 4;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		
		ProdottoDao prodottoDao = new ProdottoDaoImpl(ds);
		ImmaginiDao immaginiDao = new ImmaginiDaoImpl(ds);
		
		try {
			List<ProdottoBean> scarpe = new ArrayList<>();
			List<ProdottoBean> vestiti = new ArrayList<>();
			List<ProdottoBean> accessori = new ArrayList<>();
			
			List<ProdottoBean> tutti = (List<ProdottoBean>) prodottoDao.doRetrieveAll();
			
			for(ProdottoBean p : tutti) {
				if(!p.isAttivo())
					continue;
				switch(p.getCategoria())
				{
					case SCARPA:
						if(scarpe.size() < MAX_PER_CATEGORIA) 
							scarpe.add(p);
						break;
						
					case VESTITO:
						if(vestiti.size() < MAX_PER_CATEGORIA) 
							vestiti.add(p);
						break;

					case ACCESSORIO:
						if(accessori.size() < MAX_PER_CATEGORIA) 
							accessori.add(p);
						break;

				}
			}
			
			Map<String, String> immaginiMap = new LinkedHashMap<>();
			
			List<ProdottoBean> inEvidenza = new ArrayList<>();
			inEvidenza.addAll(scarpe);
			inEvidenza.addAll(vestiti);
			inEvidenza.addAll(accessori);
			
			for(ProdottoBean p : inEvidenza)
			{
				List<ImmagineBean> imgs = (List<ImmagineBean>) immaginiDao.doRetrieveAllByIdProdotto(p.getId_prodotto());
				
				if(imgs != null && !imgs.isEmpty())
					immaginiMap.put(p.getId_prodotto(), imgs.get(0).getPathname());
				else
					immaginiMap.put(p.getId_prodotto(), null);
			}
			
			@SuppressWarnings("unchecked")
			Map<String, Integer> carrello = (Map<String, Integer>) request.getSession().getAttribute("carrello");
			int totaleArticoli = 0;
			
			if(carrello != null)
				for(int quantità : carrello.values())
					totaleArticoli += quantità;
			
			request.setAttribute("scarpeEvidenza", scarpe);
			request.setAttribute("vestitiEvidenza", vestiti);
			request.setAttribute("accessoriEvidenza", accessori);
			request.setAttribute("immaginiMap", immaginiMap);
			request.setAttribute("totaleArticoli", totaleArticoli);
			request.setAttribute("utente", request.getSession().getAttribute("utente"));
		}
		
		catch(SQLException e)
		{
			log("HomeServlet: errore DB - " + e.getMessage());
			request.setAttribute("dbErrore", "Impossibile caricare i prodotti al momento.");
		}
		
		request.getRequestDispatcher("/WEB-INF/views/common/Home.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
