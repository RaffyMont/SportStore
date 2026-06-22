<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.unisa.storage.model.OrdineBean" %>
<%@ page import="it.unisa.storage.model.UtenteBean" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<%
	String ctx = (String) request.getAttribute("ctx");
	if(ctx == null)
		ctx = request.getContextPath();
	UtenteBean utente = (UtenteBean) request.getAttribute("utente");
	String dbError       = (String) request.getAttribute("dbError");
	String dataInizio    = (String) request.getAttribute("dataInizio");
	String dataFine      = (String) request.getAttribute("dataFine");
	String idClienteSel  = (String) request.getAttribute("idClienteSel");
	
    @SuppressWarnings("unchecked")
    Collection<OrdineBean> ordini = (Collection<OrdineBean>) request.getAttribute("ordini");

    @SuppressWarnings("unchecked")
    Collection<UtenteBean> clienti = (Collection<UtenteBean>) request.getAttribute("clienti");

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    int totOrdini = ordini != null ? ordini.size() : 0;
    double totRicavi = 0;
    if (ordini != null)
        for (OrdineBean o : ordini)
            if (o.getStato() != OrdineBean.Stato.ANNULLATO)
                totRicavi += o.getPrezzo_totale();
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Area Admin - Gestione Ordini </title>
	<link rel = "stylesheet" href = "<%= ctx %>/styles/home.css">
	<link rel = "stylesheet" href = "<%= ctx %>/styles/admin.css">
</head>
<body>
	<%@ include file = "../common/header.jsp" %>
	
	<main id = "main">
		<nav id = "breadcrump">
			<ol>
				<li> <a href = "<%= ctx %>/Home"> Home </a>
				<li> / </li>
				<li> <a href = "<%= ctx %>/Profilo"> Profilo </a> </li>
				<li> / </li>
				<li aria-current = "page"> Gestione ordini </li>
			</ol>
		</nav>
		
		<div id = "container">
			<aside id = "sidebar">
				<p id = "sidebar_label"> Gestione
				<a href = "<%= ctx %>/admin/Catalogo" class = "sidebar_link">
					 <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect x="3" y="3" width="7" height="7"/>
					 	<rect x="14" y="3" width="7" height="7"/>
					 	<rect x="14" y="14" width="7" height="7"/>
					 	<rect x="3" y="14" width="7" height="7"/>
					 </svg>
					Catalogo
				</a>
				
				<a href = "<%= ctx %>/admin/Ordini" class = "sidebar_link">
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
					<h1 id = "title"> Tutti gli ordini </h1>
				</div>
				
				<div id = "filtri">
					<form action = "<%= ctx %>/admin/GestioneOrdini" method = "get" id = "filtri_form">
						<div class = "filtro_gruppo">
							<label class = "filtro_label" for = "dataInizio"> Dal </label>
							<input type = "date" id = "dataInizio" name = "dataInizio" class = "filtro_input" value = "<%= dataInizio != null ? dataInizio : ""%>">
						</div>
						
						<div class = "filtro_gruppo">
							<label class = "filtro_label" for = "dataFine"> Al </label>
							<input type = "date" id = "dataFine" name = "dataFine" class = "filtro_input" value = "<%= dataFine != null ? dataFine : ""%>">
						</div>
						
						<div class = "filtro_gruppo">
							<label class = "filtro_label" for = "idCliente"> Cliente </label>
							<select id = "idCliente" name = "idCliente" class = "filtro_select">
								<option value = ""> Tutti i clienti </option>
								<% if(clienti != null){ 
									for(UtenteBean c : clienti)
									{
										if(c.getRuolo() == UtenteBean.Ruolo.COMMON){ %>
											<option value = "<%= c.getId_utente() %>" <%= c.getId_utente().equals(idClienteSel) ?  "selected" : "" %>>
												<%= c.getNome() %> <%= c.getCognome() %> - <%= c.getEmail() %>
											</option>
											
										<% }
									}
								} %>
							</select>
						</div>
						<button type = "submit" id = "bottone_principale"> Filtra </button>
						<a href = "<%= ctx %>/admin/GestioneOrdini" id = "bottone_secondario"> Reset </a>
					</form>
				</div>
				
				<div id = "stats">
					<div class = "stat_card">
						<span class = "stat_valore"> <%= totOrdini %></span>
						<span class = "stat_label"> Ordini trovati </span>
					</div>
					
					<div class = "stat_card">
						<span class = "stat_valore"> <%= String.format("%.2f", totRicavi) %> </span>
						<span class = "stat_label"> Ricavi (esclusi annullati) </span>
					</div>
				</div>
				
				<% if(dbError != null){ %>
					<div id = "alert"> <%= dbError %></div>
				<% } %>
				
				<div id = "box">
				<% if(ordini != null && !ordini.isEmpty()){ %>
					<table id = "tabella">
						<thead>
							<tr>
								<th> Ordine </th>
								<th> Data </th>
								<th> Cliente </th>
								<th> Stato </th>
								<th> Totale </th>
								<th></th>
							</tr>
						</thead>
						
						<tbody>
							<% for(OrdineBean o : ordini){
									String stato = o.getStato().name();
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
										
										case "IN_PREPARAZIONE":
											statoCss = "preparazione";
											statoLabel = "Preparazione";
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
							<tr>
								<td class = "td_id">#<%= o.getId_ordine() %></td>
								<td><%= o.getData_ordine().format(formatter) %></td>
								<td class = "td_nominativo"> <%= o.getId_utente().getNome() %> <%= o.getId_utente().getCognome() %><br>
									<span class = "td_email"><%= o.getId_utente().getEmail() %></span>
								</td>
								<td>
									<span class = "stato_<%= statoCss %>"> <%= statoLabel %></span>
								</td>
								<td class = "td_prezzo"> <%= String.format("%.2f", o.getPrezzo_totale()) %></td>
								<td>
									<a href = "<%= ctx %>/DettaglioOrdine?id=<%= o.getId_ordine() %>" class = "azione"> Vedi </a>
								</td>
							</tr>
							<% } %>
						</tbody>
					</table>
					<%  } else if(dbError == null){ %>
						<p id = "vuoto"> Nessun ordine trovato.
					<% } %>
				</div>
			</div>
		</div>
	</main>
	
	<%@ include file = "../common/footer.jsp" %>
</body>
</html>