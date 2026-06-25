<%@page import="it.unisa.storage.model.IndirizzoBean"%>
<%@page import="it.unisa.storage.model.CarrelloItemBean"%>
<%@page import="java.util.Map"%>
<%@page import="it.unisa.storage.model.CarrelloBean"%>
<%@page import="it.unisa.storage.model.UtenteBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	String ctx = (String) request.getAttribute("ctx");

	if(ctx == null)
		ctx = request.getContextPath();
	
	request.setAttribute("ctx", ctx);
	
	UtenteBean utente = (UtenteBean) request.getAttribute("utente");
	CarrelloBean carrello2 = (CarrelloBean) request.getAttribute("carrello");
	String errore = (String) request.getAttribute("errore");
	Map<String, CarrelloItemBean> items = carrello2 != null ? carrello2.getItems() : null;
	IndirizzoBean indirizzo = (utente != null) ? utente.getIndirizzo() : null;
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Checkout</title>
	<link rel = "stylesheet" href = "<%= ctx %>/styles/home.css">
</head>
<body>
	<%@ include file = "header.jsp" %>
	
	<main id = "main">
		<nav id = "breadcrumb">
			<ol>
				<li><a href = "<%= ctx %>/Home"> Home </a></li>
				<li>/</li>
	            <li><a href="<%= ctx %>/Carrello">Carrello</a></li>
	            <li>/</li>
	            <li aria-current="page">Checkout</li>
			</ol>
		</nav>
		
		<div id = "container">
			<h1 id = "titolo">Checkout</h1>
			
			<% if(errore != null){ %>
				<div id = "alert"> <%= errore %></div>
			<% } %>
			
			<form id = "checkout" action = "<%= ctx %>/Checkout" method = "post">
				<div id = "checkout_layout">
					<div id = "form_sx">
						<div class = "box">
							<h2 class = "box_titolo">
								<svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
									<path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z"/>
									<circle cx="12" cy="10" r="3"/>
								</svg>
                       			 Indirizzo di spedizione
							</h2>
							
							<div class = "form_grid">
								<div class = "fg" id = "g_nome">
									<label class = "flabel" for = "nome"> Nome <span class = "req">*</span></label>
									<input type = "text" id = "nome" name = "nome" class = "finput" value = "" maxlength="256">
								</div>
								
								<div class = "fg" id = "g_cognome">
									<label class = "flabel" for = "cognome"> Nome <span class = "req">*</span></label>
									<input type = "text" id = "cognome" name = "cognome" class = "finput" value = "" maxlength="256">
								</div>
								
								<div class = "fg" id = "g_via">
									<label class = "flabel" for = "via"> Via/Piazza <span class = "req">*</span></label>
									<input type = "text" id = "via" name = "via" class = "finput" value = "" maxlength="256">
								</div>
								
								<div class = "fg" id = "g_civico">
									<label class = "flabel" for = "civico"> Civico <span class = "req">*</span></label>
									<input type = "text" id = "civico" name = "civico" class = "finput" value = "" maxlength="10">
								</div>
								
								<div class = "fg" id = "g_citta">
									<label class = "flabel" for = "citta"> Citta <span class = "req">*</span></label>
									<input type = "text" id = "citta" name = "citta" class = "finput" value = "" maxlength="256">
								</div>
								
								<div class = "fg" id = "g_cap">
									<label class = "flabel" for = "cap"> CAP <span class = "req">*</span></label>
									<input type = "text" id = "cap" name = "cap" class = "finput" value = "" maxlength="5" placeholder="80100">
								</div>
								
								<div class = "fg" id = "g_provincia">
									<label class = "flabel" for = "provincia"> Provincia <span class = "req">*</span></label>
									<input type = "text" id = "provincia" name = "provincia" class = "finput" value = "" maxlength="50">
								</div>
								
								<div class = "fg" id = "g_stato">
									<label class = "flabel" for = "stato"> Stato <span class = "req">*</span></label>
									<input type = "text" id = "stato" name = "stato" class = "finput" value = "" maxlength="64">
								</div>
							</div>
						</div>
						
						<div class = "box">
							<h2 class = "box_titolo">
								<svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
									<rect x="1" y="4" width="22" height="16" rx="2"/>
									<line x1="1" y1="10" x2="23" y2="10"/>
								</svg>
                        		Dati di pagamento
							</h2>
							
							<div class = "form_grid">
								<div class = "fg" id = "g_numeroCarta">
									<label class = "flabel" for = "numeroCarta"> Numero carta <span class = "req">*</span></label>
									<input type = "text" id = "numeroCarta" name = "numeroCarta" class = "finput" value = "" maxlength="16" placeholder="1234 XXXX XXXX XXXX" inputmode = "numeric">
								</div>
								
								<div class = "fg" id = "g_titolare">
									<label class = "flabel" for = "titolare"> Titolare carta <span class = "req">*</span></label>
									<input type = "text" id = "titolare" name = "titolare" class = "finput" value = "" maxlength="256">
								</div>
								
								<div class = "fg" id = "g_scadenza">
									<label class = "flabel" for = "scadenza"> Scadenza <span class = "req">*</span></label>
									<input type = "text" id = "scadenza" name = "scadenza" class = "finput" value = "" maxlength="5" placeholder="MM/AA">
								</div>
								
								<div class = "fg" id = "g_cvv">
									<label class = "flabel" for = "cvv"> CVV <span class = "req">*</span></label>
									<input type = "password" id = "cvv" name = "cvv" class = "finput" value = "" maxlength="3" placeholder="•••" inputmode = "numeric">
								</div>
							</div>
						</div>
					</div>
					
					<div id = "riepilogo">
						<div class = "box">
							<h2 class = "box_titolo">Riepilogo ordine</h2>
							
							<div id="riepilogo_items">
							    <% if(items != null){ %>
							        <% for(CarrelloItemBean item : items.values()){ %>
							            <div class="riepilogo_item">
							                <div class="riepilogo_info">
							                    <span class="item_nome"><%= item.getMarca() %> <%= item.getModello() %></span>
							                    <% if(item.getTaglia() != null && !item.getTaglia().isBlank()){ %>
							                        <span class="item_var"> Taglia: <%= item.getTaglia() %></span>
							                    <% } %>
							
							                    <% if(item.getColore() != null && !item.getColore().isBlank()){ %>
							                        <span class="item_var"> Colore: <%= item.getColore() %></span>
							                    <% } %>
							
							                    <span class="item_qty"><%= item.getQuantita() %></span>
							                </div>
							
							                <span class="item_prezzo"><%= String.format("%.2f", item.getTotale()) %>&euro;</span>
							            </div>
							        <% } %>  
							    <% } %>
							</div>
							<div class = "separator"></div>
							
							<div class = "riepilogo_riga">
								<span>Subtotale</span>
								<span><%= carrello2 != null ? String.format("%.2f", carrello2.getTotale()) : "0.00"%>&euro;</span>
							</div>
							
							<div class = "riepilogo_riga">
								<span>Spedizione</span>
								<span class = "spedizione_gratis"><%= carrello2 != null && carrello2.getTotale() >= 50 ? "Gratuita" : "4.99€"%></span>
							</div>
							
							<div class = "separator"></div>
							
							<div class = "riepilogo_riga">
								<span>Totale</span>
								<%
								    double totaleFinale2 = 0.00;
								    if(carrello2 != null){
								        totaleFinale2 = carrello2.getTotale() >= 50 ? carrello2.getTotale() : carrello2.getTotale() + 4.99;
								    }
								%>
								<span><%= carrello2 != null ? String.format("%.2f", totaleFinale2) : "0.00" %></span>
							</div>
							
							<button type = "submit" id = "acquista">
								<svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5">
									<path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"/>
								</svg>
                        		ACQUISTA ORA
                    		</button>
							
							<a href="<%= ctx %>/Carrello" id="torna_carrello">&larr; Torna al carrello</a>
					
						</div>
					</div>
				</div>
			</form>
		</div>
	</main>
	
	<%@ include file = "footer.jsp" %>
</body>
</html>