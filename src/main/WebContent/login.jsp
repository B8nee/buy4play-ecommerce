<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header.jsp" %>

<div class="login-wrapper">
    <div class="login-box">
        <h2>🔐 Accesso</h2>

        <% if (request.getAttribute("errore") != null) { %>
            <div class="alert error"><%= request.getAttribute("errore") %></div>
        <% } %>
        <% if (request.getAttribute("messaggio") != null) { %>
            <div class="alert success"><%= request.getAttribute("messaggio") %></div>
        <% } %>

        <form action="login" method="post">
            <div class="input-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" required autofocus>
            </div>

            <div class="input-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" required>
            </div>

            <div class="checkbox-wrapper">
                <label for="rememberMe">Ricordami</label>
                <input type="checkbox" id="rememberMe" name="rememberMe">
            </div>

            <button type="submit" class="btn btn-login">Accedi</button>
        </form>

        <div class="register-link">
            Non hai un account? <a href="registrazione.jsp">Registrati qui</a>
        </div>
    </div>
</div>

<style>
    .login-wrapper {
        display: flex;
        justify-content: center;
        align-items: center;
        min-height: 70vh;
        padding: 2rem 1rem;
    }
    .login-box {
        background: rgba(18, 22, 28, 0.85);
        backdrop-filter: blur(10px);
        border-radius: 32px;
        padding: 2rem 2rem 2.5rem;
        width: 100%;
        max-width: 460px;
        border: 1px solid rgba(46, 213, 115, 0.3);
        box-shadow: 0 20px 35px -10px rgba(0,0,0,0.4);
    }
    .login-box h2 {
        text-align: center;
        margin-bottom: 1.8rem;
        font-weight: 600;
        color: #fff;
    }
    .input-group {
        margin-bottom: 1.5rem;
    }
    .input-group label {
        display: block;
        margin-bottom: 0.5rem;
        font-weight: 500;
        color: #b9c7d9;
    }
    .input-group input {
        width: 100%;
        padding: 0.85rem 1rem;
        background: #0a0c10;
        border: 1px solid #2c3e3a;
        border-radius: 20px;
        color: #fff;
        font-size: 1rem;
        transition: all 0.2s;
    }
    .input-group input:focus {
        outline: none;
        border-color: #2ed573;
        box-shadow: 0 0 0 2px rgba(46, 213, 115, 0.2);
    }
    .checkbox-wrapper {
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 8px;
    margin: 1rem 0 1.5rem;
}
.checkbox-wrapper input {
    width: 18px;
    height: 18px;
    margin: 0;
    padding: 0;
    cursor: pointer;
    accent-color: #2ed573;
    flex-shrink: 0;
}
.checkbox-wrapper label {
    color: #b9c7d9;
    cursor: pointer;
    user-select: none;
    font-weight: 500;
    margin: 0;
    padding: 0;
    line-height: 18px;
    vertical-align: middle;
}
    .btn-login {
        display: block;
        width: fit-content;
        margin: 0 auto;
        text-align: center;
        font-size: 1rem;
        padding: 0.75rem 1.5rem;
    }
    .register-link {
        text-align: center;
        margin-top: 1.8rem;
        color: #8e9aaf;
    }
    .register-link a {
        color: #2ed573;
        text-decoration: none;
        font-weight: 500;
        transition: color 0.2s;
    }
    .register-link a:hover {
        color: #3ee684;
        text-decoration: underline;
    }
    .alert {
        padding: 0.8rem 1rem;
        border-radius: 24px;
        margin-bottom: 1.2rem;
        text-align: center;
        font-weight: 500;
    }
    .alert.error {
        background: rgba(233, 69, 96, 0.15);
        color: #ff6b6b;
        border-left: 3px solid #e94560;
    }
    .alert.success {
        background: rgba(46, 213, 115, 0.15);
        color: #2ed573;
        border-left: 3px solid #2ed573;
    }
</style>

<%@ include file="footer.jsp" %>