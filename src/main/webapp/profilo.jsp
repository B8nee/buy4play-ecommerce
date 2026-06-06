<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="header.jsp" %>
        <%@ page import="model.Utente" %>
            <% Utente u=(Utente) request.getAttribute("utente"); if (u==null) { response.sendRedirect("catalogo");
                return; } %>
                <h2>Il mio profilo</h2>

                <% if (request.getAttribute("messaggio") !=null) { %>
                    <p class="success">
                        <%= request.getAttribute("messaggio") %>
                    </p>
                    <% } %>
                        <% if (request.getAttribute("errore") !=null) { %>
                            <p class="error">
                                <%= request.getAttribute("errore") %>
                            </p>
                            <% } %>

                                <form action="profilo" method="post">
                                    <label>Nome:</label><br>
                                    <input type="text" name="nome" value="<%= u.getNome() != null ? u.getNome() : "" %>"
                                        required><br>
                                    <label>Cognome:</label><br>
                                    <input type="text" name="cognome"
                                        value="<%= u.getCognome() != null ? u.getCognome() : "" %>" required><br>
                                    <label>Indirizzo:</label><br>
                                    <input type="text" name="indirizzo"
                                        value="<%= u.getIndirizzo() != null ? u.getIndirizzo() : "" %>"><br>
                                    <label>Città:</label><br>
                                    <input type="text" name="citta"
                                        value="<%= u.getCitta() != null ? u.getCitta() : "" %>"><br>
                                    <label>Provincia (2 lettere):</label><br>
                                    <input type="text" name="provincia"
                                        value="<%= u.getProvincia() != null ? u.getProvincia() : "" %>"
                                        maxlength="2"><br>
                                    <label>CAP:</label><br>
                                    <input type="text" name="cap" value="<%= u.getCap() != null ? u.getCap() : "" %>"
                                        maxlength="5"><br><br>
                                    <input type="submit" value="Aggiorna profilo" class="btn">
                                </form>

                                <hr>
                                <h3>Modifica password</h3>
                                <p><a href="cambioPassword.jsp" class="btn">Cambia password</a></p>

                                <br>
                                <a href="catalogo">← Torna al catalogo</a>
                                <%@ include file="footer.jsp" %>