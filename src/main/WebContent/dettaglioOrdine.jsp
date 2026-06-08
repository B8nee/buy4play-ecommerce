<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header.jsp" %>
<%@ page import="model.Ordine, model.DettaglioOrdine, java.util.List, java.text.SimpleDateFormat" %>
<%
    Ordine ordine = (Ordine) request.getAttribute("ordine");
    if (ordine == null) {
        response.sendRedirect("ordini");
        return;
    }
    List<DettaglioOrdine> dettagli = (List<DettaglioOrdine>) request.getAttribute("dettagli");
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
%>
<div class="dettaglio-container">
    <h2>📄 Dettaglio ordine #<%= ordine.getId() %></h2>
    <div class="dettaglio-info">
        <p><strong>Data ordine:</strong> <%= sdf.format(ordine.getDataOrdine()) %></p>
        <p><strong>Indirizzo di spedizione:</strong> <%= ordine.getIndirizzoSpedizione() != null ? ordine.getIndirizzoSpedizione() : "N/D" %></p>
        <p><strong>Totale ordine:</strong> &euro; <%= String.format("%.2f", ordine.getTotale()) %></p>
    </div>
    <table class="dettaglio-table">
        <thead>
            <tr><th>Prodotto</th><th>Quantità</th><th>Prezzo unitario</th><th>IVA</th><th>Totale</th></tr>
        </thead>
        <tbody>
            <%
                double totaleRighe = 0.0;
                for (DettaglioOrdine d : dettagli) {
                    double prezzoConIva = d.getPrezzoUnitario() * (1 + d.getIva() / 100);
                    double totaleRiga = prezzoConIva * d.getQuantita();
                    totaleRighe += totaleRiga;
            %>
            <tr>
                <td><%= d.getProdotto().getNome() %></td>
                <td><%= d.getQuantita() %></td>
                <td>&euro; <%= String.format("%.2f", d.getPrezzoUnitario()) %></td>
                <td><%= d.getIva() %>%</td>
                <td>&euro; <%= String.format("%.2f", totaleRiga) %></td>
            </tr>
            <% } %>
        </tbody>
        <tfoot>
            <tr><td colspan="4" align="right"><strong>Totale complessivo</strong></td><td><strong>&euro; <%= String.format("%.2f", totaleRighe) %></strong></td></tr>
        </tfoot>
    </table>
    <div class="dettaglio-actions">
        <a href="ordini" class="btn">← Torna agli ordini</a>
        <a href="FatturaPDFControl?id=<%= ordine.getId() %>" class="btn" target="_blank">📎 Scarica PDF fattura</a>
    </div>
</div>

<style>
    .dettaglio-container {
        max-width: 1000px;
        margin: 2rem auto;
        background: rgba(18, 22, 28, 0.7);
        border-radius: 32px;
        padding: 2rem;
    }
    .dettaglio-info {
        background: #0a0c10;
        padding: 1rem;
        border-radius: 20px;
        margin-bottom: 1.5rem;
        border-left: 4px solid #2ed573;
    }
    .dettaglio-table {
        width: 100%;
        border-collapse: collapse;
        margin-bottom: 1.5rem;
    }
    .dettaglio-table th, .dettaglio-table td {
        padding: 0.8rem;
        text-align: left;
        border-bottom: 1px solid rgba(46, 213, 115, 0.2);
    }
    .dettaglio-table th {
        color: #2ed573;
    }
    .dettaglio-actions {
        display: flex;
        justify-content: space-between;
        gap: 1rem;
        flex-wrap: wrap;
    }
    @media (max-width: 768px) {
        .dettaglio-container {
            margin: 1rem;
            padding: 1rem;
        }
        .dettaglio-table th, .dettaglio-table td {
            padding: 0.5rem;
            font-size: 0.85rem;
        }
    }
</style>

<%@ include file="footer.jsp" %>