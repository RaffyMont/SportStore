package it.unisa.storage.control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import it.unisa.storage.dao.ImmaginiDao;
import it.unisa.storage.dao.ImmaginiDaoImpl;
import it.unisa.storage.dao.ProdottoDao;
import it.unisa.storage.dao.ProdottoDaoImpl;
import it.unisa.storage.model.CarrelloBean;
import it.unisa.storage.model.CarrelloItemBean;
import it.unisa.storage.model.ImmagineBean;
import it.unisa.storage.model.ProdottoBean;

/**
 * Servlet implementation class AggiungiAlCarrelloServlet
 */
@WebServlet("/AggiungiAlCarrello")
public class AggiungiAlCarrelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AggiungiAlCarrelloServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String idProdotto = request.getParameter("id_prodotto");
        String colore = request.getParameter("colore");
        String taglia = request.getParameter("taglia");
        String qStr       = request.getParameter("quantita");
        int quantita      = 1;
        if (qStr != null && !qStr.isBlank()) {
            try { quantita = Integer.parseInt(qStr); } catch (NumberFormatException e) { quantita = 1; }
        }
        if (quantita <= 0) quantita = 1;
        
        if (idProdotto == null || idProdotto.isBlank()) {
            out.print("{\"success\":false,\"message\":\"Prodotto non specificato.\"}");
            return;
        }
        
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        ProdottoDao prodottoDao = new ProdottoDaoImpl(ds);
        ImmaginiDao immaginiDao = new ImmaginiDaoImpl(ds);
        
        try
        {
        	ProdottoBean prodotto = prodottoDao.doRetrieveByKey(idProdotto);

            if (prodotto == null || !prodotto.isAttivo()) {
                out.print("{\"success\":false,\"message\":\"Prodotto non disponibile.\"}");
                return;
            }

            if (prodotto.getStock() <= 0) {
                out.print("{\"success\":false,\"message\":\"Prodotto esaurito.\"}");
                return;
            }
            
            String immagine = null;
            List<ImmagineBean> immagini = (List<ImmagineBean>) immaginiDao.doRetrieveAllByIdProdotto(idProdotto);

            if (immagini != null && !immagini.isEmpty())
                immagine = immagini.get(0).getPathname();
            
            CarrelloItemBean item = new CarrelloItemBean();
            item.setId_prodotto(idProdotto);
            item.setModello(prodotto.getModello());
            item.setMarca(prodotto.getMarca());
            item.setPrezzo(prodotto.getPrezzo());
            item.setQuantita(quantita);
            item.setColore(colore);
            item.setTaglia(taglia);
            item.setImmagine(immagine);
            
            HttpSession session = request.getSession(true);
            CarrelloBean carrello = (CarrelloBean) session.getAttribute("carrello");
            if (carrello == null) {
                carrello = new CarrelloBean();
                session.setAttribute("carrello", carrello);
            }
            
            carrello.aggiungi(item);
            int totale = carrello.getNumeroArticoli();
            out.print("{\"success\":true,\"totaleArticoli\":" + totale + "}");
        }
        catch (SQLException e) {
            e.printStackTrace();
            out.print("{\"success\":false,\"message\":\"Errore di sistema. Riprova.\"}");
        }
	}
}
