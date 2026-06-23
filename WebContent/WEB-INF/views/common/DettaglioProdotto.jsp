<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.unisa.storage.model.ProdottoBean" %>
<%@ page import="it.unisa.storage.model.ImmagineBean" %>
<%@ page import="it.unisa.storage.model.SupportoColoreBean" %>
<%@ page import="it.unisa.storage.model.SupportoTagliaBean" %>
<%@ page import="java.util.Collection" %>

<%
	String ctx = (String) request.getAttribute("ctx");
	if(ctx == null)
		ctx = request.getContextPath();
	
	ProdottoBean prodotto = (ProdottoBean) request.getAttribute("prodotto");
	
	@SuppressWarnings("unchecked")
	Collection<ImmagineBean> immagini = (Collection<ImmagineBean>) request.getAttribute("immagini");
	
	@SuppressWarnings("unchecked")
	Collection<SupportoColoreBean> colori = (Collection<SupportoColoreBean>) request.getAttribute("colori");
	
	@SuppressWarnings("unchecked")
	Collection<SupportoTagliaBean> taglie = (Collection<SupportoTagliaBean>) request.getAttribute("taglie");
	
	String immaginePrincipale = ctx + "/images/immagine.png";
	if(immagini != null && !immagini.isEmpty())
		immaginePrincipale = immagini.iterator().next().getPathname();
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title> <%= prodotto != null ? prodotto.getModello() : "Prodotto" %></title>
	<link rel = "stylesheet" href = "<%= ctx %>/styles/home.css">
	<link rel = "stylesheet" href = "<%= ctx %>/styles/dettaglio.css">
</head>
<body>
	<%@ include file = "header.jsp" %>
	
	<main id = "main">
		<% if(prodotto != null){ %>
		<div id = "container">
			<div id = "immagine"> 
				<img src = "<%= immaginePrincipale %>" alt = "<%= prodotto.getMarca() %> <%= prodotto.getModello() %>" id = "imgPrincipale">
				
				<% if(immagini != null && immagini.size() > 1){ %>
				<div id = "miniature">
					<% for(ImmagineBean img : immagini){ %>
					<img src = "<%= img.getPathname() %>" alt = "<%= prodotto.getModello() %>" class = "mini" onclick="document.getElementById('imgPrincipale').src='<%= img.getPathname() %>'">
					<% } %>
				</div>
				<% } %>
			</div>
			
			<div id = "informazioni">
				<span id = "marca"> <%= prodotto.getMarca() %></span>
				<h1 id = "modello"> <%= prodotto.getModello() %></h1>
				<p id = "prezzo"> <%= String.format("%.2f", prodotto.getPrezzo()) %>&euro;
				
				<% if(prodotto.getDescrizione() != null && !prodotto.getDescrizione().isBlank()){ %>
					<p id = "descrizione"> <%= prodotto.getDescrizione() %>
				<% } %>
				<% if(colori != null && !colori.isEmpty()){ %>
					   <div class = "sezione">
					   		<p class = "label"> Colori Disponibili
					   		<div id = "colori">
					   			<% for(SupportoColoreBean c : colori){ %>
					   			<span class = "tag_colore"> <%= c.getNome().getColore() %></span>
					   			<% } %>
					   		</div>
					   </div>
				<% } %>
				<% if(taglie != null && !taglie.isEmpty()){ %>
						<div class = "sezione">
							<p class = "label"> Taglie disponibili
							<div id = "taglie">
								<% for(SupportoTagliaBean t : taglie){ %>
					   			<span class = "box_taglia"> <%= t.getTaglia().getTaglia() %></span>
					   			<% } %>
							</div>
						</div>
				<% } %>
				<div id = "disponibilita">
					<% if(prodotto.getStock() > 0){ %>	
						<span id = "disponibile"> &#10003; Disponibile/i <%= prodotto.getStock() %> pezzi</span>
					<% } else { %>
						<span id = "esaurito"> &#10007; Esaurito </span>
					<% } %>
				</div>
				
				<% if(prodotto.getStock() > 0){ %>
					<div id = "quantita_wrap">
						<p class = "label"> Quantità
						<div id = "quantita">
							<button type = "button" class = "bottone" id = "meno" onclick="cambiaQuantita(-1, <%= prodotto.getStock() %>)"> &#8722; </button>
							<input type = "number" id = "input_quantita" class = "input" value = "1" min="1" max="<%= prodotto.getStock() %>" readonly>
							<button type = "button" class = "bottone" id = "piu" onclick="cambiaQuantita(+1, <%= prodotto.getStock() %>)"> &#43; </button>
						</div>
					</div>
					<button type = "submit" id = "aggiungi"> <!-- Da implementare successivamente --> Aggiungi al carrello</button>
				<% } else { %>
            		<button type = "submit" id = "non aggiungi" disabled> Prodotto esaurito </button>
            	<% } %>
				<a href="<%= ctx %>/Catalogo" id = "torna">  &larr; Torna al catalogo </a>	
			</div>
		</div>
		<% } else { %>
    		<div id = "error">Prodotto non trovato.</div>
    	<% } %>
	</main>
	
	<%@ include file = "footer.jsp" %>
	<script src = "<%= ctx %>/scripts/dettaglio.js"></script>
</body>
</html>