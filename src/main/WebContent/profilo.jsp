<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header.jsp" %>
<%@ page import="model.Utente" %>
<%
    Utente u = (Utente) request.getAttribute("utente");
    if (u == null) {
        response.sendRedirect("catalogo");
        return;
    }
%>

<div class="profile-wrapper">
    <div class="profile-box">
        <h2>👤 Il mio profilo</h2>

        <% if (request.getAttribute("messaggio") != null) { %>
            <div class="alert success"><%= request.getAttribute("messaggio") %></div>
        <% } %>
        <% if (request.getAttribute("errore") != null) { %>
            <div class="alert error"><%= request.getAttribute("errore") %></div>
        <% } %>

        <form action="profilo" method="post">
            <div class="form-group">
                <label for="nome">Nome *</label>
                <input type="text" id="nome" name="nome" value="<%= u.getNome() != null ? u.getNome() : "" %>" required>
            </div>
            <div class="form-group">
                <label for="cognome">Cognome *</label>
                <input type="text" id="cognome" name="cognome" value="<%= u.getCognome() != null ? u.getCognome() : "" %>" required>
            </div>
            <div class="form-group">
                <label for="indirizzo">Indirizzo</label>
                <input type="text" id="indirizzo" name="indirizzo" value="<%= u.getIndirizzo() != null ? u.getIndirizzo() : "" %>">
            </div>
            <div class="form-group">
                <label for="citta">Città</label>
                <input type="text" id="citta" name="citta" value="<%= u.getCitta() != null ? u.getCitta() : "" %>">
            </div>
            <div class="form-row">
                <div class="form-group half">
                    <label for="provincia">Provincia (2 lettere)</label>
                    <input type="text" id="provincia" name="provincia" value="<%= u.getProvincia() != null ? u.getProvincia() : "" %>" maxlength="2">
                </div>
                <div class="form-group half">
                    <label for="cap">CAP (5 cifre)</label>
                    <input type="text" id="cap" name="cap" value="<%= u.getCap() != null ? u.getCap() : "" %>" maxlength="5">
                </div>
            </div>
            <button type="submit" class="btn btn-update">Aggiorna profilo</button>
        </form>

        <div class="password-section">
            <h3>🔐 Modifica password</h3>
            <a href="cambioPassword.jsp" class="btn">Cambia password</a>
        </div>

        <div class="back-link">
            <a href="catalogo">← Torna al catalogo</a>
        </div>
    </div>
</div>

<style>
    .profile-wrapper {
        display: flex;
        justify-content: center;
        align-items: center;
        min-height: 70vh;
        padding: 2rem 1rem;
    }
    .profile-box {
        background: rgba(18, 22, 28, 0.85);
        backdrop-filter: blur(10px);
        border-radius: 32px;
        padding: 2rem 2rem 2.5rem;
        width: 100%;
        max-width: 600px;
        border: 1px solid rgba(46, 213, 115, 0.3);
        box-shadow: 0 20px 35px -10px rgba(0,0,0,0.4);
    }
    .profile-box h2 {
        text-align: center;
        margin-bottom: 1.8rem;
        font-weight: 600;
        color: #fff;
    }
    .form-group {
        margin-bottom: 1.2rem;
    }
    .form-group label {
        display: block;
        margin-bottom: 0.5rem;
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
    .form-row {
        display: flex;
        gap: 1rem;
        flex-wrap: wrap;
    }
    .form-group.half {
        flex: 1;
        min-width: 130px;
    }
    .btn-update {
        display: block;
        width: fit-content;
        margin: 1.5rem auto 0.5rem;
        font-size: 1rem;
        padding: 0.75rem 1.5rem;
    }
    .password-section {
        text-align: center;
        margin-top: 2rem;
        padding-top: 1.5rem;
        border-top: 1px solid rgba(46, 213, 115, 0.2);
    }
    .password-section h3 {
        margin-bottom: 1rem;
        font-size: 1.2rem;
        color: #eef2ff;
    }
    .btn-outline {
        background: transparent;
        border: 1px solid #2ed573;
        color: #2ed573;
        padding: 0.6rem 1.2rem;
        border-radius: 40px;
        text-decoration: none;
        font-weight: 500;
        transition: all 0.2s;
        display: inline-block;
    }
    .btn-outline:hover {
        background: #2ed573;
        color: #0a0c10;
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
    @media (max-width: 500px) {
        .profile-box {
            padding: 1.5rem;
        }
        .form-row {
            flex-direction: column;
            gap: 0;
        }
    }
</style>

<%@ include file="footer.jsp" %>