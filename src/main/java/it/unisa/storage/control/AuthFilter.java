package it.unisa.storage.control;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet implementation class AuthFilter
 */
@WebServlet("/*")
public class AuthFilter extends HttpServlet {
	
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		String path = request.getServletPath();
		
		if(!path.startsWith("/admin/") && !path.startsWith("/common/"))
		{
			chain.doFilter(request, response);
			return;
		}
		
		HttpSession session = request.getSession(false);
		String role = (session != null) ? (String) session.getAttribute("role") : null;
		boolean autorizzato = false;
		
		if(role != null)
		{
			if(path.startsWith("/admin/"))
				autorizzato = role.equals("admin");
			else if(path.startsWith("/common/"))
				autorizzato = role.equals("admin") || role.equals("user");
		}
		
		if(autorizzato)
			chain.doFilter(request, response);
		else
			response.sendRedirect(request.getContextPath() + "/index");
			

	}
}
