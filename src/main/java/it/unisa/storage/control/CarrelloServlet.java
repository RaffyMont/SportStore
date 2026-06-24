package it.unisa.storage.control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

import it.unisa.storage.model.CarrelloBean;
import it.unisa.storage.model.UtenteBean;

/**
 * Servlet implementation class CarrelloServlet
 */
@WebServlet("/Carrello")
public class CarrelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CarrelloServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ctx = request.getContextPath();
		HttpSession session = request.getSession(false);
		CarrelloBean carrello = null;
		
		if (session != null)
            carrello = (CarrelloBean) session.getAttribute("carrello");

        if (carrello == null)
            carrello = new CarrelloBean();

        //Forse da correggere con il token in sessione
        UtenteBean utente = (session != null) ? (UtenteBean) session.getAttribute("utente") : null;
        
        request.setAttribute("ctx", ctx);
        request.setAttribute("paginaAttiva", "");
        request.setAttribute("utente", utente);
        request.setAttribute("carrello", carrello);
        request.setAttribute("totaleArticoli", carrello.getNumeroArticoli());
        request.getRequestDispatcher("/WEB-INF/views/common/Carrello.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ctx = request.getContextPath();
		boolean isAjax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
		HttpSession session = request.getSession(false);
		CarrelloBean carrello = (session != null) ? (CarrelloBean) session.getAttribute("carrello") : null;
		
		if (carrello == null) {
            if (isAjax) {
                response.setContentType("application/json");
                response.getWriter().print("{\"success\":false,\"message\":\"Carrello non trovato.\"}");
			} else {
				response.sendRedirect(ctx + "/Carrello");
			}
            return;
		}
		
		String action = request.getParameter("action");
	    String chiave = request.getParameter("chiave");

	    switch (action != null ? action : "") {

            case "rimuovi":
                if (chiave != null && !chiave.isBlank())
                    carrello.rimuovi(chiave);
                break;

            case "aggiorna":
                if (chiave != null && !chiave.isBlank()) {
                    try {
                        int qty = Integer.parseInt(request.getParameter("quantita"));
                        carrello.aggiorna(chiave, qty);
                    } catch (NumberFormatException ignored) {}
                }
                break;

            case "svuota":
                carrello.svuota();
                break;
        }

	    if (isAjax) {
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print("{\"success\":true,\"totaleArticoli\":" + carrello.getNumeroArticoli() + ",\"totalePrezzo\":" + String.format("%.2f", carrello.getTotale()).replace(",", ".") + "}");
        } else {
            response.sendRedirect(ctx + "/Carrello");
        }

	}
}
