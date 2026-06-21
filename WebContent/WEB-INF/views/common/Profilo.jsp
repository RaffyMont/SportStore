<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.unisa.storage.model.UtenteBean" %>
<%@ page import="it.unisa.storage.model.IndirizzoBean" %>
<%@ page import="it.unisa.storage.model.OrdineBean" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<%
	String ctx = (String) request.getAttribute("ctx");
	if(ctx == null)
		ctx = request.getContextPath();
	
	UtenteBean utente = (UtenteBean) request.getAttribute("utente");
	String sezione = (String) request.getAttribute("sezione");
	String dbError = (String) request.getAttribute("dbError");
	
	@SuppressWarnings("unchecked")
	Collection<OrdineBean> ordini = (Collection<OrdineBean>) request.getAttribute("ordini");
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	IndirizzoBean indirizzo = null;
	
	if(utente != null)
		indirizzo = utente.getIndirizzo();
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Profilo</title>
	<link rel = "stylesheet" href = "<%= ctx %>/styles/home.css">
	<link rel = "stylesheet" href = "<%= ctx %>/styles/profilo.css">
	
</head>
<body>
	<%@ include file = "header.jsp" %>
	
	<main id = "main">
		<nav id = "breadcrump">
			<ol>
				<li> <a href = "<%= ctx %>/Home"> Home </a> </li>
				<li> / </li>
				<li aria-current = "page"> Il mio profilo
			</ol>
		</nav>
		
		<div id = "profilo_container">
			<aside id = "sidebar_avatar">
				<div id = "avatar">
					<div id = "cerchio">
						<%= utente.getNome().charAt(0) %><%= utente.getCognome().charAt(0) %>
					</div>
					<p id = "nominativo"> <%= utente.getNome() %><%= utente.getCognome() %>
					<p id = "email"> <%= utente.getEmail() %>
				</div>
				
				<nav id = "sidebar_nav">
					<span class = "sidebar_label"> Il mio account </span>
					<a href = "<%= ctx %>/Profilo" class = "sidebar_link <%= (sezione == null || "dati".equals(sezione)) ? "sidebar_link_active" : "" %>"> 
						<svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
							<circle cx="12" cy="8" r="4"/><path d="M4 20c0-4 3.6-7 8-7s8 3 8 7"/>
						</svg>
						I miei dati
					</a>
					
					<a href = "<%= ctx %>/Profilo?sezione=ordini" class = "sidebar_link <%= (sezione == null || "ordini".equals(sezione)) ? "sidebar_link_active" : "" %>">
						<svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
							<path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/>
							<polyline points="14 2 14 8 20 8"/>
							<line x1="16" y1="13" x2="8" y2="13"/>
							<line x1="16" y1="17" x2="8" y2="17"/>
						</svg>
						I miei ordini
					</a>
					
					<% if(utente.getRuolo() == UtenteBean.Ruolo.ADMIN){ %>
						<div class = "separator"></div>
						<span class = "sidebar_label"> Gestione </span>
						<a href = "<%= ctx %>/admin/CatalogoCompleto" class = "sidebar_link">
							<svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
								<rect x="3" y="3" width="7" height="7"/>
								<rect x="14" y="3" width="7" height="7"/>
								<rect x="14" y="14" width="7" height="7"/>
								<rect x="3" y="14" width="7" height="7"/>
							</svg>
							Catalogo Sito
						</a>
						
						<a href = "<%= ctx %>/admin/GestioneOrdini" class = "sidebar_link"> 
							<svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
								<line x1="8" y1="6" x2="21" y2="6"/>
								<line x1="8" y1="12" x2="21" y2="12"/>
								<line x1="8" y1="18" x2="21" y2="18"/>
								<line x1="3" y1="6" x2="3.01" y2="6"/>
								<line x1="3" y1="12" x2="3.01" y2="12"/>
								<line x1="3" y1="18" x2="3.01" y2="18"/>
							</svg>
							Tutti gli ordini
						</a>
						<% } %>
						
						<div class = "separator"></div>
						<a href = "<%= ctx %>/Logout" class = "sidebar_link">
							<svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
								<path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"/>
								<polyline points="16 17 21 12 16 7"/>
								<line x1="21" y1="12" x2="9" y2="12"/>
							</svg>
							Esci
						</a>
				</nav>
			</aside>
			
			<div id = "profilo_contenuto">
				<% if("dati".equals(sezione)){ %>
					<div class = "profilo_box">
						<h2 class = "box_titolo"> I miei dati </h2>
						
						<div class = "griglia_dati">
							<div class = "campo_dati">
								<span class = "label_dati"> Nome </span>
								<span class = "valore_dati"> <%= utente.getNome() %> </span>
							</div>
							
							<div class = "campo_dati">
								<span class = "label_dati"> Cognome </span>
								<span class = "valore_dati"> <%= utente.getCognome() %> </span>
							</div>
							
							<div class = "campo_dati">
								<span class = "label_dati"> Email </span>
								<span class = "valore_dati"> <%= utente.getEmail() %> </span>
							</div>
							
							<div class = "campo_dati">
								<span class = "label_dati"> Cellulare </span>
								<span class = "valore_dati"> <%= utente.getCellulare() != null ? utente.getCellulare() : "-" %> </span>
							</div>
						</div>
						
						<% if(indirizzo != null){ %>
							<h3 id = "box_sottotitolo"> Indirizzo di spedizione </h3>
							<div class = "griglia_dati">
							<div class = "campo_dati">
								<span class = "label_dati"> Via </span>
								<span class = "valore_dati"> <%= indirizzo.getVia() %> </span>
							</div>
							
							<div class = "campo_dati">
								<span class = "label_dati"> Civico </span>
								<span class = "valore_dati"> <%= indirizzo.getCivico() %> </span>
							</div>
							
							<div class = "campo_dati">
								<span class = "label_dati"> Città </span>
								<span class = "valore_dati"> <%= indirizzo.getCitta() %> </span>
							</div>
							
							<div class = "campo_dati">
								<span class = "label_dati"> CAP </span>
								<span class = "valore_dati"> <%= indirizzo.getCAP() %> </span>
							</div>
							
							<div class = "campo_dati">
								<span class = "label_dati"> Provincia </span>
								<span class = "valore_dati"> <%= indirizzo.getProvincia() %> </span>
							</div>
							
							<div class = "campo_dati">
								<span class = "label_dati"> Stato </span>
								<span class = "valore_dati"> <%= indirizzo.getStato() %> </span>
							</div>
						</div>
						<% } %>
					</div>
				
				<% } else if("ordini".equals(sezione)){ %>
					<div class = "profilo_box">
						<h2 class = "box_titolo"> I miei ordini </h2>
						<% if(dbError != null){ %>
							<div id = "alert"> <%= dbError %></div>
						<% } else if(ordini == null || ordini.isEmpty()){ %>
							<div id = "vuoto">
								<svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.4">
									<path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/>
									<polyline points="14 2 14 8 20 8"/>
								</svg>
								
								<p> Non hai ancora effettuato ordini. <br>
								<a href = "<%= ctx %>/Catalogo" id = "shopping"> Vai al catalogo per iniziare a fare shopping </a>
							</div>
						<% } else { %>
							<table id = "tabella_ordini">
								<thead>
									<tr>
										<th> Ordine </th>
										<th> Data </th>
										<th> Stato </th>
										<th> Totale </th>
										<th> </th>
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
			                                	statoLabel = "In spedizione";
			                                	break;
			                                case "IN_PREPARAZIONE": 
			                                	statoCss = "preparazione"; 
			                                	statoLabel = "In preparazione";
			                                	break;
			                                case "ANNULLATO":       
			                                	statoCss = "annullato";   
			                                	statoLabel = "Annullato";     
			                                	break;
			                                default:               
			                                	statoCss = "";                    
			                                	statoLabel = stato;
										}
									%>
									
									<tr>
										<td class = "td_id">#<%= o.getId_ordine() %> </td>
										<td> <%= o.getData_ordine().format(formatter) %> </td>
										<td> <span class = "stato_badge_<%= statoCss %>"> <%= statoLabel %> </span></td>
										<td class = "td_totale">&euro; <%= String.format("%.2f", o.getPrezzo_totale()) %></td>
										<td>
											<a href = "<%= ctx %>/DettaglioOrdine?id=<%=o.getId_ordine() %>" class = "vedi"> Vedi </a>
										</td>
									</tr>
									<% } %>
								</tbody>
							</table>
							<% } %>
					</div>
					<% } %>
			</div>
		</div>
	</main>
	
	<%@ include file = "footer.jsp" %>
</body>
</html>