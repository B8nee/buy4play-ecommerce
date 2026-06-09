<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Errore - Buy4Play</title>
        <style>
            /* Reset e stili per la pagina di errore generica (tema scuro) */
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
                padding: 2rem;
            }
            .error-container {
                max-width: 800px;
                margin: 2rem auto;
                background: rgba(18, 22, 28, 0.7);
                backdrop-filter: blur(8px);
                border-radius: 32px;
                padding: 2rem;
                border: 1px solid rgba(46, 213, 115, 0.3);
            }
            .error-icon {
                font-size: 3rem;
                margin-bottom: 1rem;
            }
            h1 {
                color: #e94560;
                margin-bottom: 1rem;
            }
            .error-details {
                background: rgba(0,0,0,0.3);
                border-left: 4px solid #e94560;
                padding: 1rem;
                margin: 1.5rem 0;
                border-radius: 12px;
                text-align: left;
                overflow-x: auto;
            }
            .error-details p {
                color: #ffb9c5;
                font-family: monospace;
                font-size: 0.85rem;
                margin: 0.5rem 0;
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
                margin-top: 1rem;
            }
            .btn:hover {
                transform: translateY(-2px);
                box-shadow: 0 8px 20px rgba(46, 213, 115, 0.3);
            }
        </style>
    </head>
    <body>
        <div class="error-container">
            <!-- Icona generica di errore -->
            <div class="error-icon">🚨</div>
            <h1>Si è verificato un errore</h1>
            <p>Qualcosa è andato storto durante l'elaborazione della tua richiesta.</p>
            
            <!--
                Blocco di dettagli tecnici, visibile solo in modalità debug.
                Le condizioni per mostrarlo sono:
                - presenza del parametro "debug" nell'URL (es. ?debug)
                - oppure attributo di contesto "debug" impostato (es. via ServletContextListener)
                In produzione normale, l'utente finale vede solo un messaggio generico.
            -->
            <% if (request.getParameter("debug") != null || application.getAttribute("debug") != null) { %>
                <div class="error-details">
                    <p><strong>Messaggio:</strong> <%= exception != null ? exception.getMessage() : "Nessun dettaglio disponibile" %></p>
                    <p><strong>Tipo:</strong> <%= exception != null ? exception.getClass().getName() : "N/A" %></p>
                </div>
            <% } else { %>
                <div class="error-details">
                    <p>Per assistenza, contatta l'amministratore.</p>
                </div>
            <% } %>
            
            <!-- Link per tornare al catalogo (usando il contesto dell'applicazione) -->
            <a href="<%= request.getContextPath() %>/catalogo" class="btn">Torna al catalogo</a>
        </div>
    </body>
</html>