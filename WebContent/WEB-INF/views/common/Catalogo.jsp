<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.unisa.storage.model.ProdottoBean" %>
<%@ page import="it.unisa.storage.model.ProdottoBean.Genere" %>
<%@ page import="it.unisa.storage.model.UtenteBean" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>

<%
	String genereLabel = (String) request.getAttribute("genereLabel");
	String genereParam = (String) request.getAttribute("genereParam");
	UtenteBean utente = (UtenteBean) request.getAttribute("utente");
	int totaleArticoli = 0;

	if (request.getAttribute("totale") != null) {
	    totaleArticoli = (int) request.getAttribute("totale");
	}
	
	String categoriaFiltro = (String) request.getAttribute("categoriaFiltro");
	
	List<ProdottoBean> prodotti = (List<ProdottoBean>) request.getAttribute("prodotti");
	Map<String, String> immagini = (Map<String, String>) request.getAttribute("immagini");
	
	String ctx = request.getContextPath();
	request.setAttribute("ctx", ctx);
	
	String paginaAttiva = "";

	if (genereParam != null) {
	    paginaAttiva = genereParam.toLowerCase();
	}

	request.setAttribute("paginaAttiva", paginaAttiva);

	int numeroProdotti = 0;

	if (prodotti != null) {
	    numeroProdotti = prodotti.size();
	}
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Catalogo</title>
	<link rel = "stylesheet" href = "<%= ctx %>/styles/home.css">
	<link rel = "stylesheet" href = "<%= ctx %>/styles/catalogo.css">
</head>
<body>
	<%@ include file = "header.jsp" %>
	<main id = "main">
		<section id = "banner">
			<div id = "banner_contenuto">
				<p id = "banner_paragraph"> Collezione 2026 
				<h1 id = "banner_title"> <%= genereLabel.toUpperCase() %></h1>
				<p id = "title_paragraph"> <%= "UOMO".equals(genereParam) ? "Abbigliamento, scarpe e accessori pensati per le tue performance"
											  : "DONNA".equals(genereParam) ? "Stile e performance per ogni tipo di allenamento"
											  : "BAMBINO".equals(genereParam) ? "Tutto il necessario per i piccoli sportivi in crescita"
											  : "UNISEX".equals(genereParam) ? "Capi versatili adatti a tutti, senza distinzioni"
										      : "Tutti i prodotti del nostro catalogo"
											%>
			</div>
			
			<div id = "toolbar">
				<div id = "filtri"> <span id = "filtri_label"> Filtra: </span></div>
				<a href = "<%= ctx %>/Catalogo?genere=<%= genereParam %>" class = "filtro<%= (categoriaFiltro == null) ? "active" : "" %>">
					Tutti
				</a>
				
				<a href = "<%= ctx %>/Catalogo?genere=<%= genereParam %>&categoria=SCARPA" class = "filtro<%= "SCARPA".equals(categoriaFiltro) ? "active" : "" %>">
					Scarpe
				</a>
				
				<a href = "<%= ctx %>/Catalogo?genere=<%= genereParam %>&categoria=VESTITO" class = "filtro<%= "VESTITO".equals(categoriaFiltro) ? "active" : "" %>">
					Abbigliamento
				</a>
				
				<a href = "<%= ctx %>/Catalogo?genere=<%= genereParam %>&categoria=ACCESSORIO" class = "filtro<%= "ACCESSORIO".equals(categoriaFiltro) ? "active" : "" %>">
					Accessori
				</a>
			</div>
			
			<div id = "toolbar_dx">
				<span id = "conteggio"> <%= numeroProdotti %> prodotti</span>
			</div>
			</section>			
			<% if(prodotti != null && !prodotti.isEmpty()){ %>
			<section id = "griglia">
				<div id = "griglia_iterno">
					<%  for(ProdottoBean p : prodotti){
						String img = ctx + "/images/placeholder.png";

							if (immagini != null && immagini.get(p.getId_prodotto()) != null) {
	    						img = ctx + "/" + immagini.get(p.getId_prodotto());
							}
							
							boolean esaurito = p.getStock() <= 0;
					%>
					<article class = "prodotto_card">
						<a href = "<%= ctx %>/DettaglioProdotto?id=<%= p.getId_prodotto() %>" class = "card_link">
							<div class = "card_media">
								<img src = "<%= img %>" alt = "<%= p.getMarca() %> <%= p.getModello() %>" class = "img_prodotto">
								<% if(esaurito){ %>
									<span class = "esaurito"> Esaurito </span>
								<% }							
									String nomeCategoria = "Accessori";
									
									if ("SCARPA".equals(p.getCategoria().name())) {
									    nomeCategoria = "Scarpe";
									} else if ("VESTITO".equals(p.getCategoria().name())) {
									    nomeCategoria = "Abbigliamento";
									}
								%>
								<span class = "prodotto_categoria"> <%= nomeCategoria %></span>
							</div>
						</a>
						<div class = "prodotto_card_body">
							<span class = "brand"> <%= p.getMarca() %></span>
							<h2 class = "name">
								<a href = "<%= ctx %>/DettaglioProdotto?id=<%= p.getId_prodotto() %>"> <%= p.getModello() %></a>
							</h2>
							
							<div class = "card_footer">
								<span class = "prezzo"> &euro;&nbsp;<%= String.format("%.2f", p.getPrezzo()) %></span>
								<% if(!esaurito) { %>
									<button class = "bottone" onclick = "aggiungiAlCarrello('<%= p.getId_prodotto() %>', this)">
										+ Carrello
									</button>
								<% } else { %>
									<span class = "alt_bottone"> Esaurito </span>
								<% } %>
							</div>
						</div>
					</article>
					<% } %>
				</div>
			</section>
		<% } %>
	</main>
	<%@ include file = "footer.jsp" %>
</body>
</html>