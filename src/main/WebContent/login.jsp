<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="header.jsp" %>
        <h2>Login</h2>
        <% if (request.getAttribute("errore") !=null) { %>
            <p class="error">
                <%= request.getAttribute("errore") %>
            </p>
            <% } %>
                <% if (request.getAttribute("messaggio") !=null) { %>
                    <p class="success">
                        <%= request.getAttribute("messaggio") %>
                    </p>
                    <% } %>
                        <form action="login" method="post">
    <label>Email:</label><br>
    <input type="email" name="email" required><br>
    <label>Password:</label><br>
    <input type="password" name="password" required><br>
    <label>
        <input type="checkbox" name="rememberMe"> Ricordami
    </label>
    <br><br>
    <input type="submit" value="Accedi" class="btn">
</form>
                        <p>Non hai un account? <a href="registrazione.jsp">Registrati qui</a></p>
                        <%@ include file="footer.jsp" %>