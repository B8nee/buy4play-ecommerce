<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>Pagina non trovata</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                text-align: center;
                padding: 50px;
            }

            h1 {
                color: #e94560;
            }

            .btn {
                background-color: #e94560;
                color: white;
                padding: 10px 20px;
                text-decoration: none;
                border-radius: 5px;
            }
        </style>
    </head>

    <body>
        <h1>404 - Pagina non trovata</h1>
        <p>La risorsa richiesta non esiste o non è disponibile.</p>
        <a href="<%= request.getContextPath() %>/catalogo" class="btn">Torna al catalogo</a>
    </body>

    </html>