<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.unisa.storage.model.ProdottoBean" %>
<%@ page import="it.unisa.storage.model.UtenteBean" %>
<%@ page import="java.util.Collection" %>

<%
	String ctx = (String) request.getAttribute("ctx");
	if(ctx == null)
		ctx = request.getContextPath();
	
	UtenteBean utente = (UtenteBean) request.getAttribute("utente");
	String dbError = (String) request.getAttribute("dbError");
	String successo = (String) request.getAttribute("successo");
	
	@SuppressWarnings("unchecked")
	Collection<ProdottoBean> prodotti = (Collection<ProdottoBean>) request.getAttribute("prodotti");
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title> Admin - Gestione Catalogo </title>
	<link rel = "stylesheet" href = "<%= ctx %>/styles/home.css">
	<link rel = "stylesheet" href = "<%= ctx %>/styles/admin2.css">
</head>
<body>
	<%@ include file = "../common/header.jsp" %>
	
	<main id = "main">
		<nav id = "breadcrumb">
			<ol>
				<li><a href = "<%= ctx %>/Home"> Home </a></li>
				<li> / </li>
				<li><a href = "<%= ctx %>/Profilo"> Profilo </a></li>
				<li> / </li>
				<li aria-current = "page"> Gestione Catalogo </li>
			</ol>
		</nav>
		
		<div id = "container">
			<aside id = "sidebar">
				<p id = "sidebar_label"> Gestione
				<a href = "<%= ctx %>/admin/CatalogoCompleto" class = "sidebar_link">
					 <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
					 	<rect x="3" y="3" width="7" height="7"/>
					 	<rect x="14" y="3" width="7" height="7"/>
					 	<rect x="14" y="14" width="7" height="7"/>
					 	<rect x="3" y="14" width="7" height="7"/>
					 </svg>
                	Catalogo	
				</a>
				<a href = "<%= ctx %>/admin/GestioneOrdini" class = "sidebar_link">
					<svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
						<line x1="8" y1="6" x2="21" y2="6"/>
						<line x1="8" y1="12" x2="21" y2="12"/>
						<line x1="8" y1="18" x2="21" y2="18"/>
						<line x1="3" y1="6" x2="3.01" y2="6"/>
						<line x1="3" y1="12" x2="3.01" y2="12"/>
						<line x1="3" y1="18" x2="3.01" y2="18"/>
					</svg>
                	Tutti gli ordini
				</a>
				<div id = "sidebar_separator"></div>
				<a href = "<%= ctx %>/Profilo" class = "sidebar_link">
					<svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
						<circle cx="12" cy="8" r="4"/>
						<path d="M4 20c0-4 3.6-7 8-7s8 3 8 7"/>
					</svg>
               		 Il mio profilo
				</a>
			</aside>
			
			<div id = "contenuto">
				<div id = "header">
					<h1 id = "title"> Gestione Catalogo </h1>
					<a href = "<%= ctx %>/admin/InserisciProdotto" id = "bottone_principale">
						+ Nuovo Prodotto
					</a>
				</div>
				
				<% if(successo != null){ %>
					<div id = "alert_successo"><%= successo %></div>
				<% } %>
				
				<% if(dbError != null){ %>
					<div id = "alert_errore"><%= dbError %></div>
				<% } %>
				
				<div id = "box">
					<% if(prodotti != null && !prodotti.isEmpty()){ %>
						<table id = "tabella">
							<thead>
								<tr>
									<th> ID </th>
									<th> Modello </th>
									<th> Marca </th>
									<th> Categoria </th>
									<th> Genere </th>
									<th> Prezzo </th>
									<th> Stock </th>
									<th> Stato </th>
									<th> Azioni </th>
								</tr>
							</thead>
							<tbody>
								<% for (ProdottoBean p : prodotti) { %>
		                        	<tr class="<%= !p.isAttivo() ? "tr_disattivo" : "" %>">
		                            <td class="td_id"><%= p.getId_prodotto() %></td>
		                            <td class="td_modello"><%= p.getModello() %></td>
		                            <td><%= p.getMarca() %></td>
		                            <td><%= p.getCategoria().name() %></td>
		                            <td><%= p.getGenere().name() %></td>
		                            <td class="td_prezzo"><%= String.format("%.2f", p.getPrezzo()) %>&euro; </td>
									<td><%= p.getStock() %></td>
									<td>
										<% if(p.isAttivo()){ %>
											<span class = "stato_attivo"> Attivo </span>
										<% } else { %>
											<span clss = "stato_inattivo"> Disattivo </span>
										<% } %>
									</td>
									<td class = "td_azioni">
										<a href = "<%= ctx %>/admin/ModificaProdotto?id=<%= p.getId_prodotto() %>" class = "modifica"> Modifica </a>
										<% if(p.isAttivo()){ %>
											<form method = "post" action = "<%= ctx %>/admin/CatalogoCompleto">
												<input type = "hidden" name = "action" value = "disattiva">
												<input type = "hidden" name = "id" value = "<%= p.getId_prodotto() %>">
												<button type = "submit" class = "elimina"> Rimuovi </button>
											</form>
										<% } %>
										<% if(!p.isAttivo()){ %>
											<form method = "post" action = "<%= ctx %>/admin/CatalogoCompleto">
												<input type = "hidden" name = "action" value = "riattiva">
												<input type = "hidden" name = "id" value = "<%= p.getId_prodotto() %>">
												<button type = "submit" class = "reinserisci"> Riattiva </button>
											</form>
										<% } %>
									</td>
								</tr>
							<% } %>
							</tbody>
						</table>
					<% } else if(dbError == null){ %>
						<p id = "vuoto"> Nessun prodotto nel catalogo. 
					<% } %>
				</div>
			</div>
		</div>
	</main>
	
	<%@ include file = "../common/footer.jsp" %>
</body>
</html>