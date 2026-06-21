package it.unisa.storage.control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import it.unisa.storage.dao.UtenteDao;
import it.unisa.storage.dao.UtenteDaoImpl;
import it.unisa.storage.model.UtenteBean;
import it.unisa.storage.model.UtenteBean.Ruolo;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession sessione = request.getSession(false);
		String token = (sessione != null) ? (String) sessione.getAttribute("token") : null;
	    if (token != null) {
	         response.sendRedirect(request.getContextPath() + "/Home");
	         return;
	    }
		
		String redirectUrl = request.getParameter("redicter");
		if(redirectUrl != null && !redirectUrl.isBlank())
			request.setAttribute("redirectUrl", redirectUrl);
		
		request.getRequestDispatcher("/WEB-INF/views/common/Login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String redirect = request.getParameter("redirectUrl");
		
		if(email == null || email.isBlank() || password == null || password.isBlank())
		{
			request.setAttribute("errore", "Compila tutti i campi");
			request.getRequestDispatcher("/WEB-INF/views/common/Login.jsp").forward(request, response);
		
			return;
		}
		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		UtenteDao utenteDao = new UtenteDaoImpl(ds);
		
		try
		{
			UtenteBean utente = utenteDao.doAuthenticate(email.trim(), password);		
			
			if(utente == null)
			{
				request.setAttribute("errore", "Email o password non corretti");
				request.setAttribute("emailInserita", email);
				request.setAttribute("paginaAttiva", "login");
				request.getRequestDispatcher("/WEB-INF/views/common/Login.jsp").forward(request, response);
				return;
			}
			
			HttpSession sessione = request.getSession(true);
			sessione.setAttribute("utente", utente);
			sessione.setAttribute("token", utente.getId_utente());
			sessione.setAttribute("role", utente.getRuolo().name().toLowerCase());
			
            if (redirect != null && !redirect.isBlank() && redirect.startsWith(request.getContextPath()))
                response.sendRedirect(redirect);
            else 
                response.sendRedirect(request.getContextPath() + "/Home");
            

          } catch (SQLException e) {
	            log("LoginServlet: errore DB - " + e.getMessage());
	            request.setAttribute("errore", "Errore di sistema. Riprova più tardi.");
	            request.setAttribute("paginaAttiva", "login");
	            request.getRequestDispatcher("/WEB-INF/views/common/Login.jsp").forward(request, response);
         }

	}

}
