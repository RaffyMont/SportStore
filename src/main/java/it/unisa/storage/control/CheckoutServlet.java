package it.unisa.storage.control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

import javax.sql.DataSource;

import it.unisa.storage.dao.DettagliOrdineDao;
import it.unisa.storage.dao.DettagliOrdineDaoImpl;
import it.unisa.storage.dao.IndirizzoDao;
import it.unisa.storage.dao.IndirizzoDaoImpl;
import it.unisa.storage.dao.OrdineDao;
import it.unisa.storage.dao.OrdineDaoImpl;
import it.unisa.storage.dao.ProdottoDao;
import it.unisa.storage.dao.ProdottoDaoImpl;
import it.unisa.storage.model.CarrelloBean;
import it.unisa.storage.model.CarrelloItemBean;
import it.unisa.storage.model.DettagliOrdineBean;
import it.unisa.storage.model.IndirizzoBean;
import it.unisa.storage.model.OrdineBean;
import it.unisa.storage.model.OrdineBean.Stato;
import it.unisa.storage.model.ProdottoBean;
import it.unisa.storage.model.UtenteBean;

/**
 * Servlet implementation class CheckoutServlet
 */
@WebServlet("/Checkout")
public class CheckoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckoutServlet() {
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
		
		if(token == null) {
			response.sendRedirect(ctx + "/Login?redirect=" + ctx + "/Checkout");
			return;
		}
		
		UtenteBean utente = (UtenteBean) session.getAttribute("utente");
		CarrelloBean carrello = (CarrelloBean) session.getAttribute("carrello");
		
		if(carrello == null || carrello.isEmpty()){
			response.sendRedirect(ctx + "/Carrello");
			return;
		}
		
		int totaleArticoli = carrello.getNumeroArticoli();
		
		request.setAttribute("ctx", ctx);
		request.setAttribute("paginaAttiva", "");
        request.setAttribute("utente", utente);
        request.setAttribute("carrello", carrello);
        request.setAttribute("totaleArticoli", totaleArticoli);
        
        request.getRequestDispatcher("/WEB-INF/views/common/Checkout.jsp").forward(request, response);

	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String ctx = request.getContextPath();
		HttpSession session = request.getSession(false);
		String token = (session != null) ? (String) session.getAttribute("token") : null;
		
		if(token == null)
		{
			response.sendRedirect(ctx + "/Login");
			return;
		}
		
		UtenteBean utente = (UtenteBean) session.getAttribute("utente");
		CarrelloBean carrello = (CarrelloBean) session.getAttribute("carrello");
		
		if(carrello == null || carrello.isEmpty()) {
			response.sendRedirect(ctx + "/Carrello");
			return;
		}
		
		String via = request.getParameter("via");
		String civico = request.getParameter("civico");
		String citta = request.getParameter("citta");
		String cap = request.getParameter("cap");
		String provincia = request.getParameter("provincia");
		String stato = request.getParameter("stato");
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		IndirizzoDao indirizzoDao = new IndirizzoDaoImpl(ds);
		OrdineDao ordineDao = new OrdineDaoImpl(ds);
		DettagliOrdineDao dettagliDao = new DettagliOrdineDaoImpl(ds);
		ProdottoDao prodottoDao = new ProdottoDaoImpl(ds);
		
		try
		{
			IndirizzoBean indirizzo = new IndirizzoBean();
			indirizzo.setProvincia(provincia);
			indirizzo.setStato(stato);
			indirizzo.setCitta(citta);
			indirizzo.setCAP(cap);
			indirizzo.setVia(via);
			indirizzo.setCivico(civico);
			indirizzoDao.doSave(indirizzo);
			
			Collection<IndirizzoBean> indirizzi = indirizzoDao.doRetrieveAll();
			IndirizzoBean salvato = null;
			
			for(IndirizzoBean i : indirizzi)
				salvato = i;
			
			if(salvato == null)
				throw new SQLException("Errore salvataggio indirizzo.");
			
			String idOrdine;
			
			do {
				idOrdine = "ORD" + UUID.randomUUID().toString().replace("-", "").substring(0, 9).toUpperCase();
			}while(ordineDao.doRetrieveByKey(idOrdine) != null);
			
			OrdineBean ordine = new OrdineBean();
			ordine.setId_ordine(idOrdine);
			ordine.setData_ordine(LocalDateTime.now());
			ordine.setStato(Stato.IN_PREPARAZIONE);
			ordine.setPrezzo_totale(carrello.getTotale());
			ordine.setId_utente(utente);
			ordine.setId_indirizzo(salvato);
			ordineDao.doSave(ordine);
			
			for(CarrelloItemBean item : carrello.getItems().values()) {
				ProdottoBean prodotto = prodottoDao.doRetrieveByKey(item.getId_prodotto());
				
				if(prodotto == null)
					continue;
				
				DettagliOrdineBean dettaglio = new DettagliOrdineBean();
				dettaglio.setId_ordine(ordine);
				dettaglio.setId_prodotto(prodotto);
				dettaglio.setQuantita(item.getQuantita());
				dettaglio.setPrezzo_unitario(item.getPrezzo());
				dettagliDao.doSave(dettaglio);
				
				int nuovoStock = prodotto.getStock() - item.getQuantita();
				
				if(nuovoStock < 0)
					nuovoStock = 0;
				
				prodottoDao.doUpdateStock(item.getId_prodotto(), nuovoStock);
			}
			
			carrello.svuota();
			response.sendRedirect(ctx + "/Carrello?id=" + idOrdine);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			request.setAttribute("errore", "Errore durante il pagamento. Riprova.");
			request.setAttribute("ctx", ctx);
			request.setAttribute("paginaAttiva", "");
			request.setAttribute("utente", utente);
			request.setAttribute("carrello", carrello);
			request.setAttribute("totaleArticoli", carrello.getNumeroArticoli());
			request.getRequestDispatcher("/WEB-INF/views/common/Checkout.jsp").forward(request, response);
		}
	}

}
