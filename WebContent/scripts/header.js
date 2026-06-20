function toggleMenu() {
    const menu = document.getElementById('userMenu');
    menu.classList.toggle('open');
}

document.addEventListener('click', function(e) {
    const menu = document.getElementById('userMenu');
    if (menu && !menu.contains(e.target)) {
        menu.classList.remove('open');
    }
})