package it.unisa.storage.control;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import it.unisa.storage.dao.UtenteDao;
import it.unisa.storage.dao.UtenteDaoImpl;
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
		    
		    DataSource ds = (DataSource) request.getServletContext().getAttribute("DataSource");
		    UtenteDao utenteDao = new UtenteDaoImpl(ds);

		    try {
		        UtenteBean utente = utenteDao.doRetrieveByKey(token);

		        if (utente == null || utente.getRuolo() != UtenteBean.Ruolo.ADMIN) {
		            response.sendRedirect(request.getContextPath() + "/Home");
		            return;
		        }

		        chain.doFilter(request, response);

		    } catch (SQLException e) {
		        response.sendRedirect(request.getContextPath() + "/Home");
		    }

	}
}
