<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<h2>Cambio password</h2>
<% if (request.getAttribute("errore") != null) { %>
    <p class="error"><%= request.getAttribute("errore") %></p>
<% } %>
<% if (request.getAttribute("messaggio") != null) { %>
    <p class="success"><%= request.getAttribute("messaggio") %></p>
<% } %>
<form action="cambioPassword" method="post">
    <label>Password attuale:</label><br>
    <input type="password" name="oldPassword" required><br>
    <label>Nuova password:</label><br>
    <input type="password" name="newPassword" required><br>
    <label>Conferma nuova password:</label><br>
    <input type="password" name="confirmNewPassword" required><br><br>
    <input type="submit" value="Cambia password" class="btn">
</form>
<a href="profilo">← Torna al profilo</a>
<%@ include file="footer.jsp" %>