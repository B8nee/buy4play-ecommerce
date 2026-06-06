<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="../header.jsp" %>
        <%@ page import="java.util.List, model.Prodotto" %>
            <h2>Gestione Prodotti</h2>
            <a href="gestioneProdotti?action=addForm" class="btn">Aggiungi prodotto</a>
            <table border="1">
                <tr>
                    <th>ID</th>
                    <th>Nome</th>
                    <th>Piattaforma</th>
                    <th>Prezzo</th>
                    <th>Azioni</th>
                </tr>
                <% List<Prodotto> prodotti = (List<Prodotto>) request.getAttribute("prodotti");
                        if (prodotti != null) {
                        for (Prodotto p : prodotti) {
                        %>
                        <tr>
                            <td>
                                <%= p.getId() %>
                            </td>
                            <td>
                                <%= p.getNome() %>
                            </td>
                            <td>
                                <%= p.getPiattaforma() %>
                            </td>
                            <td>&euro; <%= String.format("%.2f", p.getPrezzo()) %>
                            </td>
                            <td>
                                <a href="gestioneProdotti?action=editForm&id=<%= p.getId() %>">Modifica</a>
                                <a href="gestioneProdotti?action=delete&id=<%= p.getId() %>"
                                    onclick="return confirm('Confermi eliminazione?')">Cancella</a>
                            </td>
                        </tr>
                        <% } } %>
            </table>
            <a href="index.jsp">← Torna alla dashboard</a>
            <%@ include file="../footer.jsp" %>