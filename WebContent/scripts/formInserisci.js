"use strict";

function mostraErrore(gruppoId, msg) {
    const gruppo = document.getElementById(gruppoId);
    if (!gruppo) return;

    let span = gruppo.querySelector(".errore-msg");
    if (!span) {
        span = document.createElement("span");
        span.className = "errore-msg";
        span.style.cssText = "display:block;font-size:11px;color:#e53935;font-weight:bold;margin-top:3px";
        gruppo.appendChild(span);
    }
    span.textContent = msg;
    gruppo.classList.add("has-error");
}

function rimuoviErrore(gruppoId) {
    const gruppo = document.getElementById(gruppoId);
    if (!gruppo) return;
    const span = gruppo.querySelector(".errore-msg");
    if (span) span.textContent = "";
    gruppo.classList.remove("has-error");
}

//VALIDAZIONE PRODOTTO
function validaModello() {
    const v = document.getElementById("modello").value.trim();
    if (!v) { mostraErrore("g_modello", "Il modello è obbligatorio."); return false; }
    if (v.length < 2) { mostraErrore("g_modello", "Il modello deve avere almeno 2 caratteri."); return false; }
    rimuoviErrore("g_modello");
    return true;
}

function validaMarca() {
    const v = document.getElementById("marca").value.trim();
    if (!v) { mostraErrore("g_marca", "La marca è obbligatoria."); return false; }
    if (v.length < 2) { mostraErrore("g_marca", "La marca deve avere almeno 2 caratteri."); return false; }
    rimuoviErrore("g_marca");
    return true;
}

function validaPrezzo() {
    const v = document.getElementById("prezzo").value.trim();
    if (!v) { mostraErrore("g_prezzo", "Il prezzo è obbligatorio."); return false; }
    if (isNaN(v) || parseFloat(v) <= 0)
       { mostraErrore("g_prezzo", "Il prezzo deve essere maggiore di 0."); return false; }
    rimuoviErrore("g_prezzo");
    return true;
}

function validaStock() {
    const v = document.getElementById("stock").value.trim();
    if (v === "") { mostraErrore("g_stock", "Lo stock è obbligatorio."); return false; }
    if (isNaN(v) || parseInt(v) < 0 || !Number.isInteger(parseFloat(v)))
       { mostraErrore("g_stock", "Lo stock deve essere un intero >= 0."); return false; }
    rimuoviErrore("g_stock");
    return true;
}

function validaCategoria() {
    const v = document.getElementById("categoria").value;
    if (!v) { mostraErrore("g_categoria", "Seleziona una categoria."); return false; }
    rimuoviErrore("g_categoria");
    return true;
}

function validaGenere() {
    const v = document.getElementById("genere").value;
    if (!v) { mostraErrore("g_genere", "Seleziona un genere."); return false; }
    rimuoviErrore("g_genere");
    return true;
}


//VALIDAZIONE SCARPA
function validaSuola() {
	const v = document.getElementById("tipo_suola").value;
	if(!v) { mostraErrore("g_tipo_suola", "Seleziona un tipo di suola."); return false; }
	rimuoviErrore("g_tipo_suola");
	return true;
}

function validaMateriale() {
	const v = document.getElementById("materiale_scarpa").value;
	if(!v) { mostraErrore("g_materiale_scarpa", "Seleziona un materiale."); return false; }
	rimuoviErrore("g_materiale_scarpa");
	return true;
}

//VALIDAZIONE VESTITO
function validaCategoriaVestito() {
	const v = document.getElementById("categoria_vestito").value;
	if(!v) { mostraErrore("g_categoria_vestito", "Seleziona una categoria di vestito."); return false; }
	rimuoviErrore("g_categoria_vestito");
	return true;
}

function validaTipoVita() {
	const v = document.getElementById("tipo_vita").value;
	if(!v) { mostraErrore("g_tipo_vita", "Seleziona un tipo vita."); return false; }
	rimuoviErrore("g_tipo_vita");
	return true;
}

function validaTessuto() {
	const v = document.getElementById("tessuto").value;
	if(!v) { mostraErrore("g_tessuto", "Seleziona un tessuto."); return false; }
	rimuoviErrore("g_tessuto");
	return true;
}

function validaStagione() {
	const v = document.getElementById("stagione").value;
	if(!v) { mostraErrore("g_stagione", "Seleziona una stagione."); return false; }
	rimuoviErrore("g_stagione");
	return true;
}

function validaTipoCollo() {
	const v = document.getElementById("tipo_collo").value;
	if(!v) { mostraErrore("g_tipo_collo", "Seleziona un tipo collo."); return false; }
	rimuoviErrore("g_tipo_collo");
	return true;
}

function validaManica() {
	const v = document.getElementById("manica").value;
	if(!v) { mostraErrore("g_manica", "Seleziona una manica"); return false; }
	rimuoviErrore("g_manica");
	return true;
}

function validaGamba() {
	const v = document.getElementById("gamba").value;
	if(!v) { mostraErrore("g_gamba", "Seleziona una gamba"); return false; }
	rimuoviErrore("g_gamba");
	return true;
}


//VALIDAZIONE ACCESSORI
function validaTipoAccessorio() {
	const v = document.getElementById("tipo_accessorio").value;
	if(!v) { mostraErrore("g_tipo_accessorio", "Seleziona un tipo accessorio"); return false; }
	rimuoviErrore("g_tipo_accessorio");
	return true;
}

function validaMaterialeAccessorio() {
	const v = document.getElementById("materiale_accessorio").value;
	if(!v) { mostraErrore("g_materiale_accessorio", "Seleziona un materiale per l'accessorio"); return false; }
	rimuoviErrore("g_materiale_accessorio");
	return true;
}

//PRODOTTO
document.getElementById("modello").addEventListener("change", validaModello);
document.getElementById("marca").addEventListener("change", validaMarca);
document.getElementById("prezzo").addEventListener("change", validaPrezzo);
document.getElementById("stock").addEventListener("change", validaStock);
document.getElementById("categoria").addEventListener("change", validaCategoria);
document.getElementById("genere").addEventListener("change", validaGenere);

// SCARPA (Nota: l'if serve perché addEventListener viene eseguito al caricamento della pagina ma l'elemento ha display:none)
var tipoSuola = document.getElementById("tipo_suola");
var materialeScarpa = document.getElementById("materiale_scarpa");
if (tipoSuola) tipoSuola.addEventListener("change", validaSuola);
if (materialeScarpa) materialeScarpa.addEventListener("change", validaMateriale);

// VESTITO
var catVestito = document.getElementById("categoriaVestito");
var tipoVita = document.getElementById("tipo_vita");
var tessuto = document.getElementById("tessuto");
var stagione = document.getElementById("stagione");
var tipoCollo = document.getElementById("tipo_collo");
var manica = document.getElementById("manica");
var gamba = document.getElementById("gamba");
if (catVestito) catVestito.addEventListener("change", validaCategoriaVestito);
if (tipoVita) tipoVita.addEventListener("change", validaTipoVita);
if (tessuto) tessuto.addEventListener("change", validaTessuto);
if (stagione) stagione.addEventListener("change", validaStagione);
if (tipoCollo) tipoCollo.addEventListener("change", validaTipoCollo);
if (manica) manica.addEventListener("change", validaManica);
if (gamba) gamba.addEventListener("change", validaGamba);

// ACCESSORIO
var tipoAccessorio = document.getElementById("tipo_accessorio");
var materialeAccessorio = document.getElementById("materiale_accessorio");
if (tipoAccessorio) tipoAccessorio.addEventListener("change", validaTipoAccessorio);
if (materialeAccessorio) materialeAccessorio.addEventListener("change", validaMaterialeAccessorio);

document.getElementById("formInserisci").addEventListener("submit", function(e) {
	
	const categoria = document.getElementById("categoria").value;
	
    const risultati = [
        validaModello(),
        validaMarca(),
        validaPrezzo(),
        validaStock(),
        validaCategoria(),
        validaGenere(),
    ];
	
	if (categoria === "SCARPA") {
	        risultati.push(
	            validaSuola(),
	            validaMateriale()
	        );
	    }

	 if (categoria === "VESTITO") {
	        risultati.push(
	            validaCategoriaVestito(),
	            validaTipoVita(),
	            validaTessuto(),
	            validaStagione(),
	            validaTipoCollo(),
	            validaManica(),
	            validaGamba()
	        );
	    }

	 if (categoria === "ACCESSORIO") {
	        risultati.push(
	            validaTipoAccessorio(),
	            validaMaterialeAccessorio()
	        );
	    }

    if (risultati.includes(false)) {
        e.preventDefault();

        const primo = document.querySelector(".has-error");
        if (primo) primo.scrollIntoView({ behavior: "smooth", block: "center" });
    }
});

document.getElementById('categoria').addEventListener('change', function() {
    var val = this.value;

    var scarpa = ['g_tipo_suola', 'g_materiale_scarpa'];
    var vestito = ['g_categoria_vestito', 'g_tipo_vita', 'g_tessuto', 'g_stagione', 'g_tipo_collo', 'g_manica', 'g_gamba'];
    var accessorio = ['g_tipo_accessorio', 'g_materiale_accessorio'];
    var tutti = scarpa.concat(vestito).concat(accessorio);

    tutti.forEach(function(id) {
        document.getElementById(id).style.display = 'none';
		//rimuoviErrore(id);
    });

    if (val === 'SCARPA') {
        scarpa.forEach(function(id) { document.getElementById(id).style.display = ''; });
    } else if (val === 'VESTITO') {
        vestito.forEach(function(id) { document.getElementById(id).style.display = ''; });
    } else if (val === 'ACCESSORIO') {
        accessorio.forEach(function(id) { document.getElementById(id).style.display = ''; });
    }
});