<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.unisa.storage.model.UtenteBean" %>

<%
    String ctx    = (String)     request.getAttribute("ctx");
    if (ctx == null) 
    	ctx = request.getContextPath();
    UtenteBean utente = (UtenteBean) request.getAttribute("utente");
    String errore     = (String)     request.getAttribute("errore");

    String vModello    = (String) request.getAttribute("vModello");
    String vDescrizione= (String) request.getAttribute("vDescrizione");
    String vMarca      = (String) request.getAttribute("vMarca");
    String vPrezzo     = (String) request.getAttribute("vPrezzo");
    String vStock      = (String) request.getAttribute("vStock");
    String vCategoria  = (String) request.getAttribute("vCategoria");
    String vGenere     = (String) request.getAttribute("vGenere");
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Admin - Catalogo Completo - Inserisci Prodotto</title>
	<link rel = "stylesheet" href = "<%= ctx %>/styles/home.css">
	<link rel = "stylesheet" href = "<%= ctx %>/styles/admin2.css">
	<link rel = "stylesheet" href = "<%= ctx %>/styles/adminForm.css">
</head>
<body>
	<%@ include file = "../common/header.jsp" %>
	
	<main id = "main">
		<nav id = "breadcrumb">
			<ol>
				<li><a href = "<%= ctx %>/Home">Home</a></li>
				<li> / </li>
				<li><a href = "<%= ctx %>/admin/CatalogoCompleto">Catalogo</a></li>
				<li> / </li>
				<li aria-current = "page"> Nuovo prodotto </li>
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
				
				<div id = "separator"></div>
				
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
					<h1 id = "title"> Nuovo prodotto </h1>
					<a href = "<%= ctx %>/admin/CatalogoCompleto" class = "bottone_secondario"> &larr; Torna al catalogo completo </a>
				</div>
				
				<% if(errore != null){ %>
					<div id = "alert"><%= errore %></div>
				<% } %>
				
				<div id = "box">
					<form action = "<%= ctx %>/admin/InserisciProdotto" method = "post" id = "formInserisci" novalidate>
						<div id = "griglia">
							<div class = "gruppo" id = "g_modello">
								<label class = "label" for = "modello"> Modello <span class = "req">*</span></label>
								<input type = "text" id = "modello" name = "modello" class = "input" value = "<%= vModello != null ? vModello : ""  %>" maxlength = "100">
							</div>
							
							<div class = "gruppo" id = "g_marca">
								<label class = "label" for = "marca"> Marca <span class = "req">*</span></label>
								<input type = "text" id = "marca" name = "marca" class = "input" value = "<%= vMarca != null ? vMarca : ""  %>" maxlength = "100">
							</div>
							
							<div class = "gruppo" id = "g_prezzo">
								<label class = "label" for = "prezzo"> Prezzo <span class = "req">*</span></label>
								<input type = "number" id = "prezzo" name = "prezzo" class = "input" value = "<%= vPrezzo != null ? vPrezzo : ""  %>" min = "0" step = "0.01">
							</div>
							
							<div class = "gruppo" id = "g_stock">
								<label class = "label" for = "stock"> Stock <span class = "req">*</span></label>
								<input type = "number" id = "stock" name = "stock" class = "input" value = "<%= vStock != null ? vStock : ""  %>" min = "0">
							</div>
							
							<div class = "gruppo" id = "g_categoria">
								<label class = "label" for = "categoria"> Categoria <span class = "req">*</span></label>
								<select id = "categoria" name = "categoria" class = "select">
									<option value = ""> -- Seleziona -- </option>
									<option value="SCARPA"     <%= "SCARPA".equals(vCategoria)     ? "selected" : "" %>>Scarpa</option>
	                                <option value="VESTITO"    <%= "VESTITO".equals(vCategoria)    ? "selected" : "" %>>Vestito</option>
	                                <option value="ACCESSORIO" <%= "ACCESSORIO".equals(vCategoria) ? "selected" : "" %>>Accessorio</option>
								</select>
							</div>
							
							<div class = "gruppo" id = "g_genere">
								<label class = "label" for = "genere"> Genere <span class = "req">*</span></label>
								<select id = "genere" name = "genere" class = "select">
									<option value = ""> -- Seleziona -- </option>
									<option value="UOMO"    <%= "UOMO".equals(vGenere)    ? "selected" : "" %>>Uomo</option>
                                	<option value="DONNA"   <%= "DONNA".equals(vGenere)   ? "selected" : "" %>>Donna</option>
                                	<option value="BAMBINO" <%= "BAMBINO".equals(vGenere) ? "selected" : "" %>>Bambino</option>
                                	<option value="UNISEX"  <%= "UNISEX".equals(vGenere)  ? "selected" : "" %>>Unisex</option>
								</select>
							</div>
							
							<div class = "gruppo" id = "g_descrizione">
								<label class = "label" for = "descrizione"> Descrizione </label>
								<textarea id = "descrizione" name = "descrizione" class = "textarea" maxlength="500" rows = "4"><%= vDescrizione != null ? vDescrizione : "" %></textarea>
							</div>
							
							<div class = "azioni">
								<button type = "submit" id = "bottone_primario"> Salva Prodotto </button>
								<a href = "<%= ctx %>/admin/CatalogoCompleto" class = "bottone_secondario"> Annulla </a>
							</div>
							
						</div>
					</form>
				</div>
			</div>
		</div>
	</main>
	
	<%@ include file = "../common/footer.jsp" %>
	<script src = "<%= ctx %>/scripts/formInserisci.js"></script>
</body>
</html>