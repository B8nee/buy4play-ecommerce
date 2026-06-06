<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="../header.jsp" %>
        <% model.Prodotto p=(model.Prodotto) request.getAttribute("prodotto"); boolean isEdit=(p !=null); %>
            <h2>
                <%= isEdit ? "Modifica" : "Nuovo" %> prodotto
            </h2>
            <form action="gestioneProdotti" method="post">
                <input type="hidden" name="action" value="<%= isEdit ? " update" : "add" %>">
                <% if (isEdit) { %>
                    <input type="hidden" name="id" value="<%= p.getId() %>">
                    <% } %>
                        <label>Nome:</label><br>
                        <input type="text" name="nome" value="<%= isEdit ? p.getNome() : "" %>" required><br>
                        <label>Piattaforma:</label><br>
                        <input type="text" name="piattaforma" value="<%= isEdit ? p.getPiattaforma() : "" %>"
                            required><br>
                        <label>Prezzo:</label><br>
                        <input type="number" step="0.01" name="prezzo" value="<%= isEdit ? p.getPrezzo() : "" %>"
                            required><br>
                        <label>URL immagine:</label><br>
                        <input type="text" name="immagineUrl" value="<%= isEdit ? p.getImmagineUrl() : "" %>"><br><br>
                        <input type="submit" value="Salva" class="btn">
            </form>
            <a href="gestioneProdotti?action=list">← Torna all'elenco</a>
            <%@ include file="../footer.jsp" %>