<%@page import="it.unisa.storage.model.CarrelloBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "it.unisa.storage.model.UtenteBean" %>
<%@ page import = "java.util.Map" %>

<%
	if(request.getAttribute("ctx") == null)
		request.setAttribute("ctx", request.getContextPath());
		
	String hctx = (String) request.getAttribute("ctx");
	
	if(hctx == null) 
		hctx = request.getContextPath();
		
	UtenteBean hutente = (UtenteBean) session.getAttribute("utente");
	
	int totale = 0;
	Object  totAtt = request.getAttribute("totaleArticoli");
	
	if(totAtt != null)
		totale = (int) totAtt;
	else
	{
		CarrelloBean carrello = (CarrelloBean) session.getAttribute("carrello");
		if(carrello != null)
			for(int i = 0; i < carrello.getNumeroArticoli(); i++)
				totale += i;
	}
	
	String pagina = (String) request.getAttribute("paginaAttiva");
	if(pagina == null)
		pagina = "";
	%>
	
<header class = "navbar">
	<div class = "navbar_container">
		<a href= "<%= hctx %>/Home" class = "navbar_logo">
			<span class = "logo-sport"> SPORT </span>
			<span class = "logo-shop">&nbsp;SHOP </span>
		</a>
		
		<nav class = "navbar_nav" aria-label = "Naigazione principale">
			<ul role = "list">
				<li> <a href = "<%= hctx %>/Home" class = "nav-link <%= "home".equals(pagina) ? "nav-link--active" : "" %>"> HOME </a></li>
				<li> <a href = "<%= hctx %>/Catalogo?genere=UOMO" class = "nav-link <%= "uomo".equals(pagina) ? "nav-link--active" : "" %>"> UOMO </a></li>
				<li> <a href = "<%= hctx %>/Catalogo?genere=DONNA" class = "nav-link <%= "donna".equals(pagina) ? "nav-link--active" : "" %>"> DONNA </a></li>
				<li> <a href = "<%= hctx %>/Catalogo?genere=BAMBINO" class = "nav-link <%= "bambino".equals(pagina) ? "nav-link--active" : "" %>"> BAMBINO </a></li>
				<li> <a href = "<%= hctx %>/Catalogo?genere=UNISEX" class = "nav-link <%= "unisex".equals(pagina) ? "nav-link--active" : "" %>"> UNISEX </a></li>
				<li> <a href = "<%= hctx %>/Catalogo?genere=UNISEX&Catalogo?categoria=ACCESSORIO" class = "nav-link <%= "accessori".equals(pagina) ? "nav-link--active" : "" %>"> ACCESSORI </a></li>
			</ul> 
		</nav>
		
		<div class = "navbar_actions">
		
			<form class = "navbar_search-form" action = "<%= hctx %>/Catalogo" method = "get" aria-label = "Ricerca">
				<input type = "search" name = "q" id = "searchInput" placeholder = "Cerca..." aria-label = "Cerca prodotti" maxlength = 100>
				<button type = "submit" class = "search-btn" aria-label = "Avvia ricerca"> 
					 <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.2" aria-hidden="true">
                        <circle cx="11" cy="11" r="8"/><path d="m21 21-4.35-4.35"/>
                    </svg>
					
				</button>
			</form>
			
			<%  if(hutente != null){ %>
				<div class = "navbar_user-menu" id = "userMenu">
					<button class = "user-trigger" id = "userTrigger" aria-haspopup = "true" aria-expanded = "false" onclick = "toggleMenu()">
						<span class = "user-avatar"> <%= hutente.getNome().charAt(0) %><%= hutente.getCognome().charAt(0)%></span>
						<span class = "user-name"> <%= hutente.getNome() %></span>
						<svg class="user-chevron" width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" aria-hidden="true">
                            <path d="m6 9 6 6 6-6"/>
                        </svg>
						
					</button>
				
					<div class = "user-dropdown" id = "userDropDown" role = "menu">
						<a href = "<%= hctx %>/Profilo" class = "user-dropdown_item" role = "menuitem">
						
							<svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" aria-hidden="true"><circle cx="12" cy="8" r="4"/>
								<path d="M4 20c0-4 3.6-7 8-7s8 3 8 7"/>
							</svg>
							
							Il mio profilo
						</a>
						<a href = "<%= hctx %>/Profilo?sezione=ordini" class = "user-dropdown_item" role = "menuitem">
						
							<svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" aria-hidden="true">
								<path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/>
									<polyline points="14 2 14 8 20 8"/>
										<line x1="16" y1="13" x2="8" y2="13"/>
										<line x1="16" y1="17" x2="8" y2="17"/>
							</svg>
							
							I miei ordini
						</a>
						<div class = "user-dropdown_sep"></div>
						<a href = "<%= hctx %>/Logout" class = "user-dropdown_item user-dropdown_item--logout" role = "menuitem">
						
							<svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" aria-hidden="true">
								<path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"/>
									<polyline points="16 17 21 12 16 7"/>
										<line x1="21" y1="12" x2="9" y2="12"/>			
							</svg>
							
							Esci
						</a>
					</div>
				</div>
			<% }else{ %>
				<a href = "<%= hctx %>/Login" class = "icon-btn navbar_login-btn" title = "Accedi" aria-label = "Accedi">
					<svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" aria-hidden="true">
                        <circle cx="12" cy="8" r="4"/><path d="M4 20c0-4 3.6-7 8-7s8 3 8 7"/>
                    </svg>
					
					<span class = "navbar_login-label"> Accedi </span>
				</a>
			<% } %>
			
			<a href = "<%= hctx %>/Carrello" class = "icon-btn navbar_cart" title = "Carrello" aria-label = "Carrello," <%= totale %> articoli">
				<svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" aria-hidden="true">
                    <path d="M6 2 3 6v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2V6l-3-4z"/>
                    <line x1="3" y1="6" x2="21" y2="6"/>
                    <path d="M16 10a4 4 0 0 1-8 0"/>
                </svg>
				
				<span class = "cart-badge<%= totale == 0 ? " cart-badge--hidden" : "" %>" id = "cartBadge" aria-live = "polite"> <%= totale %></span>
			</a>
		</div>
	</div>
	<script src="<%= hctx %>/scripts/header.js"></script>
</header>
	