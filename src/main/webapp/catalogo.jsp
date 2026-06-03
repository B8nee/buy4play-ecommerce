<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header.jsp" %>
<%@ page import="java.util.List, model.Prodotto" %>
<h2>🔥 Catalogo giochi</h2>
<div class="catalogo-grid" style="display: grid; grid-template-columns: repeat(auto-fill, minmax(280px,1fr)); gap: 2rem;">
<%
    List<Prodotto> prodotti = (List<Prodotto>) request.getAttribute("listaProdotti");
    if (prodotti != null && !prodotti.isEmpty()) {
        for (Prodotto p : prodotti) {
%>
    <div class="prodotto-card" style="background: white; border-radius: 12px; overflow: hidden; box-shadow: 0 4px 8px rgba(0,0,0,0.1);">
        <img src="<%= p.getImmagineUrl() %>" alt="<%= p.getNome() %>" style="width:100%; height:200px; object-fit:cover;">
        <div style="padding: 1rem;">
            <h3><a href="dettaglio?id=<%= p.getId() %>" style="text-decoration: none; color: #333;"><%= p.getNome() %></a></h3>
            <div style="color: #e94560;"><%= p.getPiattaforma() %></div>
            <div class="prezzo" style="font-size: 1.3rem; font-weight: bold;">&euro; <%= String.format("%.2f", p.getPrezzo()) %></div>
            <a href="carrello?action=add&id=<%= p.getId() %>" class="btn" style="display: block; text-align: center;">🛒 Aggiungi</a>
        </div>
    </div>
<%
        }
    } else {
%>
    <p>Nessun prodotto disponibile.</p>
<% } %>
</div>

<%
    Integer currentPage = (Integer) request.getAttribute("currentPage");
    Integer totalPages = (Integer) request.getAttribute("totalPages");
    Integer limit = (Integer) request.getAttribute("limit");
    if (totalPages != null && totalPages > 1) {
%>
<div class="pagination" style="text-align: center; margin-top: 2rem;">
    <% if (currentPage > 1) { %>
        <a href="catalogo?page=<%= currentPage-1 %>&limit=<%= limit %>" class="btn">« Precedente</a>
    <% } %>
    <% for (int p = 1; p <= totalPages; p++) { %>
        <% if (p == currentPage) { %>
            <span style="background-color: #e94560; color:white; padding:0.5rem 0.8rem; border-radius:5px;"><%= p %></span>
        <% } else { %>
            <a href="catalogo?page=<%= p %>&limit=<%= limit %>" style="padding:0.5rem 0.8rem; border:1px solid #ddd; border-radius:5px; text-decoration:none;"><%= p %></a>
        <% } %>
    <% } %>
    <% if (currentPage < totalPages) { %>
        <a href="catalogo?page=<%= currentPage+1 %>&limit=<%= limit %>" class="btn">Successivo »</a>
    <% } %>
</div>
<% } %>

<%@ include file="footer.jsp" %>