const toastEl = document.getElementById("toast");
let toastTimer = null;

function showToast(msg, tipo) {
    if (!toastEl) return;
    toastEl.textContent = msg;
    toastEl.style.borderLeftColor = tipo === "err" ? "#e53935" : "#2e7d32";
    toastEl.classList.add("show");
    clearTimeout(toastTimer);
    toastTimer = setTimeout(() => toastEl.classList.remove("show"), 2500);
}

function aggiornaBadgeCarrello(n) {
    const badge = document.getElementById("cartBadge");
    if (!badge) return;
    badge.textContent = n;
    n > 0 ? badge.classList.remove("cart-badge--hidden")
           : badge.classList.add("cart-badge--hidden");
}

function aggiornaRiepilogo(totaleArticoli, totalePrezzo) {
    const numEl  = document.getElementById("riepilogo_num");
    const subEl  = document.getElementById("riepilogo_subtotale");
    const totEl  = document.getElementById("riepilogo_totale_val");

    if (numEl)  numEl.textContent  = totaleArticoli;

    const totNum = parseFloat(totalePrezzo);
    if (subEl)  subEl.textContent  = "€ " + totNum.toFixed(2).replace(".", ",");

    const spedizione = totNum >= 50 ? 0 : 4.99;
    if (totEl)  totEl.textContent  = "€ " + (totNum + spedizione).toFixed(2).replace(".", ",");

    const spedEl = document.querySelector(".spedizione_gratis");
    if (spedEl) spedEl.textContent = totNum >= 50 ? "Gratuita" : "€ 4,99";
}

function postCarrello(params) {
    return fetch(CTX + "/Carrello", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded",
            "X-Requested-With": "XMLHttpRequest"
        },
        body: new URLSearchParams(params).toString()
    }).then(r => {
        if (!r.ok) throw new Error("HTTP " + r.status);
        return r.json();
    });
}

function aggiornaQuantita(chiave, delta) {
    const safeId = chiave.replace(/_/g, "").replace(/ /g, "");
    const qtyEl  = document.getElementById("qty_" + safeId);
    if (!qtyEl) return;

    const nuovaQty = parseInt(qtyEl.textContent.trim()) + delta;

    postCarrello({ action: "aggiorna", chiave: chiave, quantita: nuovaQty })
    .then(data => {
        if (!data.success) { showToast("Errore aggiornamento.", "err"); return; }

        if (nuovaQty <= 0) {
            const itemEl = document.getElementById("item_" + safeId);
            if (itemEl) itemEl.remove();
            verificaCarrelloVuoto();
        } else {
            qtyEl.textContent = nuovaQty;
            const subEl = document.getElementById("sub_" + safeId);
            if (subEl) {
                const prezzo = parseFloat(subEl.closest(".carrello_item")
                    .querySelector(".item_prezzo_unit").textContent
                    .replace("€", "").replace(",", ".").trim());
                subEl.textContent = "€ " + (prezzo * nuovaQty).toFixed(2).replace(".", ",");
            }
        }

        aggiornaBadgeCarrello(data.totaleArticoli);
        aggiornaRiepilogo(data.totaleArticoli, data.totalePrezzo);
    })
    .catch(() => showToast("Errore di rete.", "err"));
}

function rimuoviArticolo(chiave) {
    postCarrello({ action: "rimuovi", chiave: chiave })
    .then(data => {
        if (!data.success) { showToast("Errore rimozione.", "err"); return; }

        const safeId = chiave.replace(/_/g, "").replace(/ /g, "");
        const itemEl = document.getElementById("item_" + safeId);
        if (itemEl) itemEl.remove();

        aggiornaBadgeCarrello(data.totaleArticoli);
        aggiornaRiepilogo(data.totaleArticoli, data.totalePrezzo);
        showToast("Articolo rimosso.", "ok");
        verificaCarrelloVuoto();
    })
    .catch(() => showToast("Errore di rete.", "err"));
}

function svuotaCarrello() {
    if (!confirm("Vuoi svuotare il carrello?")) return;

    postCarrello({ action: "svuota" })
    .then(data => {
        if (!data.success) { showToast("Errore.", "err"); return; }
        aggiornaBadgeCarrello(0);
        window.location.reload();
    })
    .catch(() => showToast("Errore di rete.", "err"));
}

function verificaCarrelloVuoto() {
    const items = document.querySelectorAll(".carrello_item");
    if (items.length === 0) {
        window.location.reload();
    }
}