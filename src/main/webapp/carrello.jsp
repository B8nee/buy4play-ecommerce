<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header.jsp" %>
<%@ page import="model.Cart, model.CartItem, model.Prodotto" %>
<%
    Cart cart = (Cart) session.getAttribute("cart");
    if (cart == null) cart = new Cart();
%>
<h2>Il tuo carrello</h2>
<% if (cart.isEmpty()) { %>
    <p>Il carrello è vuoto.</p>
    <a href="catalogo" class="btn">Continua gli acquisti</a>
<% } else { %>
    <table border="0" cellpadding="10" style="width:100%; border-collapse: collapse;">
        <tr style="background:#eee;">
            <th>Prodotto</th><th>Prezzo</th><th>Quantità</th><th>Subtotale</th><th></th>
        </tr>
        <% for (CartItem item : cart.getItems()) { 
            Prodotto p = item.getProdotto();
        %>
        <tr>
            <td><%= p.getNome() %></td>
            <td>&euro; <%= String.format("%.2f", p.getPrezzo()) %></td>
            <td>
                <form action="carrello" method="get" style="display:inline;">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="id" value="<%= p.getId() %>">
                    <input type="number" name="qty" value="<%= item.getQuantita() %>" min="1" max="99" style="width:60px;">
                    <input type="submit" value="Aggiorna" class="btn" style="padding:0.2rem 0.5rem;">
                </form>
            </td>
            <td>&euro; <%= String.format("%.2f", item.getSubtotale()) %></td>
            <td><a href="carrello?action=remove&id=<%= p.getId() %>" class="btn" style="background:#aaa;">Rimuovi</a></td>
        </tr>
        <% } %>
        <tr style="font-weight: bold;">
            <td colspan="3" align="right">Totale:</td>
            <td>&euro; <%= String.format("%.2f", cart.getTotal()) %></td>
            <td></td>
        </tr>
    </table>
    <div style="margin-top: 2rem;">
        <a href="catalogo" class="btn">Continua acquisti</a>
        <a href="carrello?action=clear" class="btn" style="background:#aaa;">Svuota carrello</a>
        <a href="checkout" class="btn">Procedi al checkout</a>
    </div>
<% } %>
<%@ include file="footer.jsp" %>