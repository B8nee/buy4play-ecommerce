<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, model.Ordine, model.Utente, java.text.SimpleDateFormat" %>
<%
    model.Utente admin = (model.Utente) session.getAttribute("utente");
    if (admin == null || !"admin".equals(admin.getRuolo())) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestione Ordini - Buy4Play Admin</title>
    <style>
        .admin-container {
            background: rgba(18, 22, 28, 0.7);
            border-radius: 32px;
            padding: 2rem;
            margin: 2rem 0;
        }
        .filters {
            display: flex;
            gap: 1rem;
            margin-bottom: 2rem;
            flex-wrap: wrap;
            align-items: flex-end;
        }
        .filter-group {
            display: flex;
            flex-direction: column;
            gap: 0.3rem;
        }
        .filter-group label {
            font-size: 0.8rem;
            color: #b9c7d9;
        }
        .filter-group input, .filter-group select {
            background: #0a0c10;
            border: 1px solid #2ed573;
            padding: 0.5rem;
            border-radius: 20px;
            color: white;
        }
        .orders-table {
            width: 100%;
            background: #0a0c10;
            border-radius: 20px;
            overflow-x: auto;
        }
        .orders-table th, .orders-table td {
            padding: 1rem;
            text-align: left;
            border-bottom: 1px solid #2c3e3a;
        }
        .orders-table th {
            background: #1e232c;
            color: #2ed573;
        }
        .btn-detail {
            background: #2ed573;
            padding: 0.2rem 1rem;
            border-radius: 20px;
            color: #0a0c10;
            text-decoration: none;
        }
    </style>
</head>
<body>
<%@ include file="../header.jsp" %>

<div class="admin-container">
    <h2 class="admin-title">📋 Gestione Ordini</h2>

    <form method="get" action="VisualizzaOrdini" class="filters">
        <div class="filter-group">
            <label>Data da:</label>
            <input type="date" name="dataDa" value="<%= request.getParameter("dataDa") != null ? request.getParameter("dataDa") : "" %>">
        </div>
        <div class="filter-group">
            <label>Data a:</label>
            <input type="date" name="dataA" value="<%= request.getParameter("dataA") != null ? request.getParameter("dataA") : "" %>">
        </div>
        <div class="filter-group">
            <label>Cliente (email):</label>
            <input type="text" name="clienteEmail" placeholder="email cliente" value="<%= request.getParameter("clienteEmail") != null ? request.getParameter("clienteEmail") : "" %>">
        </div>
        <div class="filter-group">
            <button type="submit" class="btn">Filtra</button>
            <a href="VisualizzaOrdini" class="btn-outline" style="display: inline-block; padding: 0.5rem 1rem;">Reset</a>
        </div>
    </form>

    <div style="overflow-x: auto;">
        <table class="orders-table">
            <thead>
                <tr><th>ID Ordine</th><th>Cliente</th><th>Email</th><th>Data</th><th>Totale</th><th>Dettagli</th></tr>
            </thead>
            <tbody>
                <%
                    List<Ordine> ordini = (List<Ordine>) request.getAttribute("ordini");
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    if (ordini != null && !ordini.isEmpty()) {
                        for (Ordine o : ordini) {
                %>
                <tr>
                    <td><%= o.getId() %></td>
                    <td><%= o.getUtente().getNome() + " " + o.getUtente().getCognome() %></td>
                    <td><%= o.getUtente().getEmail() %></td>
                    <td><%= sdf.format(o.getDataOrdine()) %></td>
                    <td>&euro; <%= String.format("%.2f", o.getTotale()) %></td>
                    <td><a href="DettaglioOrdineAdmin?id=<%= o.getId() %>" class="btn-detail">Dettagli</a></td>
                </tr>
                <%      }
                    } else { %>
                <tr><td colspan="6">Nessun ordine trovato con i filtri selezionati.</td></tr>
                <% } %>
            </tbody>
        </table>
    </div>
</div>

<%@ include file="../footer.jsp" %>
</body>
</html>