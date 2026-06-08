<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header.jsp" %>
<%@ page import="java.util.List, model.Ordine, java.text.SimpleDateFormat, java.util.Date" %>
<%@ page import="java.util.Calendar, java.util.ArrayList, java.util.Collections" %>
<%
    List<Ordine> ordini = (List<Ordine>) request.getAttribute("ordini");
    if (ordini == null) {
        response.sendRedirect("catalogo");
        return;
    }
    SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    SimpleDateFormat sdfAnnoMese = new SimpleDateFormat("yyyy-MM");
    
    String filtroMese = request.getParameter("mese");
    List<Ordine> ordiniFiltrati = ordini;
    if (filtroMese != null && !filtroMese.isEmpty()) {
        ordiniFiltrati = new ArrayList<>();
        for (Ordine o : ordini) {
            String annoMese = sdfAnnoMese.format(o.getDataOrdine());
            if (annoMese.equals(filtroMese)) {
                ordiniFiltrati.add(o);
            }
        }
    }
%>
<div class="ordini-container">
    <h2>📋 I miei ordini</h2>

    <div class="filtro-ordini">
        <form method="get" action="ordini">
            <label>Filtra per mese:</label>
            <select name="mese" onchange="this.form.submit()">
                <option value="">Tutti</option>
                <%
                    java.util.Set<String> periodi = new java.util.HashSet<>();
                    for (Ordine o : ordini) {
                        periodi.add(sdfAnnoMese.format(o.getDataOrdine()));
                    }
                    List<String> periodiList = new ArrayList<>(periodi);
                    Collections.sort(periodiList, Collections.reverseOrder());
                    for (String p : periodiList) {
                        String[] parts = p.split("-");
                        String visualizza = parts[1] + "/" + parts[0];
                %>
                <option value="<%= p %>" <%= p.equals(filtroMese) ? "selected" : "" %>><%= visualizza %></option>
                <% } %>
            </select>
            <noscript><input type="submit" value="Filtra" class="btn-small"></noscript>
            <% if (filtroMese != null && !filtroMese.isEmpty()) { %>
                <a href="ordini" class="btn-outline btn-small">Reset</a>
            <% } %>
        </form>
    </div>

    <% if (ordiniFiltrati.isEmpty()) { %>
        <div class="empty-orders">
            <p>Nessun ordine trovato.</p>
            <a href="catalogo" class="btn">Inizia a fare acquisti</a>
        </div>
    <% } else { %>
        <div class="ordini-grid">
            <% for (Ordine o : ordiniFiltrati) { %>
                <div class="ordine-card">
                    <div class="ordine-header">
                        <span class="ordine-id">Ordine #<%= o.getId() %></span>
                        <span class="ordine-data"><%= sdfData.format(o.getDataOrdine()) %></span>
                    </div>
                    <div class="ordine-body">
                        <div class="ordine-totale">Totale: <strong>&euro; <%= String.format("%.2f", o.getTotale()) %></strong></div>
                        <div class="ordine-indirizzo">📦 Spedizione: <%= o.getIndirizzoSpedizione() != null ? o.getIndirizzoSpedizione() : "N/D" %></div>
                    </div>
                    <div class="ordine-actions">
                        <a href="dettaglioOrdine?id=<%= o.getId() %>" class="btn-small">📄 Dettagli</a>
                        <a href="FatturaPDFControl?id=<%= o.getId() %>" class="btn-small btn-outline" target="_blank">📎 Scarica PDF</a>
                    </div>
                </div>
            <% } %>
        </div>
    <% } %>
</div>

<style>
    .ordini-container {
        max-width: 1000px;
        margin: 2rem auto;
        background: rgba(18, 22, 28, 0.7);
        border-radius: 32px;
        padding: 2rem;
    }
    .filtro-ordini {
        margin: 1.5rem 0;
        text-align: right;
    }
    .filtro-ordini select, .filtro-ordini .btn-small {
        padding: 0.4rem 0.8rem;
        border-radius: 30px;
        background: #0a0c10;
        border: 1px solid #2ed573;
        color: white;
        margin-left: 0.5rem;
    }
    .ordini-grid {
        display: flex;
        flex-direction: column;
        gap: 1rem;
    }
    .ordine-card {
        background: #0f1117;
        border-radius: 24px;
        padding: 1.2rem 1.5rem;
        border: 1px solid rgba(46, 213, 115, 0.2);
        transition: all 0.2s;
    }
    .ordine-card:hover {
        border-color: #2ed573;
        transform: translateX(4px);
    }
    .ordine-header {
        display: flex;
        justify-content: space-between;
        margin-bottom: 0.8rem;
        padding-bottom: 0.5rem;
        border-bottom: 1px solid rgba(46, 213, 115, 0.2);
    }
    .ordine-id {
        font-weight: bold;
        color: #2ed573;
    }
    .ordine-data {
        font-size: 0.85rem;
        color: #b9c7d9;
    }
    .ordine-body {
        margin-bottom: 1rem;
    }
    .ordine-totale {
        font-size: 1.2rem;
        margin-bottom: 0.3rem;
    }
    .ordine-indirizzo {
        font-size: 0.85rem;
        color: #b9c7d9;
    }
    .ordine-actions {
        display: flex;
        gap: 1rem;
        justify-content: flex-end;
    }
    .btn-small {
        padding: 0.3rem 1rem;
        font-size: 0.8rem;
        border-radius: 30px;
        text-decoration: none;
        background: #2ed573;
        color: #0a0c10;
        font-weight: bold;
        transition: 0.2s;
        border: none;
        cursor: pointer;
        display: inline-block;
    }
    .btn-small:hover {
        background: #3ee684;
    }
    .btn-outline {
        background: transparent;
        border: 1px solid #2ed573;
        color: #2ed573;
    }
    .btn-outline:hover {
        background: #2ed573;
        color: #0a0c10;
    }
    .empty-orders {
        text-align: center;
        padding: 2rem;
    }
    @media (max-width: 768px) {
        .ordini-container {
            margin: 1rem;
            padding: 1rem;
        }
        .ordine-header {
            flex-direction: column;
            gap: 0.3rem;
        }
        .ordine-actions {
            justify-content: center;
        }
    }
</style>

<%@ include file="footer.jsp" %>