const NOME_RE  = /^[a-zA-ZÀ-ú'\-\s]{2,50}$/;
const CAP_RE   = /^\d{5}$/;
const CARTA_RE = /^\d{16}$/;
const CVV_RE   = /^\d{3,4}$/;
const SCAD_RE  = /^(0[1-9]|1[0-2])\/\d{2}$/;

function mostraErrore(gruppoId, msg) {
    const g = document.getElementById(gruppoId);
    if (!g) return;
    let span = g.querySelector(".ferr");
    if (!span) {
        span = document.createElement("span");
        span.className = "ferr";
        span.style.cssText = "display:block;font-size:11px;color:#e53935;font-weight:bold;margin-top:3px";
        g.appendChild(span);
    }
    span.textContent = msg;
    g.classList.add("has-error");
}

function rimuoviErrore(gruppoId) {
    const g = document.getElementById(gruppoId);
    if (!g) return;
    const span = g.querySelector(".ferr");
    if (span) span.textContent = "";
    g.classList.remove("has-error");
}

function val(id) {
    const el = document.getElementById(id);
    return el ? el.value.trim() : "";
}

function vNome() {
    const v = val("nome");
	if (!v) { 
		mostraErrore("g_nome", "Il nome è obbligatorio."); 
		return false; 
	}
    if (!NOME_RE.test(v)){ 
		mostraErrore("g_nome", "Solo lettere, 2-50 caratteri."); 
		return false; 
	}
    rimuoviErrore("g_nome"); 
	return true;
}

function vCognome() {
    const v = val("cognome");
    if (!v) { 
		mostraErrore("g_cognome", "Il cognome è obbligatorio."); 
		return false; 
	}
    if (!NOME_RE.test(v)){ 
		mostraErrore("g_cognome", "Solo lettere, 2-50 caratteri."); 
		return false; 
	}
    rimuoviErrore("g_cognome"); 
	return true;
}

function vVia() {
    const v = val("via");
    if (!v || v.length < 2) { 
		mostraErrore("g_via", "Inserisci una via valida."); 
		return false; 
	}
    rimuoviErrore("g_via"); 
	return true;
}

function vCivico() {
    const v = val("civico");
    if (!v) { 
		mostraErrore("g_civico", "Il civico è obbligatorio."); 
		return false; 
	}
    rimuoviErrore("g_civico"); 
	return true;
}

function vCitta() {
    const v = val("citta");
    if (!v || v.length < 2) { 
		mostraErrore("g_citta", "Inserisci una città valida."); 
		return false; 
	}
    rimuoviErrore("g_citta"); 
	return true;
}

function vCap() {
    const v = val("cap");
    if (!CAP_RE.test(v)) { 
		mostraErrore("g_cap", "CAP non valido (5 cifre)."); 
		return false; 
	}
    rimuoviErrore("g_cap"); 
	return true;
}

function vProvincia() {
    const v = val("provincia");
    if (!v || v.length < 2) { 
		mostraErrore("g_provincia", "Inserisci una provincia valida."); 
		return false; 
	}
    rimuoviErrore("g_provincia"); 
	return true;
}

function vStato() {
    const v = val("stato");
    if (!v) { 
		mostraErrore("g_stato", "Inserisci lo stato."); 
		return false; 
	}
    rimuoviErrore("g_stato"); 
	return true;
}

function vNumeroCarta() {
    const v = val("numeroCarta").replace(/\s/g, "");
    if (!CARTA_RE.test(v)) { 
		mostraErrore("g_numeroCarta", "Numero carta non valido (16 cifre)."); 
		return false; 
	}
    rimuoviErrore("g_numeroCarta"); 
	return true;
}

function vTitolare() {
    const v = val("titolare");
    if (!v || v.length < 2) { 
		mostraErrore("g_titolare", "Inserisci il nome del titolare."); 
		return false; 
	}
    rimuoviErrore("g_titolare"); 
	return true;
}

function vScadenza() {
    const v = val("scadenza");
    if (!SCAD_RE.test(v)) { 
		mostraErrore("g_scadenza", "Scadenza non valida (MM/AA)."); 
		return false; 
	}

    const [mm, yy] = v.split("/").map(Number);
    const now = new Date();
    const expDate = new Date(2000 + yy, mm - 1, 1);
    const today   = new Date(now.getFullYear(), now.getMonth(), 1);
    if (expDate < today) { 
		mostraErrore("g_scadenza", "La carta è scaduta."); 
		return false; 
	}

    rimuoviErrore("g_scadenza"); 
	return true;
}

function vCvv() {
    const v = val("cvv");
    if (!CVV_RE.test(v)) { 
		mostraErrore("g_cvv", "CVV non valido (3-4 cifre)."); 
		return false; 
	}
    rimuoviErrore("g_cvv"); 
	return true;
}

const cartaInput = document.getElementById("numeroCarta");
if (cartaInput) {
    cartaInput.addEventListener("input", function () {
        this.value = this.value.replace(/\D/g, "").substring(0, 16);
    });
}

const scadenzaInput = document.getElementById("scadenza");
if (scadenzaInput) {
    scadenzaInput.addEventListener("input", function () {
        let v = this.value.replace(/\D/g, "").substring(0, 4);
        if (v.length >= 3) v = v.substring(0, 2) + "/" + v.substring(2);
        this.value = v;
    });
}

const bindings = [
    ["nome",        vNome],
    ["cognome",     vCognome],
    ["via",         vVia],
    ["civico",      vCivico],
    ["citta",       vCitta],
    ["cap",         vCap],
    ["provincia",   vProvincia],
    ["stato",       vStato],
    ["numeroCarta", vNumeroCarta],
    ["titolare",    vTitolare],
    ["scadenza",    vScadenza],
    ["cvv",         vCvv],
];

bindings.forEach(([id, fn]) => {
    const el = document.getElementById(id);
    if (el) el.addEventListener("change", fn);
});

document.getElementById("checkout").addEventListener("submit", function (e) {
    const risultati = [
        vNome(), vCognome(), vVia(), vCivico(), vCitta(),
        vCap(), vProvincia(), vStato(),
        vNumeroCarta(), vTitolare(), vScadenza(), vCvv()
    ];

    if (risultati.includes(false)) {
        e.preventDefault();
        const primo = document.querySelector(".has-error");
        if (primo) primo.scrollIntoView({ behavior: "smooth", block: "center" });
        return;
    }

    const btn = document.getElementById("acquista");
    if (btn) {
        btn.disabled = true;
        btn.innerHTML = '<span>Elaborazione...</span>';
    }
});

window.addEventListener("pageshow", function () {
    const btn = document.getElementById("acquista");
    if (btn) {
        btn.disabled = false;
        btn.innerHTML = '<svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5"><path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"/></svg> ACQUISTA ORA';
    }
});