<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header.jsp" %>
<%@ page import="java.util.List, model.Prodotto" %>
<h2>Catalogo giochi</h2>

<div class="filters-bar">
    <div class="filter-group">
        <label>Piattaforma:</label>
        <select id="platformFilter" onchange="applyFilters()">
            <option value="">Tutte</option>
            <option value="PC" <%= "PC".equals(request.getAttribute("platform")) ? "selected" : "" %>>PC</option>
            <option value="PS5" <%= "PS5".equals(request.getAttribute("platform")) ? "selected" : "" %>>PS5</option>
            <option value="Xbox" <%= "Xbox".equals(request.getAttribute("platform")) ? "selected" : "" %>>Xbox</option>
        </select>
    </div>
    <div class="filter-group">
        <label>Ordina per:</label>
        <select id="sortBy" onchange="applyFilters()">
            <option value="prezzo_asc" <%= "prezzo_asc".equals(request.getAttribute("sort")) ? "selected" : "" %>>Prezzo crescente</option>
            <option value="prezzo_desc" <%= "prezzo_desc".equals(request.getAttribute("sort")) ? "selected" : "" %>>Prezzo decrescente</option>
        </select>
    </div>
</div>

<div class="catalogo">
    <%
        List<Prodotto> prodotti = (List<Prodotto>) request.getAttribute("listaProdotti");
        if (prodotti != null && !prodotti.isEmpty()) {
            for (Prodotto p : prodotti) {
    %>
    <div class="prodotto-card">
        <% if (p.isPopolare()) { %>
            <div class="card-badge">🔥 Popolare</div>
        <% } %>
        <img src="<%= p.getImmagineUrl() %>" alt="<%= p.getNome() %>">
        <div class="info">
            <h3><a href="dettaglio?id=<%= p.getId() %>"><%= p.getNome() %></a></h3>
            <div class="piattaforma"><%= p.getPiattaforma() %></div>
            <div class="prezzo">&euro; <%= String.format("%.2f", p.getPrezzo()) %></div>
            <button class="btn add-to-cart" data-id="<%= p.getId() %>">🛒 Aggiungi al carrello</button>
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
    String platform = (String) request.getAttribute("platform");
    String sort = (String) request.getAttribute("sort");
    
    if (totalPages != null && totalPages > 1) {
%>
<div class="pagination">
    <% if (currentPage > 1) { %>
        <a href="#" data-page="<%= currentPage-1 %>">« Precedente</a>
    <% } %>
    <% for (int p = 1; p <= totalPages; p++) { %>
        <% if (p == currentPage) { %>
            <span class="active"><%= p %></span>
        <% } else { %>
            <a href="#" data-page="<%= p %>"><%= p %></a>
        <% } %>
    <% } %>
    <% if (currentPage < totalPages) { %>
        <a href="#" data-page="<%= currentPage+1 %>">Successivo »</a>
    <% } %>
</div>
<% } %>

<script>
    function applyFilters() {
        const platform = document.getElementById('platformFilter').value;
        const sort = document.getElementById('sortBy').value;
        const url = new URL(window.location.href);
        if (platform) url.searchParams.set('platform', platform);
        else url.searchParams.delete('platform');
        if (sort) url.searchParams.set('sort', sort);
        else url.searchParams.delete('sort');
        url.searchParams.set('page', '1');
        window.location.href = url.toString();
    }
    
    document.querySelectorAll('.pagination a').forEach(link => {
        link.addEventListener('click', function(e) {
            e.preventDefault();
            const page = this.getAttribute('data-page');
            const url = new URL(window.location.href);
            url.searchParams.set('page', page);
            window.location.href = url.toString();
        });
    });
    
    document.querySelectorAll('.add-to-cart').forEach(button => {
        button.addEventListener('click', function() {
            const productId = this.getAttribute('data-id');
            const originalText = this.innerHTML;
            this.innerHTML = '⏳ Caricamento...';
            fetch('carrello?action=add&id=' + productId, { method: 'GET' })
                .then(response => {
                    if (response.ok) {
                        this.innerHTML = '✓ Aggiunto!';
                        setTimeout(() => {
                            this.innerHTML = originalText;
                        }, 1500);
                    } else {
                        this.innerHTML = '❌ Errore';
                        setTimeout(() => {
                            this.innerHTML = originalText;
                        }, 2000);
                    }
                })
                .catch(err => {
                    console.error(err);
                    this.innerHTML = '❌ Errore';
                    setTimeout(() => {
                        this.innerHTML = originalText;
                    }, 2000);
                });
        });
    });
</script>

<%@ include file="footer.jsp" %>