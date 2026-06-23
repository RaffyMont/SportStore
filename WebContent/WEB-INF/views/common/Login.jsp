<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "it.unisa.storage.model.UtenteBean" %>

<% 
	String errore = (String) request.getAttribute("errore");
	String emailInserita = (String) request.getAttribute("emailInserita");
	String redirectUrl = (String) request.getAttribute("redirectUrl");
	String ctx = request.getContextPath();
	request.setAttribute(ctx, "ctx");
	request.setAttribute("paginaAttiva", "login");
	
	UtenteBean utenteSessione = (UtenteBean) session.getAttribute("utente");
	if(utenteSessione != null)
	{
		response.sendRedirect(ctx + "/Home");
		return;
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Login</title>
	<link rel="stylesheet" href="<%= ctx %>/styles/login.css">
	<link rel="stylesheet" href="<%= ctx %>/styles/home.css">
</head>
<body class = "login-page">
	<%@ include file = "header.jsp" %>
	
	<main class = "login-main" id = "main-content">
		<nav class = "login-bc">
			<ol>
				<li> <a href = "<%= ctx %>/Home"> Home </a></li>
				<li aria-current = "page"> Accedi </li>
			</ol>
		</nav>
		
		<div class = "login-card">
			<div class = "login-card_header">
				<div class = "login_card_icon" aria-hidden = "true">
					<svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8">
                    	<circle cx="12" cy="8" r="4"/><path d="M4 20c0-4 3.6-7 8-7s8 3 8 7"/>
                	</svg>
				</div>
				
				<h1 class = "login-card_title"> Bentornato </h1>
				<p class = "login-card_sub"> Accedi al tuo account SportShop
			</div>
			
			<% if (errore != null) { %>
			<div class = "login-alert" role = "alert" id = "serverError">
				<svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" aria-hidden="true">
	                <circle cx="12" cy="12" r="10"/>
	                <line x1="12" y1="8" x2="12" y2="12"/>
	                <line x1="12" y1="16" x2="12.01" y2="16"/>
            	</svg>
            	<%= errore %>	
			</div>
			<% } %>
			
			<form class = "login-form" action = "<%= ctx %>/Login" method = "post" id = "loginForm" novalidate>
			
				<% if(redirectUrl != null && !redirectUrl.isBlank()){ %>
					<input type = "hidden" name = "redirectUrl" value = "<%= redirectUrl %>">
				<% } %>
			
			<div class = "form-group" id = "group-email">
				<label for = "email" class = "form-label"> Email </label>
				<div class = "input-wrap">
					<span class = "input-icon" aria-hidden = "true">
                        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <rect x="2" y="4" width="20" height="16" rx="2"/>
                            <path d="m2 7 10 7 10-7"/>
                        </svg>
					</span>
					
					<input type = "email" id = "email" name = "email" class = "form-input" placeholder="la-tua@email.it" value = "<%= emailInserita != null ? emailInserita : "" %>" autocomplete="email" maxlength="100">
				</div>
				
				<span class = "form-error" id = "err-email" role = "alert" aria-live = "polite"></span>
			</div>
			
			<div class = "form-group" id = "group-password">
				<label for = "password" class = "form-label"> Password </label>
				<div class = "input-wrap">
					<span class = "input-icon" aria-hidden = "true">
						<svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <rect x="3" y="11" width="18" height="11" rx="2"/>
                            <path d="M7 11V7a5 5 0 0 1 10 0v4"/>
                        </svg>
					</span>
					
					<input type = "password" id = "password" name = "password" class = "form-input" placeholder = "" maxlength = "256">
					
					<button type = "button" class = "toggle-pwd" id = "togglePwd" aria-label = "Mostra password">
						<svg id="eyeIcon" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" aria-hidden="true">
                            <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/>
                            <circle cx="12" cy="12" r="3"/>
                        </svg>
					</button>
				</div>
				
				<span class = "form-error" id = "err-password" role = "alert" aria-live = "polite"></span>
				
			</div>
			
			<button type = "submit" class = "btn-login" id = "btnLogin">
				<span class = "btn-login_text"> ACCEDI </span>
				<span class = "btn-login_spinner" aria-hidden = "true"></span>
			</button>
			
			</form>
			
			<div class = "login-divider"> <span> oppure </span></div>
			
			<div class = "login-register"> 
				<p> Non hai ancora un account?
				<a href = "<%= ctx %>/Registrazione" class = "btn-register">
					Clicca qui per registrarti
					<svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" aria-hidden="true">
                    	<path d="M5 12h14M12 5l7 7-7 7"/>
                	</svg>
				</a>
			</div>
		</div>
	</main>
	<%@ include file = "footer.jsp" %>
	<script src = "<%= ctx %>/scripts/login.js"></script>
</body>
</html>