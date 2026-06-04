<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header.jsp" %>
<%@ page import="java.util.List, model.DettaglioOrdine" %>
<h2>Dettaglio ordine #<%= request.getAttribute("ordineId") %></h2>
<table border="1">
    <tr><th>Prodotto</th><th>Quantità</th><th>Prezzo unitario</th><th>IVA</th><th>Totale</th></tr>
<%
    List<DettaglioOrdine> dettagli = (List<DettaglioOrdine>) request.getAttribute("dettagli");
    double totaleGenerale = 0.0;
    for (DettaglioOrdine d : dettagli) {
        double prezzoConIva = d.getPrezzoUnitario() * (1 + d.getIva()/100);
        double totaleRiga = prezzoConIva * d.getQuantita();
        totaleGenerale += totaleRiga;
%>
    <tr>
        <td><%= d.getProdotto().getNome() %></td>
        <td><%= d.getQuantita() %></td>
        <td>&euro; <%= String.format("%.2f", d.getPrezzoUnitario()) %></td>
        <td><%= d.getIva() %>%</td>
        <td>&euro; <%= String.format("%.2f", totaleRiga) %></td>
    </tr>
<% } %>
</table>
<p><strong>Totale complessivo: &euro; <%= String.format("%.2f", totaleGenerale) %></strong></p>

<a href="FatturaPDFControl?id=<%= request.getAttribute("ordineId") %>" class="btn" target="_blank">📄 Scarica PDF fattura</a>
<br><br>
<a href="ordini">← Torna agli ordini</a>
<%@ include file="footer.jsp" %>
