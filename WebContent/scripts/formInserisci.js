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

function validaModello() {
    const v = document.getElementById("modello").value.trim();
    if (!v)           { mostraErrore("g_modello", "Il modello è obbligatorio.");             return false; }
    if (v.length < 2) { mostraErrore("g_modello", "Il modello deve avere almeno 2 caratteri."); return false; }
    rimuoviErrore("g_modello");
    return true;
}

function validaMarca() {
    const v = document.getElementById("marca").value.trim();
    if (!v)           { mostraErrore("g_marca", "La marca è obbligatoria.");             return false; }
    if (v.length < 2) { mostraErrore("g_marca", "La marca deve avere almeno 2 caratteri."); return false; }
    rimuoviErrore("g_marca");
    return true;
}

function validaPrezzo() {
    const v = document.getElementById("prezzo").value.trim();
    if (!v)              { mostraErrore("g_prezzo", "Il prezzo è obbligatorio.");      return false; }
    if (isNaN(v) || parseFloat(v) <= 0)
                         { mostraErrore("g_prezzo", "Il prezzo deve essere maggiore di 0."); return false; }
    rimuoviErrore("g_prezzo");
    return true;
}

function validaStock() {
    const v = document.getElementById("stock").value.trim();
    if (v === "")              { mostraErrore("g_stock", "Lo stock è obbligatorio.");        return false; }
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

document.getElementById("modello")   .addEventListener("change", validaModello);
document.getElementById("marca")     .addEventListener("change", validaMarca);
document.getElementById("prezzo")    .addEventListener("change", validaPrezzo);
document.getElementById("stock")     .addEventListener("change", validaStock);
document.getElementById("categoria") .addEventListener("change", validaCategoria);
document.getElementById("genere")    .addEventListener("change", validaGenere);

document.getElementById("formInserisci").addEventListener("submit", function(e) {

    const risultati = [
        validaModello(),
        validaMarca(),
        validaPrezzo(),
        validaStock(),
        validaCategoria(),
        validaGenere()
    ];

    if (risultati.includes(false)) {
        e.preventDefault();

        const primo = document.querySelector(".has-error");
        if (primo) primo.scrollIntoView({ behavior: "smooth", block: "center" });
    }
});