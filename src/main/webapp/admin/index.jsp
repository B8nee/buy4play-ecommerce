<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ page import="model.Utente, model.ProdottoDAO, model.OrdineDAO, java.util.*" %>
        <% Utente admin=(Utente) session.getAttribute("utente"); if (admin==null || !"admin".equals(admin.getRuolo())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp" ); return; } %>
            <!DOCTYPE html>
            <html>

            <head>
                <meta charset="UTF-8">
                <title>Area Admin - Buy4Play</title>
                <style>
                    body {
                        font-family: Arial;
                        margin: 20px;
                    }

                    .dashboard {
                        display: flex;
                        gap: 20px;
                        margin-top: 20px;
                    }

                    .card {
                        border: 1px solid #ccc;
                        padding: 20px;
                        border-radius: 8px;
                        width: 200px;
                        text-align: center;
                    }

                    .card a {
                        text-decoration: none;
                        font-weight: bold;
                    }
                </style>
            </head>

            <body>
                <h1>Area Amministrazione - Benvenuto, <%= admin.getNome() %>
                </h1>
                <div class="dashboard">
                    <div class="card">
                        <h3>📦 Prodotti</h3>
                        <a href="gestioneProdotti.jsp">Gestisci catalogo</a>
                    </div>
                    <div class="card">
                        <h3>📋 Ordini</h3>
                        <a href="visualizzaOrdini.jsp">Visualizza tutti gli ordini</a>
                    </div>
                    <div class="card">
                        <h3>👥 Utenti</h3>
                        <a href="gestioneUtenti">Gestisci utenti</a>
                    </div>
                </div>
                <p><a href="<%= request.getContextPath() %>/catalogo">← Torna al sito</a> | <a
                        href="<%= request.getContextPath() %>/logout">Logout</a></p>
            </body>

            </html>