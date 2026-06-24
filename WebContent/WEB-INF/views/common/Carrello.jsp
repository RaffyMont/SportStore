<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.unisa.storage.model.CarrelloBean" %>
<%@ page import="it.unisa.storage.model.CarrelloItemBean" %>
<%@ page import="it.unisa.storage.model.UtenteBean" %>
<%@ page import="java.util.Map" %>
<%
    String ctx = (String) request.getAttribute("ctx");
    if (ctx == null) ctx = request.getContextPath();
    request.setAttribute("ctx", ctx);

    UtenteBean utente = (UtenteBean)  request.getAttribute("utente");
    CarrelloBean carrello2 = (CarrelloBean) request.getAttribute("carrello");

    Map<String, CarrelloItemBean> items =
        (carrello2 != null) ? carrello2.getItems() : null;

    boolean vuoto = (items == null || items.isEmpty());
    request.setAttribute("paginaAttiva", "");
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sport Shop – Il tuo carrello</title>
    <link rel="stylesheet" href="<%= ctx %>/styles/home.css">
    <link rel="stylesheet" href="<%= ctx %>/styles/carrello.css">
</head>
<body>

<%@ include file="header.jsp" %>

<main id="main-content">
    <nav id="breadcrumb">
        <ol>
            <li><a href="<%= ctx %>/Home">Home</a></li>
            <li>/</li>
            <li aria-current="page">Carrello</li>
        </ol>
    </nav>

    <div id="carrello_container">
        <h1 id="carrello_titolo">Il tuo carrello</h1>

        <% if (vuoto) { %>
	        <div id="carrello_vuoto">
	            <svg width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.4">
	                <path d="M6 2 3 6v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2V6l-3-4z"/>
	                <line x1="3" y1="6" x2="21" y2="6"/>
	                <path d="M16 10a4 4 0 0 1-8 0"/>
	            </svg>
	            <p>Il tuo carrello è vuoto.</p>
	            <a href="<%= ctx %>/Home" class="btn_continua_shopping">Continua lo shopping</a>
	        </div>

        <% } else { %>
        <div id="carrello_layout">
            <div id="carrello_items">
                <% for (Map.Entry<String, CarrelloItemBean> entry : items.entrySet()) {
                    String chiave = entry.getKey();
                    CarrelloItemBean item = entry.getValue();
                %>
                <div class="carrello_item" id="item_<%= chiave.replace("_","").replace(" ","") %>">
                    <div class="item_immagine">
                        <% if (item.getImmagine() != null && !item.getImmagine().isBlank()) { %>
                            <img src="<%= ctx %>/<%= item.getImmagine() %>" alt="<%= item.getModello() %>" class="item_img">
                        <% } else { %>
                            <div class="item_img_placeholder">
                                <svg width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="#ccc" stroke-width="1.5">
                                    <rect x="3" y="3" width="18" height="18" rx="2"/><circle cx="8.5" cy="8.5" r="1.5"/>
                                    <polyline points="21 15 16 10 5 21"/>
                                </svg>
                            </div>
                        <% } %>
                    </div>

                    <div class="item_info">
                        <p class="item_marca"><%= item.getMarca() %></p>
                        <p class="item_modello"><%= item.getModello() %></p>
                        <% if (item.getColore() != null && !item.getColore().isBlank()) { %>
                            <p class="item_variante">Colore: <%= item.getColore() %></p>
                        <% } %>
                        <% if (item.getTaglia() != null && !item.getTaglia().isBlank()) { %>
                            <p class="item_variante">Taglia: <%= item.getTaglia() %></p>
                        <% } %>
                        <p class="item_prezzo_unit">&euro; <%= String.format("%.2f", item.getPrezzo()) %> cad.</p>
                    </div>

                    <div class="item_quantita">
                        <button class="qty_btn" onclick="aggiornaQuantita('<%= chiave %>', -1)">&#8722;</button>
                        <span class="qty_valore" id="qty_<%= chiave.replace("_","").replace(" ","") %>">
                            <%= item.getQuantita() %>
                        </span>
                        <button class="qty_btn" onclick="aggiornaQuantita('<%= chiave %>', 1)">&#43;</button>
                    </div>

                    <div class="item_subtotale">
                        <span class="item_prezzo_tot" id="sub_<%= chiave.replace("_","").replace(" ","") %>">
                            &euro; <%= String.format("%.2f", item.getTotale()) %>
                        </span>
                    </div>

                    <button class="item_rimuovi"
                            onclick="rimuoviArticolo('<%= chiave %>')"
                            title="Rimuovi articolo"
                            aria-label="Rimuovi <%= item.getModello() %>">
                        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <line x1="18" y1="6" x2="6" y2="18"/>
                            <line x1="6" y1="6" x2="18" y2="18"/>
                        </svg>
                    </button>

                </div>
                <% } %>

                <div id="svuota_wrap">
                    <button id="btn_svuota" onclick="svuotaCarrello()">
                        Svuota carrello
                    </button>
                </div>
            </div>

            <div id="carrello_riepilogo">
                <h2 id="riepilogo_titolo">Riepilogo ordine</h2>

                <div class="riepilogo_riga">
                    <span>Articoli (<span id="riepilogo_num"><%= carrello2.getNumeroArticoli() %></span>)</span>
                    <span id="riepilogo_subtotale">&euro; <%= String.format("%.2f", carrello2.getTotale()) %></span>
                </div>

                <div class="riepilogo_riga">
                    <span>Spedizione</span>
                    <span class="spedizione_gratis">
                        <%= carrello2.getTotale() >= 50 ? "Gratuita" : "€ 4,99" %>
                    </span>
                </div>

                <div class="riepilogo_sep"></div>

                <div class="riepilogo_riga riepilogo_totale">
                    <span>Totale</span>
                    <span id="riepilogo_totale_val">
                        &euro; <%= String.format("%.2f",
                            carrello2.getTotale() >= 50
                                ? carrello2.getTotale()
                                : carrello2.getTotale() + 4.99) %>
                    </span>
                </div>

                <% if (utente != null) { %>
                    <a href="<%= ctx %>/Checkout" id="btn_checkout">PROCEDI ALL'ACQUISTO</a>
                <% } else { %>
                    <a href="<%= ctx %>/Login?redirect=<%= ctx %>/Carrello" id="btn_checkout">
                        ACCEDI PER CONTINUARE
                    </a>
                <% } %>

                <a href="<%= ctx %>/Home" id="btn_continua">← Continua lo shopping</a>
            </div>
        </div>
        <% } %>
    </div>
</main>
<div id="toast" class="toast" role="status" aria-live="polite"></div>

<%@ include file="footer.jsp" %>

<script>
    var CTX = "<%= ctx %>";
</script>
<script src="<%= ctx %>/scripts/carrello.js"></script>
</body>
</html>