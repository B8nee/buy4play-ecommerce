<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../header.jsp" %>
<%@ page import="java.util.List, model.DettaglioOrdine" %>
<h2>Dettaglio Ordine #<%= request.getAttribute("ordineId") %></h2>
<table class="orders-table">
    <tr><th>Prodotto</th><th>Quantità</th><th>Prezzo unitario</th><th>IVA</th><th>Totale</th></tr>
<%
    List<DettaglioOrdine> dettagli = (List<DettaglioOrdine>) request.getAttribute("dettagli");
    double totale = 0.0;
    for (DettaglioOrdine d : dettagli) {
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
<tr><td colspan="4"><strong>Totale complessivo</strong></td><td><strong>&euro; <%= String.format("%.2f", totale) %></strong></td></tr>
</table>
<a href="VisualizzaOrdini" class="btn">← Torna agli ordini</a>
<%@ include file="../footer.jsp" %>