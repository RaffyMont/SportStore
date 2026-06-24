<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "it.unisa.storage.model.UtenteBean" %>
<%
	String ctx = request.getContextPath();
	request.setAttribute("ctx", ctx);
	
	UtenteBean utenteSession = (UtenteBean) session.getAttribute("utente");
	
	if(utenteSession != null)
	{
		response.sendRedirect(ctx + "/Home");
		return;
	}
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Registrazione</title>
	<link rel = "stylesheet" href = "<%= ctx %>/styles/home.css">
	<link rel = "stylesheet" href = "<%= ctx %>/styles/registrazione.css">
</head>
<body id = "body_registrazione">
	<%@ include file = "header.jsp" %>
	
		<main id = "main-content">
			<div id = "registrazione_card">
				<div id = "header">
					<svg width="30" height="30" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8">
	                    <path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2"/>
	                    <circle cx="9" cy="7" r="4"/>
	                    <line x1="19" y1="8" x2="19" y2="14"/>
	                    <line x1="22" y1="11" x2="16" y2="11"/>
                	</svg>	
				</div>
				
				<h1 id = "header_title"> Crea il tuo account </h1>
				<p id = "hedear_paragraph"> Registrati e inizia a fare shopping
			
			
			
			<form class = "registrazione_form" id = "form" action = "<%= ctx %>/Registrazione" method = "post">
				<div class = "titolo_sezione">
					<svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
						<circle cx="12" cy="8" r="4"/>
							<path d="M4 20c0-4 3.6-7 8-7s8 3 8 7"/>
					</svg>
                	Dati personali
            	</div>
					
			 	<div class = "registrazione_schermata">
			 		<div class = "form_group" id = "div_nome">
			 			<label for = "nome" class = "form_label"> Nome <span class = "req"> * </span></label>
			 			
			 			<div class = "input_wrap"> 
			 				<input type = "text" id = "nome" name = "nome" class = "form_input" maxlength = "256">
			 			</div>
			 		</div>
			 		
			 		<div class = "form_group" id = "div_cognome">
			 			<label for = "cognome" class = "form_label"> Cognome <span class = "req"> * </span></label>
			 			
			 			<div class = "input_wrap"> 
			 				<input type = "text" id = "cognome" name = "cognome" class = "form_input" maxlength = "256">
			 			</div>
			 		</div>
			 	</div>
			 		
			 		<div class = "form_group" id = "div_email">
			 			<label for = "email" class = "form_label"> Email <span class = "req"> * </span></label>
			 			
			 			<div class = "input_wrap"> 
			 				<input type = "email" id = "email" name = "email" class = "form_input" maxlength = "256">
			 			</div>
			 		</div>
			 		
			 		
			 	<div class = "registrazione_schermata">
			 	
			 		<div class = "form_group" id = "div_password">
			 			<label for = "password" class = "form_label"> Password <span class = "req"> * </span></label>
			 			
			 			<div class = "input_wrap"> 
			 				<input type = "password" id = "password" name = "password" class = "form_input" maxlength = "64">
			 			</div>
			 		</div>
			 		
			 		<div class = "form_group" id = "div_conferma">
			 			<label for = "nome" class = "form_label"> Conferma Password <span class = "req"> * </span></label>
			 			
			 			<div class = "input_wrap"> 
			 				<input type = "password" id = "conferma" name = "conferma" class = "form_input" maxlength = "64">
			 			</div>
			 		</div>
			 	</div>
			 		
			 		<div class = "form_group" id = "div_cellulare">
			 			<label for = "cellulare" class = "form_label"> Cellulare <span class = "req"> * </span></label>
			 			
			 			<div class = "input_wrap"> 
			 				<input type = "tel" id = "cellulare" name = "cellulare" placeholder = "XXX-XXX-XXXX" class = "form_input" maxlength = "15">
			 			</div>
			 		</div>
			 		
			 		<div class = "titolo_sezione">
			 			 <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
			 			 	<path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z"/>
			 			 		<circle cx="12" cy="10" r="3"/>
			 			 </svg>
			 			 Indirizzo di spedizione
			 		</div>
			 		
			 		<div class = "sezione_interna">
			 			<div class = "form_group_interno" id = "div_via">
			 				<label for = "via" class = "form_label"> Via <span class = "req"> * </span></label>
			 			
				 			<div class = "input_wrap"> 
				 				<input type = "text" id = "via" name = "via" class = "form_input" maxlength = "256">
				 			</div>
			 			</div>
			 			
			 			<div class = "form_group_interno" id = "div_civico">
			 				<label for = "civico" class = "form_label"> Civico <span class = "req"> * </span></label>
			 				
			 				<div class = "input_wrap">
			 					<input type = "text" name = "civico" id = "civico" class = "form_input" maxlength = "10">
			 				</div>
			 			</div>
			 		</div>
			 		
			 	<div class = "registrazione_schermata">
			 	
			 		<div class = "form_group" id = "div_citta">
			 			<label for = "citta" class = "form_label"> Citta <span class = "req"> * </span></label>
			 			
			 			<div class = "input_wrap"> 
			 				<input type = "text" id = "citta" name = "citta"  class = "form_input" maxlength = "256">
			 			</div>
			 		</div>
			 		
			 		<div class = "form_group" id = "div_cap">
			 			<label for = "cap" class = "form_label"> CAP <span class = "req"> * </span></label>
			 			
			 			<div class = "input_wrap"> 
			 				<input type = "text" id = "cap" name = "cap" class = "form_input" maxlength = "10">
			 			</div>
			 		</div>
			 	</div>
			 	
			 	<div class = "registrazione_schermata">
			 	
			 		<div class = "form_group" id = "div_provincia">
			 			<label for = "provincia" class = "form_label"> Provincia <span class = "req"> * </span></label>
			 			
			 			<div class = "input_wrap"> 
			 				<input type = "text" id = "provincia" name = "provincia" class = "form_input" maxlength = "64">
			 			</div>
			 		</div>
			 		
			 		<div class = "form_group" id = "div_stato">
			 			<label for = "stato" class = "form_label"> Stato <span class = "req"> * </span></label>
			 			
			 			<div class = "input_wrap"> 
			 				<input type = "text" id = "stato" name = "stato" class = "form_input" maxlength = "15">
			 			</div>
			 		</div>
			 	</div>
			 	<button type = "submit" id = "button">
			 		<span id = "testo_button"> CREA ACCOUNT </span>
			 		<span id = "spinner_button" aria-hidden = "true"> </span>
			 	</button>
			 </form>
			 	
			 	<div id = "divisore"> <span> Hai già un account? </span></div>
			 	<div id = "link"> 
			 		<a href = "<%= ctx %>/Login" id = "link_login"> Accedi </a>
			 	</div>

			</div>
		</main>
	
	<%@ include file = "footer.jsp" %>
	<script src="<%= ctx %>/scripts/registrazione.js"></script>
</body>
</html>