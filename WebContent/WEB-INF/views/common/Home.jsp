<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="it.unisa.storage.model.ProdottoBean" %>
<%@ page import="it.unisa.storage.model.UtenteBean" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>

<% 
	UtenteBean utente = (UtenteBean) request.getAttribute("utente");
	int totaleArticoli = (request.getAttribute("totaleArticoli") != null) ? (int) request.getAttribute("totaleArticoli") : 0;
	String dbError = (String) request.getAttribute("dbError");
	
	@SuppressWarnings("unchecked")
	List<ProdottoBean> scarpeList = (List<ProdottoBean>) request.getAttribute("scarpeEvidenza");
	@SuppressWarnings("unchecked")
	List<ProdottoBean> vestitiList = (List<ProdottoBean>) request.getAttribute("vestitiEvidenza");
	@SuppressWarnings("unchecked")
	List<ProdottoBean> accessoriList = (List<ProdottoBean>) request.getAttribute("accessoriEvidenza");
	@SuppressWarnings("unchecked")
	Map<String, String> immaginiMap = (Map<String, String>) request.getAttribute("immaginiMap");
	
	String ctx = request.getContextPath();
	
	boolean catalogoVuoto = (scarpeList == null || scarpeList.isEmpty()) && (vestitiList == null || vestitiList.isEmpty()) && (accessoriList == null || accessoriList.isEmpty());
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Sport Shop - Il meglio per il tuo sport</title>
	<link rel="stylesheet" href="<%= ctx %>/styles/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/home.css">
</head>
<body>
	<%@ include file = "header.jsp" %>
	<main id="main-content">

    <!-- ── HERO ──────────────────────────────────────────────────────── -->
    <section class="hero" aria-labelledby="hero-title">
        <div class="hero_text">
            <p class="hero_eyebrow">Nuova Collezione 2026</p>
            <h1 class="hero_title" id="hero-title">IL MEGLIO<br>PER IL TUO SPORT</h1>
            <p class="hero_sub">Scopri la nuova collezione di abbigliamento sportivo</p>
            <a href="<%= ctx %>/Catalogo" class="btn btn--primary btn--lg">SCOPRI ORA</a>
        </div>
        <div class="hero_visual" aria-hidden="true">
            <img src="<%= ctx %>/images/home.png"
                 alt="SportShop home image"
                 class="hero_img"
                 width="800" height="400">
        </div>
    </section>

    <!-- ── VANTAGGI ──────────────────────────────────────────────────── -->
    <section class="benefits" aria-label="I nostri vantaggi">
        <div class="benefits_container">
            <div class="benefit">
                <span class="benefit_icon" aria-hidden="true">
                    <svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8">
                        <rect x="1" y="3" width="15" height="13" rx="1"/><path d="M16 8h4l3 5v3h-7V8z"/><circle cx="5.5" cy="18.5" r="2.5"/><circle cx="18.5" cy="18.5" r="2.5"/>
                    </svg>
                </span>
                <div>
                    <strong>SPEDIZIONE GRATUITA</strong>
                    <span>sopra i 50€</span>
                </div>
            </div>
            <div class="benefit benefit--sep"></div>
            <div class="benefit">
                <span class="benefit_icon" aria-hidden="true">
                    <svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8">
                        <polyline points="1 4 1 10 7 10"/><path d="M3.51 15a9 9 0 1 0 .49-4.5"/>
                    </svg>
                </span>
                <div>
                    <strong>RESO FACILE</strong>
                    <span>entro 30 giorni</span>
                </div>
            </div>
            <div class="benefit benefit--sep"></div>
            <div class="benefit">
                <span class="benefit_icon" aria-hidden="true">
                    <svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8">
                        <rect x="3" y="11" width="18" height="11" rx="2"/><path d="M7 11V7a5 5 0 0 1 10 0v4"/>
                    </svg>
                </span>
                <div>
                    <strong>PAGAMENTI SICURI</strong>
                    <span>100% protetti</span>
                </div>
            </div>
        </div>
    </section>

    <!-- ── MESSAGGIO ERRORE DB ───────────────────────────────────────── -->
    <% if (dbError != null) { %>
    <div class="alert alert--warning" role="alert">
        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" aria-hidden="true">
            <path d="M10.29 3.86L1.82 18a2 2 0 0 0 1.71 3h16.94a2 2 0 0 0 1.71-3L13.71 3.86a2 2 0 0 0-3.42 0z"/><line x1="12" y1="9" x2="12" y2="13"/><line x1="12" y1="17" x2="12.01" y2="17"/>
        </svg>
        <%= dbError %>
    </div>
    <% } %>

    <!-- ═══════════════════  PRODOTTI IN EVIDENZA  ════════════════════ -->

    <!-- ── SCARPE ────────────────────────────────────────────────────── -->
    <% if (scarpeList != null && !scarpeList.isEmpty()) { %>
    <section class="catalog-section" id="scarpe" aria-labelledby="title-scarpe">
        <div class="catalog-section_header">
            <h2 class="catalog-section_title" id="title-scarpe">Scarpe</h2>
            <a href="<%= ctx %>/Catalogo?categoria=SCARPA" class="catalog-section__more">Vedi tutte &rarr;</a>
        </div>
        <div class="products-grid">
            <% for (ProdottoBean p : scarpeList) {
                String imgSrc = (immaginiMap != null && immaginiMap.get(p.getId_prodotto()) != null)
                              ? ctx + "/" + immaginiMap.get(p.getId_prodotto())
                              : ctx + "/images/placeholder.png";
            %>
            <article class="product-card">
                <a href="<%= ctx %>/DettaglioProdotto?id=<%= p.getId_prodotto() %>"
                   class="product-card_media-link"
                   tabindex="-1" aria-hidden="true">
                    <div class="product-card__media">
                        <img src="<%= imgSrc %>"
                             alt="<%= p.getMarca() %> <%= p.getModello() %>"
                             loading="lazy"
                             class="product-card_img">
                    </div>
                </a>
                <div class="product-card_body">
                    <span class="product-card_brand"><%= p.getMarca() %></span>
                    <h3 class="product-card_name">
                        <a href="<%= ctx %>/DettaglioProdotto?id=<%= p.getId_prodotto() %>"><%= p.getModello() %></a>
                    </h3>
                    <div class="product-card_footer">
                        <span class="product-card_price">&euro;&nbsp;<%= String.format("%.2f", p.getPrezzo()) %></span>
                        <button class="btn btn--primary btn--sm"
                                data-id="<%= p.getId_prodotto() %>"
                                onclick="aggiungiAlCarrello('<%= p.getId_prodotto() %>', this)">
                            + Carrello
                        </button>
                    </div>
                </div>
            </article>
            <% } %>
        </div>
    </section>
    <% } %>

    <!-- ── ABBIGLIAMENTO ─────────────────────────────────────────────── -->
    <% if (vestitiList != null && !vestitiList.isEmpty()) { %>
    <section class="catalog-section" id="abbigliamento" aria-labelledby="title-vestiti">
        <div class="catalog-section_header">
            <h2 class="catalog-section_title" id="title-vestiti">Abbigliamento</h2>
            <a href="<%= ctx %>/Catalogo?categoria=VESTITO" class="catalog-section__more">Vedi tutti &rarr;</a>
        </div>
        <div class="products-grid">
            <% for (ProdottoBean p : vestitiList) {
                String imgSrc = (immaginiMap != null && immaginiMap.get(p.getId_prodotto()) != null)
                              ? ctx + "/" + immaginiMap.get(p.getId_prodotto())
                              : ctx + "/images/placeholder.png";
            %>
            <article class="product-card">
                <a href="<%= ctx %>/DettaglioProdotto?id=<%= p.getId_prodotto() %>"
                   class="product-card_media-link"
                   tabindex="-1" aria-hidden="true">
                    <div class="product-card__media">
                        <img src="<%= imgSrc %>"
                             alt="<%= p.getMarca() %> <%= p.getModello() %>"
                             loading="lazy"
                             class="product-card_img">
                    </div>
                </a>
                <div class="product-card_body">
                    <span class="product-card_brand"><%= p.getMarca() %></span>
                    <h3 class="product-card_name">
                        <a href="<%= ctx %>/DettaglioProdotto?id=<%= p.getId_prodotto() %>"><%= p.getModello() %></a>
                    </h3>
                    <div class="product-card_footer">
                        <span class="product-card_price">&euro;&nbsp;<%= String.format("%.2f", p.getPrezzo()) %></span>
                        <button class="btn btn--primary btn--sm"
                                data-id="<%= p.getId_prodotto() %>"
                                onclick="aggiungiAlCarrello('<%= p.getId_prodotto() %>', this)">
                            + Carrello
                        </button>
                    </div>
                </div>
            </article>
            <% } %>
        </div>
    </section>
    <% } %>

    <!-- ── ACCESSORI ─────────────────────────────────────────────────── -->
    <% if (accessoriList != null && !accessoriList.isEmpty()) { %>
    <section class="catalog-section" id="accessori" aria-labelledby="title-accessori">
        <div class="catalog-section_header">
            <h2 class="catalog-section_title" id="title-accessori">Accessori</h2>
            <a href="<%= ctx %>/Catalogo?categoria=ACCESSORIO" class="catalog-section_more">Vedi tutti &rarr;</a>
        </div>
        <div class="products-grid">
            <% for (ProdottoBean p : accessoriList) {
                String imgSrc = (immaginiMap != null && immaginiMap.get(p.getId_prodotto()) != null)
                              ? ctx + "/" + immaginiMap.get(p.getId_prodotto())
                              : ctx + "/images/placeholder.png";
            %>
            <article class="product-card">
                <a href="<%= ctx %>/DettaglioProdotto?id=<%= p.getId_prodotto() %>"
                   class="product-card_media-link"
                   tabindex="-1" aria-hidden="true">
                    <div class="product-card__media">
                        <img src="<%= imgSrc %>"
                             alt="<%= p.getMarca() %> <%= p.getModello() %>"
                             loading="lazy"
                             class="product-card_img">
                    </div>
                </a>
                <div class="product-card_body">
                    <span class="product-card_brand"><%= p.getMarca() %></span>
                    <h3 class="product-card_name">
                        <a href="<%= ctx %>/DettaglioProdotto?id=<%= p.getId_prodotto() %>"><%= p.getModello() %></a>
                    </h3>
                    <div class="product-card_footer">
                        <span class="product-card_price">&euro;&nbsp;<%= String.format("%.2f", p.getPrezzo()) %></span>
                        <button class="btn btn--primary btn--sm"
                                data-id="<%= p.getId_prodotto() %>"
                                onclick="aggiungiAlCarrello('<%= p.getId_prodotto() %>', this)">
                            + Carrello
                        </button>
                    </div>
                </div>
            </article>
            <% } %>
        </div>
    </section>
    <% } %>

    <!-- Catalogo vuoto -->
    <% if (catalogoVuoto && dbError == null) { %>
    <div class="empty-state" role="status">
        <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" aria-hidden="true">
            <circle cx="12" cy="12" r="10"/><line x1="12" y1="8" x2="12" y2="12"/><line x1="12" y1="16" x2="12.01" y2="16"/>
        </svg>
        <p>Nessun prodotto disponibile al momento.</p>
        <span>Torna presto, stiamo aggiornando il catalogo!</span>
    </div>
    <% } %>

</main>

<!-- ═══════════════════════════  FOOTER  ══════════════════════════════ -->
<%@ include file="footer.jsp" %>
</body>
</html>