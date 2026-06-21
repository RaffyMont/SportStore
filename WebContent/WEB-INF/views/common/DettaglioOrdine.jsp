<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.unisa.storage.model.OrdineBean" %>
<%@ page import="it.unisa.storage.model.DettagliOrdineBean" %>
<%@ page import="it.unisa.storage.model.UtenteBean" %>
<%@ page import="it.unisa.storage.model.IndirizzoBean" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<%
	String ctx = (String) request.getAttribute("ctx");
	if(ctx == null)
		ctx = request.getContextPath();
	
	UtenteBean utente = (UtenteBean) request.getAttribute("utente");
	OrdineBean ordine = (OrdineBean) request.getAttribute("ordine");
	String errore = (String) request.getAttribute("errore");
	int totaleArticoli = request.getAttribute("totaleArticoli") != null ? (int) request.getAttribute("totaleArticoli") : 0;
	
	@SuppressWarnings("unchecked")
	Collection<DettagliOrdineBean> dettagli = (Collection<DettagliOrdineBean>) request.getAttribute("dettagli");
	
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Dettaglio Ordine</title>
	<link rel = "stylesheet" href = "<%= ctx %>/styles/home.css">
	<link rel = "stylesheet" href = "<%= ctx %>/styles/dettaglioOrdine.css">
</head>
<body>
	<%@ include file = "header.jsp" %>
	
	<main id = "main">
		<nav id = breadcrumb>
			<ol>
				<li> <a href = "<%= ctx %>/Home"> Home </a></li>
				<li> / </li>
				<li> <a href = "<%= ctx %>/Profilo?sezione=ordini"> I miei ordini </a></li>
				<li> / </li>
				<% if(ordine != null){ %>
					<li aria-current = "page"> Ordine #<%= ordine.getId_ordine() %></li>
				<% } else { %>
					<li aria-current = "page"> Dettaglio ordine </li>
				<% } %>
			</ol>
		</nav>
		
		<% if(errore != null){ %>
			<div class = "alert" role = "alert"> <%= errore %></div>
		<% } %>
		
		<% if(ordine != null){ %>
			<div id = "contenitore">
				<div id = "header">
					<div id = "header_left">
						<h1 id = "titolo"> Ordine #<%= ordine.getId_ordine() %></h1>
						<p id = "data"> Effettuato il <%= ordine.getData_ordine().format(formatter) %>
					</div>
					
					<div id = "header_right">
						<% 
							String stato = ordine.getStato().name();
							String statoCss = "";
							String statoLabel = "";
							
							switch(stato)
							{
								case "CONSEGNATO": 
									statoCss = "consegnato";
									statoLabel = "Consegnato";
									break;
									
								case "IN_SPEDIZIONE": 
									statoCss = "spedizione";
									statoLabel = "Spedizione";
									break;
									
								case "ANNULLATO": 
									statoCss = "annullato";
									statoLabel = "Annullato";
									break;
									
								default:
									statoCss = "";
									statoLabel = "";
							}
						%>
						<span id = "badge_<%= statoCss %>"> <%= statoLabel %></span>
					</div>
				</div>
				
				<div class = "box">
					<h2 class = "box_titolo"> Prodotti </h2>
					
					<% if(dettagli != null && !dettagli.isEmpty()){ %>
						<table id = "tabella_ordine">
							<thead>
								<tr>
									<th> Prodotto </th>
									<th> Marca </th>
									<th> Prezzo unitario </th>
									<th> Quantità </th>
									<th> Subtotale </th>
								</tr>
							</thead>
							<tbody>
								<% for(DettagliOrdineBean d : dettagli){ %>
									<tr>
										<td class = "td_prodotto">
											<span class = "prodotto_nome"> <%= d.getId_prodotto().getModello() %></span>
											<span class = "prodotto_categoria"> <%= d.getId_prodotto().getCategoria().name() %></span>
										</td>
										<td><%= d.getId_prodotto().getMarca() %> </td>
										<td><%= String.format("%.2f", d.getPrezzo_unitario()) %>&euro;</td>
										<td><%= d.getQuantita() %></td>
										<td class = "prodotto_subtotale"><%= String.format("%.2f", d.getPrezzo_unitario() * d.getQuantita()) %>&euro;</td>
									</tr>
								<% } %>
							</tbody>
						</table>
					<% } else { %>
						<p id = "vuoto"> Nessuno prodotto trovato per quest'ordine.
					<% } %>
					
					<div id = "totale">
						<span> Totale ordine </span>
						<span id = "totale_prezzo"><%= String.format("%.2f", ordine.getPrezzo_totale()) %></span>
					</div>
				</div>
				
				<%  IndirizzoBean indirizzo = ordine.getId_indirizzo();
					if (indirizzo != null){
				%>
				
					<div class = "box">
						<h2 class = "box_titolo"> Indirizzo di spedizione </h2>
						<div id = "indirizzo">
							<p><%= indirizzo.getVia()%>, <%= indirizzo.getCivico() %>
							<p><%= indirizzo.getCAP() %> <%= indirizzo.getCitta() %> (<%= indirizzo.getProvincia() %>)
							<p><%= indirizzo.getStato() %>
						</div>
					</div>
				<% } %>
				
				<div id = "azioni">
					<a href = "<%= ctx %>/Profilo?sezione=ordini" id = "azione_secondaria">&larr; Torna ai miei ordini </a>
					<a href = "<%= ctx %>/Catalogo" id = "azione_primaria"> Continua con lo shopping </a>
				</div>
			</div>
			
		<% } else if(errore == null){ %>
			<div class = "alert"> Ordine non trovato. </div>
		<% } %>
	</main>
	
	<%@ include file = "footer.jsp" %>
</body>
</html>