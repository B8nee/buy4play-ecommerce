<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header.jsp" %>

<!--
    Pagina per il cambio password dell'utente autenticato.
    Presenta un form con tre campi: password attuale, nuova password e conferma.
    I messaggi di errore o successo vengono visualizzati tramite attributi della request.
-->

<div class="password-wrapper">
    <div class="password-box">
        <h2>🔐 Cambio password</h2>

        <!-- Visualizza eventuali messaggi di errore o successo -->
        <% if (request.getAttribute("errore") != null) { %>
            <div class="alert error"><%= request.getAttribute("errore") %></div>
        <% } %>
        <% if (request.getAttribute("messaggio") != null) { %>
            <div class="alert success"><%= request.getAttribute("messaggio") %></div>
        <% } %>

        <!-- Form di cambio password, invia POST alla servlet "/cambioPassword" -->
        <form action="cambioPassword" method="post">
            <div class="input-group">
                <label for="oldPassword">Password attuale</label>
                <input type="password" id="oldPassword" name="oldPassword" required autofocus>
            </div>

            <div class="input-group">
                <label for="newPassword">Nuova password</label>
                <input type="password" id="newPassword" name="newPassword" required>
            </div>

            <div class="input-group">
                <label for="confirmNewPassword">Conferma nuova password</label>
                <input type="password" id="confirmNewPassword" name="confirmNewPassword" required>
            </div>

            <button type="submit" class="btn btn-change">Cambia password</button>
        </form>

        <!-- Link per tornare alla pagina del profilo -->
        <div class="back-link">
            <a href="profilo">← Torna al profilo</a>
        </div>
    </div>
</div>

<style>
    /* Stili per centrare e dare un look moderno al form di cambio password */
    .password-wrapper {
        display: flex;
        justify-content: center;
        align-items: center;
        min-height: 70vh;
        padding: 2rem 1rem;
    }
    .password-box {
        background: rgba(18, 22, 28, 0.85);
        backdrop-filter: blur(10px);
        border-radius: 32px;
        padding: 2rem 2rem 2.5rem;
        width: 100%;
        max-width: 500px;
        border: 1px solid rgba(46, 213, 115, 0.3);
        box-shadow: 0 20px 35px -10px rgba(0,0,0,0.4);
    }
    .password-box h2 {
        text-align: center;
        margin-bottom: 1.8rem;
        font-weight: 600;
        color: #fff;
    }
    .input-group {
        margin-bottom: 1.5rem;
    }
    .input-group label {
        display: block;
        margin-bottom: 0.5rem;
        font-weight: 500;
        color: #b9c7d9;
    }
    .input-group input {
        width: 100%;
        padding: 0.85rem 1rem;
        background: #0a0c10;
        border: 1px solid #2c3e3a;
        border-radius: 20px;
        color: #fff;
        font-size: 1rem;
        transition: all 0.2s;
    }
    .input-group input:focus {
        outline: none;
        border-color: #2ed573;
        box-shadow: 0 0 0 2px rgba(46, 213, 115, 0.2);
    }
    .btn-change {
        display: block;
        width: fit-content;
        margin: 1.5rem auto 0;
        font-size: 1rem;
        padding: 0.75rem 1.5rem;
    }
    .back-link {
        text-align: center;
        margin-top: 1.5rem;
    }
    .back-link a {
        color: #b9c7d9;
        text-decoration: none;
        font-size: 0.9rem;
    }
    .back-link a:hover {
        color: #2ed573;
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
    .alert.success {
        background: rgba(46, 213, 115, 0.15);
        color: #2ed573;
        border-left: 3px solid #2ed573;
    }
</style>

<%@ include file="footer.jsp" %>