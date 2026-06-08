<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>404 - Pagina non trovata | Buy4Play</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: 'Poppins', 'Segoe UI', sans-serif;
            background: linear-gradient(135deg, #0a0c10 0%, #12161c 100%);
            color: #eef2ff;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            text-align: center;
            padding: 2rem;
        }
        .error-container {
            max-width: 500px;
            background: rgba(18, 22, 28, 0.7);
            backdrop-filter: blur(8px);
            border-radius: 32px;
            padding: 2.5rem;
            border: 1px solid rgba(46, 213, 115, 0.3);
        }
        .error-code {
            font-size: 6rem;
            font-weight: 800;
            color: #e94560;
            text-shadow: 0 0 10px rgba(233, 69, 96, 0.5);
            margin-bottom: 1rem;
        }
        h1 {
            font-size: 1.8rem;
            margin-bottom: 1rem;
        }
        p {
            color: #b9c7d9;
            margin-bottom: 2rem;
        }
        .btn {
            display: inline-block;
            background: linear-gradient(95deg, #2ed573 0%, #1e9b52 100%);
            color: #0a0c10;
            font-weight: 700;
            padding: 0.7rem 1.5rem;
            border-radius: 40px;
            text-decoration: none;
            transition: transform 0.2s, box-shadow 0.2s;
        }
        .btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 20px rgba(46, 213, 115, 0.3);
        }
        .icon {
            font-size: 4rem;
            margin-bottom: 1rem;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <div class="icon">🔍</div>
        <div class="error-code">404</div>
        <h1>Pagina non trovata</h1>
        <p>La risorsa che stai cercando non esiste o è stata spostata.</p>
        <a href="<%= request.getContextPath() %>/catalogo" class="btn">Torna al catalogo</a>
    </div>
</body>
</html>