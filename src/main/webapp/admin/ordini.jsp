<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="../header.jsp" %>
        <%@ page import="java.util.List, model.Ordine, java.text.SimpleDateFormat" %>
            <h2>Gestione Ordini</h2>
            <form method="get" action="visualizzaOrdini">
                Data da: <input type="date" name="dataInizio" value="<%= request.getParameter(" dataInizio") !=null ?
                    request.getParameter("dataInizio") : "" %>">
                Data a: <input type="date" name="dataFine" value="<%= request.getParameter(" dataFine") !=null ?
                    request.getParameter("dataFine") : "" %>">
                Cliente ID: <input type="text" name="clienteId" value="<%= request.getParameter(" clienteId") !=null ?
                    request.getParameter("clienteId") : "" %>">
                <input type="submit" value="Filtra">
            </form>
            <table border="1">
                <tr>
                    <th>ID Ordine</th>
                    <th>Cliente ID</th>
                    <th>Data</th>
                    <th>Totale</th>
                    <th>Dettaglio</th>
                </tr>
                <% List<Ordine> ordini = (List<Ordine>) request.getAttribute("ordini");
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        if (ordini != null) {
                        for (Ordine o : ordini) {
                        %>
                        <tr>
                            <td>
                                <%= o.getId() %>
                            </td>
                            <td>
                                <%= o.getUtenteId() %>
                            </td>
                            <td>
                                <%= sdf.format(o.getDataOrdine()) %>
                            </td>
                            <td>&euro; <%= String.format("%.2f", o.getTotale()) %>
                            </td>
                            <td><a href="../dettaglioOrdine?id=<%= o.getId() %>">Dettaglio</a></td>
                        </tr>
                        <% } } %>
            </table>
            <a href="index.jsp">← Torna alla dashboard</a>
            <%@ include file="../footer.jsp" %>