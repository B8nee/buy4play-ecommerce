<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Prodotto" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dettaglio prodotto</title>
    <style>
        body { font-family: Arial; margin: 20px; }
        .card { max-width: 500px; margin: auto; border: 1px solid #ccc; padding: 20px; border-radius: 10px; }
        img { max-width: 100%; }
        .prezzo { font-size: 1.5em; color: #e94560; }
        button { background: #e94560; color: white; padding: 10px; border: none; border-radius: 5px; cursor: pointer; }
    </style>
</head>
<body>
    <%
        Prodotto p = (Prodotto) request.getAttribute("prodotto");
        if (p == null) {
            response.sendRedirect("catalogo");
            return;
        }
    %>
    <div class="card">
        <img src="<%= p.getImmagineUrl() %>" alt="<%= p.getNome() %>">
        <h1><%= p.getNome() %></h1>
        <p><strong>Piattaforma:</strong> <%= p.getPiattaforma() %></p>
        <p class="prezzo">€ <%= String.format("%.2f", p.getPrezzo()) %></p>
        <form action="carrello" method="get">
            <input type="hidden" name="action" value="add">
            <input type="hidden" name="id" value="<%= p.getId() %>">
            <button type="submit">🛒 Aggiungi al carrello</button>
        </form>
        <br><br>
        <a href="catalogo">← Torna al catalogo</a>
    </div>
</body>
</html>
