<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Carrello, model.CarrelloItem, java.util.List" %>
<%
    Carrello carrello = (Carrello) session.getAttribute("carrello");
    if (carrello == null) {
        carrello = new Carrello();
        session.setAttribute("carrello", carrello);
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Il mio carrello</title>
    <style>
        body { font-family: Arial; margin: 20px; }
        table { width: 100%; border-collapse: collapse; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: center; }
        th { background: #1a1a2e; color: white; }
        .totale { font-size: 1.5em; margin-top: 20px; }
        button { background: #e94560; color: white; border: none; padding: 5px 10px; cursor: pointer; border-radius: 5px; }
        a { text-decoration: none; color: #e94560; }
    </style>
</head>
<body>
    <h1>Il tuo carrello</h1>
    <table>
        <tr>
            <th>Prodotto</th>
            <th>Piattaforma</th>
            <th>Prezzo unitario</th>
            <th>Quantità</th>
            <th>Totale</th>
            <th></th>
        </tr>
        <%
            List<CarrelloItem> items = carrello.getItems();
            if (items.isEmpty()) {
        %>
        <tr><td colspan="6">Il carrello è vuoto</td></tr>
        <%
            } else {
                for (CarrelloItem item : items) {
        %>
        <tr>
            <td><%= item.getProdotto().getNome() %></td>
            <td><%= item.getProdotto().getPiattaforma() %></td>
            <td>€ <%= String.format("%.2f", item.getProdotto().getPrezzo()) %></td>
            <td>
                <form action="carrello" method="get" style="display:inline;">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="id" value="<%= item.getProdotto().getId() %>">
                    <input type="number" name="quantita" value="<%= item.getQuantita() %>" min="1" style="width:60px;">
                    <button type="submit">Aggiorna</button>
                </form>
            </td>
            <td>€ <%= String.format("%.2f", item.getProdotto().getPrezzo() * item.getQuantita()) %></td>
            <td>
                <a href="carrello?action=remove&id=<%= item.getProdotto().getId() %>">Rimuovi</a>
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>
    <div class="totale">
        <strong>Totale carrello: € <%= String.format("%.2f", carrello.getTotale()) %></strong>
    </div>
    <p>
        <a href="catalogo">← Continua acquisti</a>&nbsp;&nbsp;
        <a href="carrello?action=clear" onclick="return confirm('Svuotare il carrello?')">Svuota carrello</a>&nbsp;&nbsp;
        <a href="checkout">Procedi all'acquisto</a> (da implementare)
    </p>
</body>
</html>
