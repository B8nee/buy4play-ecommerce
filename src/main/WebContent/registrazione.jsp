<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header.jsp" %>

<div class="register-wrapper">
    <div class="register-box">
        <h2>📝 Registrazione</h2>

        <% if (request.getAttribute("errore") != null) { %>
            <div class="alert error"><%= request.getAttribute("errore") %></div>
        <% } %>

        <form action="registrazione" method="post" onsubmit="return validateForm()">
            <div class="form-row">
                <div class="form-group">
                    <label for="nome">Nome *</label>
                    <input type="text" id="nome" name="nome" required>
                </div>
                <div class="form-group">
                    <label for="cognome">Cognome *</label>
                    <input type="text" id="cognome" name="cognome" required>
                </div>
            </div>

            <div class="form-group">
                <label for="email">Email *</label>
                <input type="email" id="email" name="email" required>
                <span id="email-status" class="field-status"></span>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="password">Password *</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <div class="form-group">
                    <label for="conferma">Conferma password *</label>
                    <input type="password" id="conferma" name="conferma" required>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="indirizzo">Indirizzo</label>
                    <input type="text" id="indirizzo" name="indirizzo">
                </div>
                <div class="form-group">
                    <label for="citta">Città</label>
                    <input type="text" id="citta" name="citta">
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="provincia">Provincia (2 lettere)</label>
                    <input type="text" id="provincia" name="provincia" maxlength="2" pattern="[A-Za-z]{2}">
                </div>
                <div class="form-group">
                    <label for="cap">CAP (5 cifre)</label>
                    <input type="text" id="cap" name="cap" maxlength="5" pattern="\d*">
                </div>
            </div>

            <button type="submit" class="btn btn-register">Registrati</button>
        </form>

        <div class="login-link">
            Hai già un account? <a href="login.jsp">Accedi qui</a>
        </div>
    </div>
</div>

<style>
    .register-wrapper {
        display: flex;
        justify-content: center;
        align-items: center;
        min-height: 70vh;
        padding: 2rem 1rem;
    }
    .register-box {
        background: rgba(18, 22, 28, 0.85);
        backdrop-filter: blur(10px);
        border-radius: 32px;
        padding: 2rem 2rem 2.5rem;
        width: 100%;
        max-width: 800px;
        border: 1px solid rgba(46, 213, 115, 0.3);
        box-shadow: 0 20px 35px -10px rgba(0,0,0,0.4);
    }
    .register-box h2 {
        text-align: center;
        margin-bottom: 1.8rem;
        font-weight: 600;
        color: #fff;
    }
    .form-row {
        display: flex;
        gap: 1.5rem;
        flex-wrap: wrap;
    }
    .form-group {
        flex: 1;
        min-width: 180px;
        margin-bottom: 1.2rem;
    }
    .form-group label {
        display: block;
        margin-bottom: 0.4rem;
        font-weight: 500;
        color: #b9c7d9;
        font-size: 0.9rem;
    }
    .form-group input {
        width: 100%;
        padding: 0.75rem 1rem;
        background: #0a0c10;
        border: 1px solid #2c3e3a;
        border-radius: 20px;
        color: #fff;
        font-size: 0.95rem;
        transition: all 0.2s;
    }
    .form-group input:focus {
        outline: none;
        border-color: #2ed573;
        box-shadow: 0 0 0 2px rgba(46, 213, 115, 0.2);
    }
    .field-status {
        font-size: 0.75rem;
        display: inline-block;
        margin-top: 0.3rem;
    }
    .btn-register {
    display: block;
    margin: 0 auto;
    width: fit-content;
    min-width: 180px;
    text-align: center;
}
    .login-link {
        text-align: center;
        margin-top: 1.8rem;
        color: #8e9aaf;
    }
    .login-link a {
        color: #2ed573;
        text-decoration: none;
        font-weight: 500;
        transition: color 0.2s;
    }
    .login-link a:hover {
        color: #3ee684;
        text-decoration: underline;
    }
    .alert {
        padding: 0.8rem 1rem;
        border-radius: 24px;
        margin-bottom: 1.2rem;
        text-align: center;
        font-weight: 500;
    }
    .alert.error {
        background: rgba(233, 69, 96, 0.15);
        color: #ff6b6b;
        border-left: 3px solid #e94560;
    }
    @media (max-width: 640px) {
        .register-box {
            padding: 1.5rem;
        }
        .form-row {
            flex-direction: column;
            gap: 0;
        }
    }
</style>

<script>
    function validateForm() {
        const password = document.getElementById('password').value;
        const conferma = document.getElementById('conferma').value;
        if (password !== conferma) {
            alert('Le password non coincidono');
            return false;
        }
        const email = document.getElementById('email').value;
        const emailRegex = /^[^\s@]+@([^\s@]+\.)+[^\s@]+$/;
        if (!emailRegex.test(email)) {
            alert('Inserisci un indirizzo email valido');
            return false;
        }
        const cap = document.getElementById('cap').value;
        if (cap && !/^\d{5}$/.test(cap)) {
            alert('Il CAP deve essere di 5 cifre');
            return false;
        }
        const provincia = document.getElementById('provincia').value;
        if (provincia && !/^[A-Za-z]{2}$/.test(provincia)) {
            alert('La provincia deve essere di 2 lettere (es. SA, NA)');
            return false;
        }
        return true;
    }

    const emailInput = document.getElementById('email');
    const emailStatus = document.getElementById('email-status');
    if (emailInput) {
        emailInput.addEventListener('blur', function() {
            const email = this.value.trim();
            if (email.length < 5) {
                emailStatus.innerHTML = '';
                return;
            }
            fetch('<%= request.getContextPath() %>/CheckEmail?email=' + encodeURIComponent(email))
                .then(response => response.json())
                .then(data => {
                    if (data.exists) {
                        emailStatus.innerHTML = '<span style="color:#e94560;">✗ Email già registrata</span>';
                    } else {
                        emailStatus.innerHTML = '<span style="color:#2ed573;">✓ Email disponibile</span>';
                    }
                })
                .catch(err => console.error(err));
        });
    }
</script>

<%@ include file="footer.jsp" %>