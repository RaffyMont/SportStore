<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "it.unisa.storage.model.UtenteBean" %>

<%  String fCtx = (String) request.getAttribute("ctx");

	if (fCtx == null) 
			fCtx = request.getContextPath();
	
	UtenteBean futente = (UtenteBean) session.getAttribute("utente");
%>

<footer class = "footer" aria-label = "Footer sito">
	<div class = "footer_container">
		<div>
			<div class = "footer_brand">
				<a href = "<%= fCtx %>/Home" class = "navbar_logo"> 
					<span class = "logo-sport"> SPORT </span>
					<span class = "logo-shop"> &nbsp;SHOP</span>
				</a>
				<p> Il meglio per il tuo sport.
			</div>
			
			<nav class = "footer_nav" aria-label = "Categorie">
			<h4> Categorie </h4>
			<ul role = "list">
				<li> <a href = "<%= fCtx %>/Catalogo?genere=UOMO"> Uomo </a></li>
				<li> <a href = "<%= fCtx %>/Catalogo?genere=DONNA"> Donna </a></li>
				<li> <a href = "<%= fCtx %>/Catalogo?genere=BAMBINO"> Bambino </a></li>
				<li> <a href = "<%= fCtx %>/Catalogo?genere=UNISEX"> Unisex </a></li>
				<li> <a href = "<%= fCtx %>/Catalogo?categoria=ACCESSORIO"> Accessori </a></li>
			</ul>
			</nav>
			
			<nav class = "footer_nav" aria-label = "Account">
				<h4> Account </h4>
				<ul role = "list">
					<% if(futente != null){ %>
						<li> <a href = "<%= fCtx %>/Profilo"> Il mio profilo </a></li>
						<li> <a herf = "<%= fCtx %>/Profilo?sezione=ordini"> I miei ordini</a> </li>
						<li> <a href = "<%= fCtx %>/Logout"> Esci </a></li>
					<% } else{ %>
						<li> <a href = "<%= fCtx %>/Login"> Accedi </a></li>
						<li> <a href = "<%= fCtx %>/Registrazione"> Registrazione </a></li>
					<% }  %>
				</ul>
			</nav>
			
			<nav class = "footer_nav" aria-label = "Informazioni">
				<h4> Info </h4>
				<ul role = "list">
					<li> <a href = "<%= fCtx %>/Contatti"> Contatti </a></li>
					<li> <a href = "<%= fCtx %>/SpedizioniResi"> Spedizioni e Resi </a></li>
					<li> <a href = "<%= fCtx %>/Privacy"> Privacy </a></li>
				</ul>
			</nav>
		</div>
		
		<div class = "footer_bottom">
			<p> &copy; 2026 SportShop &ndash; Tutti i diritti riservati.
		</div>
	</div>
</footer>
	