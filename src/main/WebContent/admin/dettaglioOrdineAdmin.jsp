<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../header.jsp" %>
<%@ page import="java.util.List, model.DettaglioOrdine" %>

<!-- 
  Pagina di dettaglio ordine per l'amministratore.
  Visualizza i prodotti acquistati, quantità, prezzi unitari, aliquota IVA,
  totale per riga e totale complessivo dell'ordine.
  Riceve gli attributi "ordineId" (ID dell'ordine) e "dettagli" (lista di DettaglioOrdine).
-->

<h2>Dettaglio Ordine #<%= request.getAttribute("ordineId") %></h2>

<table class="orders-table">
    <thead>
        <tr><th>Prodotto</th><th>Quantità</th><th>Prezzo unitario</th><th>IVA</th><th>Totale</th></tr>
    </thead>
    <tbody>
        <%
            List<DettaglioOrdine> dettagli = (List<DettaglioOrdine>) request.getAttribute("dettagli");
            double totale = 0.0;
            for (DettaglioOrdine d : dettagli) {
                // Calcola il totale per ogni riga: (prezzo unitario + IVA) * quantità
                double riga = d.getPrezzoUnitario() * (1 + d.getIva()/100) * d.getQuantita();
                totale += riga;
        %>
        <tr>
            <td><%= d.getProdotto().getNome() %></td>
            <td><%= d.getQuantita() %></td>
            <td>&euro; <%= String.format("%.2f", d.getPrezzoUnitario()) %></td>
            <td><%= d.getIva() %>%</td>
            <td>&euro; <%= String.format("%.2f", riga) %></td>
        </tr>
        <% } %>
        <tr>
            <td colspan="4"><strong>Totale complessivo</strong></td>
            <td><strong>&euro; <%= String.format("%.2f", totale) %></strong></td>
        </tr>
    </tbody>
</table>

<!-- Link per tornare alla lista degli ordini admin -->
<a href="VisualizzaOrdini" class="btn">← Torna agli ordini</a>

<%@ include file="../footer.jsp" %>