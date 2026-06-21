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

import it.unisa.storage.dao.ImmaginiDao;
import it.unisa.storage.dao.ImmaginiDaoImpl;
import it.unisa.storage.dao.ProdottoDao;
import it.unisa.storage.dao.ProdottoDaoImpl;
import it.unisa.storage.dao.SupportoColoreDao;
import it.unisa.storage.dao.SupportoColoreDaoImpl;
import it.unisa.storage.dao.SupportoTagliaDao;
import it.unisa.storage.dao.SupportoTagliaDaoImpl;
import it.unisa.storage.model.ImmagineBean;
import it.unisa.storage.model.ProdottoBean;
import it.unisa.storage.model.SupportoColoreBean;
import it.unisa.storage.model.SupportoTagliaBean;
import it.unisa.storage.model.UtenteBean;

/**
 * Servlet implementation class DettaglioProdottoServlet
 */
@WebServlet("/DettaglioProdotto")
public class DettaglioProdottoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DettaglioProdottoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String ctx  = request.getContextPath();
		String idProdotto = request.getParameter("id");
		
		if(idProdotto == null || idProdotto.isBlank())
		{
			response.sendRedirect(ctx + "/Home");
			return;
		}
		
		HttpSession session = request.getSession(false);
        String     token   = (session != null) ? (String)     session.getAttribute("token")  : null;
        UtenteBean utente  = (token   != null) ? (UtenteBean) session.getAttribute("utente") : null;
		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		ProdottoDao prodottoDao = new ProdottoDaoImpl(ds);
		ImmaginiDao immaginiDao = new ImmaginiDaoImpl(ds);
		SupportoColoreDao coloreDao = new SupportoColoreDaoImpl(ds);
		SupportoTagliaDao tagliaDao = new SupportoTagliaDaoImpl(ds);
		
		try
		{
			ProdottoBean prodotto = prodottoDao.doRetrieveByKey(idProdotto);
			if(prodotto == null || !prodotto.isAttivo())
			{
				response.sendRedirect(ctx + "/Home");
				return;
			}
			
			Collection<ImmagineBean> immagini = immaginiDao.doRetrieveAllByIdProdotto(idProdotto);
			Collection<SupportoColoreBean> colori = coloreDao.doRetrieveAllByProdotto(idProdotto);
			Collection<SupportoTagliaBean> taglie = tagliaDao.doRetrieveAllByProdotto(idProdotto);
			
			Map<String, Integer> carrello =  (Map<String, Integer>) (session != null ? session.getAttribute("carrello") : null);;
			int totale = 0;
			
			if(carrello != null)
				for(int quantita : carrello.values())
					totale += quantita;
			
			request.setAttribute("ctx", ctx);
			request.setAttribute("paginaAttiva", "");
			request.setAttribute("utente", utente);
            request.setAttribute("prodotto", prodotto);
            request.setAttribute("immagini", immagini);
            request.setAttribute("colori", colori);
            request.setAttribute("taglie", taglie);
            request.setAttribute("totale", totale);
            
            request.getRequestDispatcher("/WEB-INF/views/common/DettaglioProdotto.jsp").forward(request, response);

		}
		catch (SQLException e) {
			e.printStackTrace();
			response.sendRedirect(ctx + "/Home");
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
