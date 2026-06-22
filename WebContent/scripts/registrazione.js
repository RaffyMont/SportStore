const REGOLE = {
    nome:      { regex: /^[a-zA-Z\u00C0-\u00FF\s']{2,}$/, msg: "Il nome deve contenere almeno 2 lettere." },
    cognome:   { regex: /^[a-zA-Z\u00C0-\u00FF\s']{2,}$/, msg: "Il cognome deve contenere almeno 2 lettere." },
    email:     { regex: /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/,  msg: "Inserisci un'email valida." },
    password:  { regex: /^.{8,}$/,                         msg: "La password deve contenere almeno 8 caratteri." },
    cellulare: { regex: /^\d{10}$/,                        msg: "Il cellulare deve contenere esattamente 10 cifre." },
    via:       { regex: /^.{2,}$/,                         msg: "Inserisci una via valida." },
    civico:    { regex: /^[0-9]{1,5}[a-zA-Z]?$/,          msg: "Inserisci un civico valido." },
    citta:     { regex: /^[a-zA-Z\u00C0-\u00FF\s']{2,}$/, msg: "Inserisci una città valida." },
    cap:       { regex: /^\d{5}$/,                         msg: "Il CAP deve contenere esattamente 5 cifre." },
    provincia: { regex: /^[a-zA-Z]{2}$/,                   msg: "La provincia deve contenere 2 lettere (es. NA)." },
    stato:     { regex: /^.{2,}$/,                         msg: "Inserisci uno stato valido." }
};

function mostraErrore(id, messaggio) {
    var div = document.getElementById("div_" + id);
    if (!div) {
        var input = document.getElementById(id);
        if (input) div = input.closest(".form_group") || input.closest(".form_group_interno");
    }
    if (!div) return;

    var span = div.querySelector(".errore_campo");
    if (!span) {
        span = document.createElement("span");
        span.className = "errore_campo";
        div.appendChild(span);
    }
    span.textContent = messaggio;
    var inp = div.querySelector(".form_input");
    if (inp) inp.classList.add("input_errore");
}

function rimuoviErrore(id) {
    var div = document.getElementById("div_" + id);
    if (!div) {
        var input = document.getElementById(id);
        if (input) div = input.closest(".form_group") || input.closest(".form_group_interno");
    }
    if (!div) return;

    var span = div.querySelector(".errore_campo");
    if (span) span.textContent = "";
    var inp = div.querySelector(".form_input");
    if (inp) inp.classList.remove("input_errore");
}

function validaCampo(id) {
    var input = document.getElementById(id);
    if (!input) return true;

    var valore = input.value.trim();
    var regola = REGOLE[id];

    if (!regola) return true;

    if (valore === "") {
        mostraErrore(id, "Questo campo è obbligatorio.");
        return false;
    }

    if (!regola.regex.test(valore)) {
        mostraErrore(id, regola.msg);
        return false;
    }

    rimuoviErrore(id);
    return true;
}

function validaConferma() {
    var password = document.getElementById("password").value;
    var conferma = document.getElementById("conferma").value;
    var div = document.getElementById("div_conferma");

    var span = div.querySelector(".errore_campo");
    if (!span) {
        span = document.createElement("span");
        span.className = "errore_campo";
        div.appendChild(span);
    }

    if (conferma === "") {
        span.textContent = "Questo campo è obbligatorio.";
        document.getElementById("conferma").classList.add("input_errore");
        return false;
    }

    if (password !== conferma) {
        span.textContent = "Le password non coincidono.";
        document.getElementById("conferma").classList.add("input_errore");
        return false;
    }

    span.textContent = "";
    document.getElementById("conferma").classList.remove("input_errore");
    return true;
}

var campi = Object.keys(REGOLE);
for (var i = 0; i < campi.length; i++) {
    (function(id) {
        var input = document.getElementById(id);
        if (input) {
            input.addEventListener("change", function() { validaCampo(id); });
            input.addEventListener("blur",   function() { validaCampo(id); });
        }
    })(campi[i]);
}

var confermaInput = document.getElementById("conferma");
if (confermaInput) {
    confermaInput.addEventListener("change", validaConferma);
    confermaInput.addEventListener("blur",   validaConferma);
}

document.getElementById("form").addEventListener("submit", function(e) {
    var valido = true;

    for (var i = 0; i < campi.length; i++) {
        if (!validaCampo(campi[i])) valido = false;
    }

    if (!validaConferma()) valido = false;

    if (!valido) e.preventDefault();
});