package it.unisa.storage.control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
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
import it.unisa.storage.model.ProdottoBean.Categoria;
import it.unisa.storage.model.ProdottoBean.Genere;

/**
 * Servlet implementation class CatalogoServlet
 */
@WebServlet("/Catalogo")
public class CatalogoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CatalogoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String genereParam = request.getParameter("genere");
		Genere genere;
		
		try
		{
			genere = Genere.valueOf(genereParam.toUpperCase());
		}
		catch(IllegalArgumentException e)
		{
			response.sendRedirect(request.getContextPath() + "/Home");
			return;
		}
		
		String categoriaParam = request.getParameter("categoria");
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		ProdottoDao prodottoDao = new ProdottoDaoImpl(ds);
		ImmaginiDao immaginiDao = new ImmaginiDaoImpl(ds);
		
		List<ProdottoBean> prodotti = null;
		Map<String, String> immagini = new LinkedHashMap<String, String>();
		
		try
		{
			Collection<ProdottoBean> prodotti2 = prodottoDao.doRetrieveByGenere(genere.name());
			prodotti = new ArrayList<ProdottoBean>(prodotti2);
			
			if(categoriaParam != null && !categoriaParam.isBlank())
			{
				try
				{
					Categoria categoriaP = Categoria.valueOf(categoriaParam.toUpperCase());
					List<ProdottoBean> filtrati = new ArrayList<>();

					for (ProdottoBean p : prodotti) {
					    if (p.getCategoria() == categoriaP) {
					        filtrati.add(p);
					    }
					}

					prodotti = filtrati;
				}
				catch (IllegalArgumentException e) {
					categoriaParam = null;
				}
			}
			
			for(ProdottoBean p : prodotti)
			{
				List<ImmagineBean> imgs = (List<ImmagineBean>) immaginiDao.doRetrieveAllByIdProdotto(p.getId_prodotto());
				String path = null;
				
				if(imgs != null && !imgs.isEmpty())
					path = imgs.get(0).getPathname();
				immagini.put(p.getId_prodotto(), path);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		@SuppressWarnings("unchecked")
		Map<String, Integer> carrello = (Map<String, Integer>) request.getSession().getAttribute("carrello");
		int totale = 0;
		
		if(carrello != null)
			for(int quantita : carrello.values())
				totale += quantita;
		
		String genereLabel;
		
		if(genere.name() == null || genere.name().isEmpty())
			genereLabel = genere.name();
		else
			genereLabel = genere.name().charAt(0) + genere.name().substring(1).toLowerCase();

		
		request.setAttribute("genereLabel", genereLabel);
		request.setAttribute("genereParam", genere.name());
		request.setAttribute("prodotti", prodotti);
		request.setAttribute("immagini", immagini);
		request.setAttribute("categoriaFiltro", categoriaParam);
		request.setAttribute("totale", totale);
		request.setAttribute("utente", request.getSession().getAttribute("utente"));
		request.getRequestDispatcher("/WEB-INF/views/common/Catalogo.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
