<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Buy 4 Play</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }
        header {
            background-color: #1a1a2e;
            color: white;
            padding: 1rem 2rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
            flex-wrap: wrap;
        }
        .logo h1 {
            margin: 0;
            font-size: 1.8rem;
        }
        .logo p {
            margin: 0;
            font-size: 0.8rem;
            color: #ccc;
        }
        nav a {
            color: white;
            text-decoration: none;
            margin: 0 1rem;
            font-weight: bold;
        }
        nav a:hover { color: #e94560; }
        .container {
            max-width: 1200px;
            margin: 2rem auto;
            padding: 0 1rem;
        }
        footer {
            background-color: #1a1a2e;
            color: #ccc;
            text-align: center;
            padding: 1.5rem;
            margin-top: 2rem;
        }
        .btn {
            background-color: #e94560;
            color: white;
            padding: 0.5rem 1rem;
            border: none;
            border-radius: 25px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
        }
        .btn:hover { background-color: #c5314b; }
        .error { color: red; }
        .success { color: green; }
    </style>
</head>
<body>
<header>
    <div class="logo">
        <h1>🎮 Buy 4 Play</h1>
        <p>Le migliori chiavi per i tuoi giochi</p>
    </div>
    <nav>
        <a href="catalogo">Catalogo</a>
        <a href="carrello">Carrello</a>
        <% 
            model.Utente utente = (model.Utente) session.getAttribute("utente");
            if (utente != null) { 
        %>
            <span>Benvenuto, <%= utente.getNome() %></span>
            <a href="logout">Logout</a>
        <% } else { %>
            <a href="login.jsp">Accedi</a>
            <a href="registrazione.jsp">Registrati</a>
        <% } %>
    </nav>
</header>
<div class="container">