<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header.jsp" %>
<%@ page import="model.Prodotto" %>
<%
    Prodotto p = (Prodotto) request.getAttribute("prodotto");
    if (p == null) {
        response.sendRedirect("catalogo");
        return;
    }
%>
<div style="max-width: 600px; margin: 0 auto; background: white; padding: 2rem; border-radius: 12px;">
    <img src="<%= p.getImmagineUrl() %>" alt="<%= p.getNome() %>" style="width:100%; border-radius: 8px;">
    <h1><%= p.getNome() %></h1>
    <p><strong>Piattaforma:</strong> <%= p.getPiattaforma() %></p>
    <p><strong>Prezzo:</strong> &euro; <%= String.format("%.2f", p.getPrezzo()) %></p>
    <a href="carrello?action=add&id=<%= p.getId() %>" class="btn">🛒 Aggiungi al carrello</a>
    <br><br>
    <a href="catalogo">← Torna al catalogo</a>
</div>
<%@ include file="footer.jsp" %>