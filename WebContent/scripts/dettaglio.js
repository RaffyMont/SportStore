function init(){
	const meno = document.getElementById("meno");
	if(meno)
		meno.disabled = true;
};

"use strict";
 
const CTX = (function () {
    const parts = window.location.pathname.split("/");
    return parts.length > 1 ? "/" + parts[1] : "";
}());

function aggiungiAlCarrelloConQuantita(idProdotto, btnEl) {
    if (!idProdotto) return;
 
    const quantita = document.getElementById("input_quantita")
                   ? parseInt(document.getElementById("input_quantita").value) : 1;
 
    const coloreEl  = document.querySelector(".tag_colore.sel");
    const tagliaEl  = document.querySelector(".box_taglia.sel");
    const colore    = coloreEl  ? coloreEl.dataset.valore  : "";
    const taglia    = tagliaEl  ? tagliaEl.dataset.valore  : "";
 
    if (btnEl) {
        btnEl.disabled    = true;
        btnEl.textContent = "…";
    }
 
    const params = new URLSearchParams();
    params.append("id_prodotto", idProdotto);
    params.append("quantita",    quantita);
    if (colore) params.append("colore", colore);
    if (taglia) params.append("taglia", taglia);
 
    fetch(CTX + "/AggiungiAlCarrello", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded",
            "X-Requested-With": "XMLHttpRequest"
        },
        body: params.toString()
    })
    .then(r => {
        if (!r.ok) throw new Error("HTTP " + r.status);
        return r.json();
    })
    .then(data => {
        if (data.success) {
            aggiornaBadgeCarrello(data.totaleArticoli);
            showToast(quantita + " articolo/i aggiunto/i al carrello!", "ok");
            const input = document.getElementById("input_quantita");
            if (input) {
                input.value = 1;
                document.getElementById("meno").disabled = true;
                document.getElementById("piu").disabled  = false;
            }
        } else {
            if (data.redirectLogin) {
                window.location.href = CTX + "/Login";
            } else {
                showToast(data.message || "Errore durante l'aggiunta.", "err");
            }
        }
    })
    .catch(() => showToast("Errore di rete. Riprova.", "err"))
    .finally(() => {
        if (btnEl) {
            btnEl.disabled    = false;
            btnEl.textContent = "Aggiungi al carrello";
        }
    });
}
 
document.querySelectorAll(".box_taglia").forEach(function(el) {
    el.addEventListener("click", function() {
        document.querySelectorAll(".box_taglia").forEach(t => t.classList.remove("sel"));
        this.classList.add("sel");
    });
});
 
document.querySelectorAll(".tag_colore").forEach(function(el) {
    el.addEventListener("click", function() {
        document.querySelectorAll(".tag_colore").forEach(c => c.classList.remove("sel"));
        this.classList.add("sel");
    });
});

function cambiaQuantita(valore, stock)
{
    const input = document.getElementById("input_quantita");
    const meno = document.getElementById("meno");
    const piu = document.getElementById("piu");

    let val = parseInt(input.value);

    val += valore;

    if(val < 1)
        val = 1;

    if(val > stock)
        val = stock;

    input.value = val;

    meno.disabled = (val <= 1);
    piu.disabled = (val >= stock);
}