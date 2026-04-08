// --- Lógica do Olho da Senha (Login) ---
const togglePassword = document.querySelector('#togglePassword');
const password = document.querySelector('#password');

if (togglePassword && password) {
    togglePassword.addEventListener('click', function () {
        if (password.type === 'password') {
            password.type = 'text';
            this.textContent = 'visibility';
        } else {
            password.type = 'password';
            this.textContent = 'visibility_off';
        }
    });
}

// --- Lógica das Notificações (Painel) ---
document.addEventListener('DOMContentLoaded', () => {
    const btnNotif = document.getElementById('btnNotif');
    const modal = document.getElementById('modalNotif');
    const closeBtn = document.getElementById('closeNotif');

    // Abre o modal ao clicar no sino
    if (btnNotif && modal) {
        btnNotif.onclick = function() {
            modal.style.display = "block";
        }
    }

    // Fecha no 'X'
    if (closeBtn) {
        closeBtn.onclick = function() {
            modal.style.display = "none";
        }
    }

    // Fecha se clicar fora da caixa branca
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
});