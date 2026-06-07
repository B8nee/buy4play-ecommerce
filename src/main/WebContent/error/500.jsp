<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>Errore interno</title>
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
        <h1>500 - Errore interno del server</h1>
        <p>Si è verificato un problema. Riprova più tardi.</p>
        <a href="<%= request.getContextPath() %>/catalogo" class="btn">Torna al catalogo</a>
    </body>

    </html>