const EMAIL_REGEX = /^[a-zA-Z0-9._%+\-]+@[a-zA-Z0-9.\-]+\.[a-zA-Z]{2,}$/;
const PWD_MIN     = 2;  
 
const form        = document.getElementById("loginForm");
const emailInput  = document.getElementById("email");
const pwdInput    = document.getElementById("password");
const btnLogin    = document.getElementById("btnLogin");
const togglePwd   = document.getElementById("togglePwd");
const eyeIcon     = document.getElementById("eyeIcon");
 
function setError(groupId, errId, msg) {
    const group = document.getElementById(groupId);
    const span  = document.getElementById(errId);
    if (!group || !span) return;
    span.textContent = msg;
    group.classList.remove("is-valid");
    group.classList.add("has-error");
}
 
function clearError(groupId, errId) {
    const group = document.getElementById(groupId);
    const span  = document.getElementById(errId);
    if (!group || !span) return;
    span.textContent = "";
    group.classList.remove("has-error");
    group.classList.add("is-valid");
}
 
function resetField(groupId, errId) {
    const group = document.getElementById(groupId);
    const span  = document.getElementById(errId);
    if (!group || !span) return;
    span.textContent = "";
    group.classList.remove("has-error", "is-valid");
}
 
function validateEmail() {
    const val = emailInput.value.trim();
    if (val === "") {
        setError("group-email", "err-email", "L'email è obbligatoria.");
        return false;
    }
    if (!EMAIL_REGEX.test(val)) {
        setError("group-email", "err-email", "Inserisci un indirizzo email valido (es. nome@dominio.it).");
        return false;
    }
    clearError("group-email", "err-email");
    return true;
}
 
function validatePassword() {
    const val = pwdInput.value;
    if (val === "") {
        setError("group-password", "err-password", "La password è obbligatoria.");
        return false;
    }
    if (val.length < PWD_MIN) {
        setError("group-password", "err-password",
            "La password deve contenere almeno " + PWD_MIN + " caratteri.");
        return false;
    }
    clearError("group-password", "err-password");
    return true;
}
 
emailInput.addEventListener("change", validateEmail);
pwdInput.addEventListener("change",   validatePassword);
 
emailInput.addEventListener("input", function () {
    if (this.value === "") resetField("group-email", "err-email");
});
pwdInput.addEventListener("input", function () {
    if (this.value === "") resetField("group-password", "err-password");
});
 
form.addEventListener("submit", function (e) {
    const okEmail = validateEmail();
    const okPwd   = validatePassword();
 
    if (!okEmail || !okPwd) {
        e.preventDefault();
        // Focus sul primo campo invalido
        if (!okEmail) emailInput.focus();
        else           pwdInput.focus();
        return;
    }
 
    btnLogin.classList.add("loading");
    btnLogin.disabled = true;
});

togglePwd.addEventListener("click", function () {
    const isPassword = pwdInput.type === "password";
    pwdInput.type    = isPassword ? "text" : "password";
    this.setAttribute("aria-pressed",  String(isPassword));
    this.setAttribute("aria-label",    isPassword ? "Nascondi password" : "Mostra password");
 
    eyeIcon.innerHTML = isPassword
        ? 
          '<path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94"/>' +
          '<path d="M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19"/>' +
          '<line x1="1" y1="1" x2="23" y2="23"/>'
        : 
          '<path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/>' +
          '<circle cx="12" cy="12" r="3"/>';
});
 
window.addEventListener("pageshow", function () {
    btnLogin.classList.remove("loading");
    btnLogin.disabled = false;
});