package it.unisa.storage.control;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import it.unisa.storage.model.UtenteBean;

/**
 * Servlet implementation class AuthFilter
 */
@WebFilter("/*")
public class AuthFilter extends HttpFilter {
	
	private static final long serialVersionUID = 1L;

	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		 	String path = request.getServletPath();

		    if (!path.startsWith("/admin/")) {
		        chain.doFilter(request, response);
		        return;
		    }

		    HttpSession session = request.getSession(false);
		    String token = (session != null) ? (String) session.getAttribute("token") : null;

		    if (token == null) {
		        response.sendRedirect(request.getContextPath() + "/Home");
		        return;
		    }
		    
		    UtenteBean utente = (UtenteBean) session.getAttribute("utente");

		    if (utente == null || utente.getRuolo() != UtenteBean.Ruolo.ADMIN) {
		        response.sendRedirect(request.getContextPath() + "/Home");
		        return;
		    }

		    chain.doFilter(request, response);

	}
}
