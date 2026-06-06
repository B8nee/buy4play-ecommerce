<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>Errore</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                padding: 20px;
            }

            .error-details {
                background: #f4f4f4;
                border-left: 5px solid #e94560;
                padding: 10px;
                margin: 20px 0;
            }
        </style>
    </head>

    <body>
        <h1>Si è verificato un errore</h1>
        <div class="error-details">
            <p><strong>Messaggio:</strong>
                <%= exception.getMessage() %>
            </p>
            <p><strong>Tipo:</strong>
                <%= exception.getClass().getName() %>
            </p>
        </div>
        <a href="<%= request.getContextPath() %>/catalogo">Torna al catalogo</a>
    </body>

    </html>