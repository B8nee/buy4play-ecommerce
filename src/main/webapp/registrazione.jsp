<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header.jsp" %>
<h2>Registrazione</h2>
<% if (request.getAttribute("errore") != null) { %>
    <p class="error"><%= request.getAttribute("errore") %></p>
<% } %>
<form action="registrazione" method="post">
    <label>Email:</label><br>
    <input type="email" name="email" required><br>
    <label>Nome:</label><br>
    <input type="text" name="nome" required><br>
    <label>Cognome:</label><br>
    <input type="text" name="cognome" required><br>
    <label>Indirizzo:</label><br>
    <input type="text" name="indirizzo"><br>
    <label>Città:</label><br>
    <input type="text" name="citta"><br>
    <label>Provincia (2 lettere):</label><br>
    <input type="text" name="provincia" maxlength="2"><br>
    <label>CAP:</label><br>
    <input type="text" name="cap" maxlength="5"><br>
    <label>Password:</label><br>
    <input type="password" name="password" required><br>
    <label>Conferma password:</label><br>
    <input type="password" name="conferma" required><br><br>
    <input type="submit" value="Registrati" class="btn">
</form>
<%@ include file="footer.jsp" %>