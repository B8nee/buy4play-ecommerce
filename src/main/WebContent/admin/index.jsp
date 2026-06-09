<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.Utente, model.ProdottoDAO, model.OrdineDAO, model.UtenteDAO, java.util.*" %>

<%
    // Verifica che l'utente sia autenticato e abbia ruolo admin
    Utente admin = (Utente) session.getAttribute("utente");
    if (admin == null || !"admin".equals(admin.getRuolo())) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    // Recupera le statistiche per la dashboard
    ProdottoDAO prodottoDAO = new ProdottoDAO();
    OrdineDAO ordineDAO = new OrdineDAO();
    UtenteDAO utenteDAO = new UtenteDAO();
    int totalProdotti = prodottoDAO.countProdotti();   // Numero di prodotti nel catalogo
    int totalOrdini = ordineDAO.countOrdini();         // Numero totale di ordini
    int totalUtenti = utenteDAO.countUtenti();         // Numero di utenti registrati
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Dashboard Admin - Buy4Play</title>
        <link rel="icon" type="image/x-icon" href="<%= request.getContextPath() %>/favicon.ico">
        <style>
            /* Stili per le card delle statistiche e della dashboard */
            .dashboard-stats {
                display: flex;
                gap: 2rem;
                margin: 2rem 0;
                flex-wrap: wrap;
            }
            .stat-card {
                background: linear-gradient(135deg, #1a1f2a, #0f1117);
                border-radius: 28px;
                padding: 1.5rem;
                flex: 1;
                min-width: 180px;
                border: 1px solid rgba(46, 213, 115, 0.3);
                text-align: center;
                transition: transform 0.2s;
            }
            .stat-card:hover {
                transform: translateY(-5px);
                border-color: #2ed573;
            }
            .stat-number {
                font-size: 2.5rem;
                font-weight: 700;
                color: #2ed573;
                margin: 0.5rem 0;
            }
            .stat-label {
                color: #b9c7d9;
                font-weight: 500;
            }
            .dashboard-cards {
                display: flex;
                gap: 2rem;
                margin: 2rem 0;
                flex-wrap: wrap;
            }
            .admin-card {
                background: rgba(18, 22, 28, 0.8);
                backdrop-filter: blur(8px);
                border-radius: 28px;
                padding: 2rem;
                flex: 1;
                min-width: 220px;
                text-align: center;
                border: 1px solid rgba(46, 213, 115, 0.2);
                transition: all 0.2s;
            }
            .admin-card:hover {
                border-color: #2ed573;
                transform: translateY(-4px);
                box-shadow: 0 20px 30px -12px rgba(46, 213, 115, 0.2);
            }
            .admin-card h3 {
                font-size: 1.5rem;
                margin-bottom: 1rem;
                color: white;
            }
            .admin-card .btn {
                margin-top: 1rem;
                display: inline-block;
            }
            .welcome-banner {
                background: linear-gradient(95deg, #2ed57310, #1e9b5210);
                border-left: 4px solid #2ed573;
                padding: 1rem 2rem;
                border-radius: 20px;
                margin-bottom: 2rem;
            }
        </style>
    </head>
    <body>
    <%@ include file="../header.jsp" %>

    <!-- Banner di benvenuto -->
    <div class="welcome-banner">
        <h1>🎛️ Area Amministrazione</h1>
        <p>Benvenuto, <strong><%= admin.getNome() %> <%= admin.getCognome() %></strong> (ruolo: <span style="color:#2ed573;">admin</span>)</p>
    </div>

    <!-- Card riassuntive delle statistiche -->
    <div class="dashboard-stats">
        <div class="stat-card">
            <div class="stat-label">📦 Prodotti in catalogo</div>
            <div class="stat-number"><%= totalProdotti %></div>
        </div>
        <div class="stat-card">
            <div class="stat-label">📋 Ordini totali</div>
            <div class="stat-number"><%= totalOrdini %></div>
        </div>
        <div class="stat-card">
            <div class="stat-label">👥 Utenti registrati</div>
            <div class="stat-number"><%= totalUtenti %></div>
        </div>
    </div>

    <!-- Card di navigazione alle sezioni di gestione -->
    <div class="dashboard-cards">
        <div class="admin-card">
            <h3>📦 Gestione Prodotti</h3>
            <p>Aggiungi, modifica o elimina prodotti dal catalogo</p>
            <a href="<%= request.getContextPath() %>/admin/GestioneProdotti" class="btn">Vai alla gestione</a>
        </div>
        <div class="admin-card">
            <h3>📋 Gestione Ordini</h3>
            <p>Visualizza e filtra tutti gli ordini dei clienti</p>
            <a href="<%= request.getContextPath() %>/admin/VisualizzaOrdini" class="btn">Vai agli ordini</a>
        </div>
        <div class="admin-card">
            <h3>👥 Gestione Utenti</h3>
            <p>Modifica ruoli ed elimina utenti</p>
            <a href="<%= request.getContextPath() %>/admin/gestioneUtenti" class="btn">Gestisci utenti</a>
        </div>
    </div>

    <!-- Link per tornare al sito pubblico e logout -->
    <div style="margin-top: 2rem;">
        <a href="<%= request.getContextPath() %>/catalogo" class="btn-outline" style="display: inline-block; padding: 0.6rem 1.2rem;">← Torna al sito</a>
        <a href="<%= request.getContextPath() %>/logout" class="btn" style="margin-left: 1rem;">Logout</a>
    </div>

    <%@ include file="../footer.jsp" %>
    </body>
</html>