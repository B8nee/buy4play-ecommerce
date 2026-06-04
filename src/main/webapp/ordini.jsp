<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header.jsp" %>
<%@ page import="java.util.List, model.Ordine, java.text.SimpleDateFormat" %>
<h2>I miei ordini</h2>
<%
    List<Ordine> ordini = (List<Ordine>) request.getAttribute("ordini");
    if (ordini == null || ordini.isEmpty()) {
%>
    <p>Non hai ancora effettuato ordini.</p>
<% } else { %>
    <table border="1" cellpadding="8">
        <tr><th>ID Ordine</th><th>Data</th><th>Totale</th><th>Dettagli</th></tr>
        <% SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
           for (Ordine o : ordini) { %>
        <tr>
            <td><%= o.getId() %></td>
            <td><%= sdf.format(o.getDataOrdine()) %></td>
            <td>&euro; <%= String.format("%.2f", o.getTotale()) %></td>
            <td><a href="dettaglioOrdine?id=<%= o.getId() %>">Vedi</a></td>
        </tr>
        <% } %>
    </table>
<% } %>
<%@ include file="footer.jsp" %>
