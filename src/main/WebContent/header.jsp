<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Buy 4 Play</title>
        <link rel="icon" type="image/x-icon" href="<%= request.getContextPath() %>/favicon.ico">
        <style>
            /* ========== STILI GLOBALI ========== */
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }

            body {
                font-family: 'Poppins', 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background: linear-gradient(135deg, #0a0c10 0%, #12161c 100%);
                color: #eef2ff;
                line-height: 1.5;
                display: flex;
                flex-direction: column;
                min-height: 100vh;          /* Assicura che il footer resti in basso */
            }

            @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800&display=swap');

            /* Contenitore principale flessibile per spingere il footer in basso */
            .container {
                flex: 1;
                max-width: 1440px;
                margin: 0 auto;
                padding: 0 2rem;
                width: 100%;
            }

            /* ========== HEADER (NAVBAR) ========== */
            header {
                background: rgba(10, 14, 23, 0.98);
                backdrop-filter: blur(12px);
                border-bottom: 1px solid rgba(46, 213, 115, 0.3);
                padding: 0.8rem 2rem;
                position: sticky;
                top: 0;
                z-index: 100;
                display: flex;
                align-items: center;
                justify-content: space-between;
                flex-wrap: wrap;
                gap: 1rem;
            }

            .logo {
                flex: 0 0 auto;
            }

            .logo h1 {
                font-size: 1.8rem;
                font-weight: 700;
                background: linear-gradient(135deg, #ffffff, #2ed573);
                -webkit-background-clip: text;
                background-clip: text;
                color: transparent;
                letter-spacing: -0.5px;
                margin-left: 13px;
                transition: transform 0.2s;
            }
            .logo h1:hover {
                transform: translateY(-2px);
            }

            .logo p {
                font-size: 0.7rem;
                color: #8e9aaf;
                margin-top: 2px;
            }

            /* Menu di navigazione (non più usato? mantenuto per compatibilità) */
            .nav-menu {
                display: flex;
                gap: 1.8rem;
                align-items: center;
                flex-wrap: wrap;
            }
            .nav-menu a {
                color: #e0e6f0;
                text-decoration: none;
                font-weight: 500;
                font-size: 1rem;
                transition: color 0.2s;
                white-space: nowrap;
            }
            .nav-menu a:hover {
                color: #2ed573;
            }

            /* Barra di ricerca con effetto hover e focus */
            .search-area {
                display: flex;
                align-items: center;
                background: #1e232c;
                border-radius: 40px;
                padding: 0.3rem 0.8rem;
                border: 1px solid #2c3e3a;
                transition: all 0.2s;
                margin: 0 1rem;
                justify-content: center;
            }
            .search-area:hover {
                transform: translateY(-2px);
            }
            .search-area:focus-within {
                border-color: #2ed573;
                box-shadow: 0 0 0 2px rgba(46, 213, 115, 0.2);
            }
            .search-area input {
                background: transparent;
                border: none;
                padding: 0.5rem;
                color: white;
                font-size: 0.9rem;
                outline: none;
                width: 100%;
                max-width: 180px;
            }
            .search-area button {
                background: transparent;
                border: none;
                color: #b9c7d9;
                cursor: pointer;
                font-size: 1.2rem;
                display: flex;
                align-items: center;
                padding-left: 5px;
            }
            .search-area button:hover {
                color: #2ed573;
            }

            /* Area utente (icone e testo) */
            .user-actions {
                display: flex;
                align-items: center;
                gap: 1rem;
                flex: 0 0 auto;
            }
            .icon-link {
                display: flex;
                align-items: center;
                gap: 0.3rem;
                color: #b9c7d9;
                text-decoration: none;
                font-weight: 500;
                transition: color 0.2s;
            }
            .icon-link:hover {
                color: #2ed573;
            }
            .user-info {
                background: rgba(46, 213, 115, 0.15);
                padding: 0.3rem 0.8rem;
                border-radius: 40px;
                font-size: 0.85rem;
                font-weight: 500;
                color: #2ed573;
            }

            /* Responsive header */
            @media (max-width: 900px) {
                header {
                    flex-direction: column;
                    text-align: center;
                }
                .nav-menu {
                    justify-content: center;
                }
                .search-area input {
                    width: 140px;
                }
            }
            @media (max-width: 600px) {
                .user-actions {
                    flex-wrap: wrap;
                    justify-content: center;
                }
                .search-area input {
                    width: 100px;
                }
            }

            /* ========== BOTTONI ========== */
            .btn {
                display: inline-block;
                background: linear-gradient(95deg, #2ed573 0%, #1e9b52 100%);
                color: #0a0c10;
                font-weight: 700;
                padding: 0.7rem 1.5rem;
                border-radius: 40px;
                text-decoration: none;
                text-align: center;
                transition: transform 0.2s, box-shadow 0.2s;
                border: none;
                cursor: pointer;
                font-size: 0.9rem;
                letter-spacing: 0.5px;
            }
            .btn:hover {
                transform: translateY(-2px);
                box-shadow: 0 8px 20px rgba(46, 213, 115, 0.3);
                background: linear-gradient(95deg, #3ee684 0%, #26b85f 100%);
                color: #000;
            }
            .btn-outline {
                background: transparent;
                border: 1px solid #2ed573;
                color: #2ed573;
                box-shadow: none;
            }
            .btn-outline:hover {
                background: #2ed573;
                color: #0a0c10;
            }

            /* ========== TITOLI ========== */
            h2 {
                font-size: 1.8rem;
                font-weight: 600;
                margin-bottom: 2rem;
                position: relative;
                display: inline-block;
            }
            h2:after {
                content: '';
                position: absolute;
                bottom: -8px;
                left: 0;
                width: 60px;
                height: 3px;
                background: #2ed573;
                border-radius: 3px;
            }

            /* ========== CATALOGO (Griglia prodotti) ========== */
            .catalogo-grid,
            .catalogo {
                display: grid;
                grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
                gap: 2rem;
                margin: 2rem 0;
            }
            .prodotto-card,
            .prodotto {
                background: rgba(18, 22, 28, 0.8);
                backdrop-filter: blur(4px);
                border-radius: 24px;
                overflow: hidden;
                border: 1px solid rgba(46, 213, 115, 0.2);
                transition: transform 0.3s ease, border-color 0.2s;
                box-shadow: 0 10px 20px -8px rgba(0, 0, 0, 0.5);
            }
            .prodotto-card:hover,
            .prodotto:hover {
                transform: translateY(-6px);
                border-color: #2ed573;
                box-shadow: 0 20px 30px -12px rgba(46, 213, 115, 0.2);
            }
            .prodotto-card img,
            .prodotto img {
                width: 100%;
                height: 200px;
                object-fit: cover;
                background: #1a1f2a;
                transition: transform 0.4s;
            }
            .prodotto-card:hover img {
                transform: scale(1.02);
            }
            .prodotto-card .info,
            .prodotto .info {
                padding: 1.2rem;
            }
            .prodotto-card h3,
            .prodotto h3 {
                font-size: 1.2rem;
                font-weight: 600;
                margin-bottom: 0.5rem;
            }
            .prodotto-card h3 a,
            .prodotto h3 a {
                color: #ffffff;
                text-decoration: none;
            }
            .prodotto-card h3 a:hover {
                color: #2ed573;
            }
            .piattaforma {
                color: #8e9aaf;
                font-size: 0.8rem;
                text-transform: uppercase;
                letter-spacing: 0.5px;
                margin-bottom: 0.5rem;
            }
            .prezzo {
                font-size: 1.5rem;
                font-weight: 700;
                color: #2ed573;
                margin: 0.8rem 0;
            }
            .prodotto-card .btn,
            .prodotto .btn {
                width: 100%;
                padding: 0.6rem;
                font-size: 0.85rem;
            }

            /* ========== PAGINAZIONE ========== */
            .pagination {
                text-align: center;
                margin: 3rem 0;
            }
            .pagination a,
            .pagination span {
                display: inline-block;
                padding: 0.5rem 1rem;
                margin: 0 0.3rem;
                border-radius: 30px;
                background: rgba(255, 255, 255, 0.05);
                color: #b9c7d9;
                text-decoration: none;
                transition: all 0.2s;
            }
            .pagination a:hover {
                background: #2ed573;
                color: #0a0c10;
            }
            .pagination span.active {
                background: #2ed573;
                color: #0a0c10;
                font-weight: bold;
            }

            /* ========== TABELLE (carrello, ordini, dettagli) ========== */
            table {
                width: 100%;
                background: rgba(18, 22, 28, 0.7);
                border-radius: 20px;
                border-collapse: collapse;
                overflow: hidden;
                margin: 1.5rem 0;
            }
            th, td {
                padding: 1rem;
                text-align: left;
                border-bottom: 1px solid rgba(46, 213, 115, 0.2);
            }
            th {
                background: rgba(0, 0, 0, 0.4);
                color: #2ed573;
                font-weight: 600;
            }
            tr:hover {
                background: rgba(46, 213, 115, 0.05);
            }

            /* ========== FORM (login, registrazione, profilo) ========== */
            form {
                background: rgba(18, 22, 28, 0.7);
                backdrop-filter: blur(8px);
                padding: 2rem;
                border-radius: 32px;
                border: 1px solid rgba(46, 213, 115, 0.3);
                max-width: 500px;
                margin: 2rem auto;
            }
            form label {
                font-weight: 500;
                margin-top: 1rem;
                display: block;
                color: #b9c7d9;
            }
            form input, form select {
                width: 100%;
                padding: 0.8rem;
                margin-top: 0.3rem;
                background: #0a0c10;
                border: 1px solid #2c3e3a;
                border-radius: 16px;
                color: #fff;
                font-size: 1rem;
                transition: border 0.2s;
            }
            form input:focus, form select:focus {
                outline: none;
                border-color: #2ed573;
            }
            form input[type="submit"], form .btn {
                width: auto;
                margin-top: 1.5rem;
                background: #2ed573;
                color: #0a0c10;
                font-weight: bold;
                cursor: pointer;
            }
            form input[type="submit"]:hover {
                background: #3ee684;
            }

            /* Messaggi di errore / successo */
            .error {
                color: #ff6b6b;
                font-size: 0.85rem;
                margin-top: 0.3rem;
            }
            .success {
                color: #2ed573;
            }

            /* ========== FOOTER (stile di base, poi ridefinito in footer.jsp) ========== */
            footer {
                background: rgba(10, 14, 23, 0.9);
                border-top: 1px solid rgba(46, 213, 115, 0.2);
                text-align: center;
                padding: 1.5rem;
                margin-top: 3rem;
                font-size: 0.8rem;
                color: #8e9aaf;
            }

            /* ========== RICERCA INTERNA (suggerimenti) ========== */
            #searchInput {
                width: 100%;
                padding: 0.9rem 1.2rem;
                font-size: 1rem;
                background: #0a0c10;
                border: 1px solid #2ed573;
                border-radius: 60px;
                color: white;
                margin-bottom: 1rem;
            }
            #searchInput:focus {
                outline: none;
                box-shadow: 0 0 0 2px rgba(46, 213, 115, 0.5);
            }
            #suggestions {
                background: #12161c;
                border: 1px solid #2ed573;
                border-radius: 20px;
                margin-top: 0.2rem;
            }

            /* ========== RESPONSIVE ========== */
            @media (max-width: 768px) {
                .container {
                    padding: 0 1rem;
                }
                .catalogo-grid, .catalogo {
                    grid-template-columns: repeat(auto-fill, minmax(240px, 1fr));
                    gap: 1.5rem;
                }
                h2 {
                    font-size: 1.5rem;
                }
                form {
                    padding: 1.5rem;
                    margin: 1rem;
                }
                .btn {
                    padding: 0.5rem 1rem;
                }
            }
            @media (max-width: 480px) {
                .catalogo-grid, .catalogo {
                    grid-template-columns: 1fr;
                }
            }

            /* ========== HERO SECTION (usata in index.jsp) ========== */
            .hero {
                display: flex;
                flex-direction: column;
                justify-content: center;
                align-items: center;
                text-align: center;
                min-height: 70vh;
                background-image: linear-gradient(rgba(0,0,0,0.6), rgba(0,0,0,0.6)), url('https://r4.wallpaperflare.com/wallpaper/402/133/167/detroit-detroit-become-human-night-lights-helicopters-hd-wallpaper-18d6edd81060dc18e0cc118ee802948a.jpg');
                background-size: cover;
                background-position: center;
                border-radius: 32px;
                margin: 2rem 0;
                padding: 2rem;
            }
            .hero h1 {
                font-size: 3rem;
                margin-bottom: 1rem;
                color: white;
            }
            .hero p {
                font-size: 1.2rem;
                margin-bottom: 2rem;
                color: #e0e0e0;
                max-width: 600px;
            }
            .hero .btn {
                font-size: 1.1rem;
                padding: 0.8rem 2rem;
            }
            @media (max-width: 768px) {
                .hero h1 {
                    font-size: 2rem;
                }
                .hero p {
                    font-size: 1rem;
                }
            }

            /* ========== FILTRI PER CATALOGO ========== */
            .filters-bar {
                display: flex;
                gap: 2rem;
                background: rgba(18, 22, 28, 0.8);
                padding: 0.8rem 2rem;
                border-radius: 60px;
                margin-bottom: 2rem;
                flex-wrap: wrap;
                align-items: center;
                justify-content: center;
            }
            .filter-group {
                display: flex;
                align-items: center;
                gap: 0.8rem;
            }
            .filter-group label {
                font-weight: 500;
                color: #b9c7d9;
            }
            .filter-group select {
                background: #0a0c10;
                border: 1px solid #2ed573;
                color: white;
                padding: 0.4rem 1rem;
                border-radius: 40px;
                cursor: pointer;
                outline: none;
                transition: all 0.2s;
            }
            .filter-group select:hover {
                background: #2ed573;
                color: #0a0c10;
            }

            /* ========== BADGE PRODOTTO POPOLARE ========== */
            .card-badge {
                position: absolute;
                top: 12px;
                right: 12px;
                background: #e94560;
                color: white;
                font-size: 0.7rem;
                font-weight: bold;
                padding: 0.2rem 0.6rem;
                border-radius: 20px;
                z-index: 2;
                box-shadow: 0 2px 5px rgba(0,0,0,0.2);
            }
            .prodotto-card {
                position: relative;
            }
            .add-to-cart {
                transition: all 0.2s;
                width: 100%;
            }
            .add-to-cart:active {
                transform: scale(0.96);
            }

            /* Tabelle (doppia definizione, ma non danneggia) */
            table {
                width: 100%;
                background: rgba(18, 22, 28, 0.7);
                border-radius: 20px;
                border-collapse: collapse;
                overflow: hidden;
                margin: 1.5rem 0;
            }
            th, td {
                padding: 1rem;
                text-align: left;
                border-bottom: 1px solid rgba(46, 213, 115, 0.2);
            }
            th {
                background: rgba(0, 0, 0, 0.4);
                color: #2ed573;
                font-weight: 600;
            }
            tr:hover {
                background: rgba(46, 213, 115, 0.05);
            }

            /* Link per carrello con badge */
            .cart-link {
                position: relative;
            }
            .cart-badge {
                background: #e94560;
                color: white;
                font-size: 0.7rem;
                font-weight: bold;
                padding: 0.1rem 0.4rem;
                border-radius: 20px;
                min-width: 18px;
                text-align: center;
                margin-left: 6px;
                display: inline-block;
                line-height: 1.2;
            }

            /* Dropdown dei suggerimenti di ricerca */
            .suggestions-dropdown {
                position: absolute;
                top: 100%;
                left: 0;
                right: 0;
                background: #1e232c;
                border-radius: 20px;
                margin-top: 5px;
                z-index: 1000;
                display: none;
                max-height: 300px;
                overflow-y: auto;
                border: 1px solid #2ed573;
            }
            .suggestions-dropdown div {
                padding: 8px 12px;
                cursor: pointer;
                border-bottom: 1px solid #2c3e3a;
                color: white;
            }
            .suggestions-dropdown div:hover {
                background: #2ed573;
                color: #0a0c10;
            }

            a {
                text-decoration: none;
            }
        </style>
    </head>

    <body>
        <!-- HEADER (NAVIGAZIONE) -->
        <header>
            <div class="logo">
                <a href="<%= request.getContextPath() %>/index.jsp" class="icon-link">
                    <h1>Buy 4 Play</h1>
                </a>
                <p>Le migliori chiavi per i tuoi giochi</p>
            </div>

            <!-- Barra di ricerca con suggerimenti (AJAX) -->
            <div class="search-area" style="position: relative;">
                <input type="text" id="searchInputHeader" placeholder="Cerca gioco..." autocomplete="off">
                <button type="button" onclick="searchNow()">🔍</button>
                <div id="searchSuggestions" class="suggestions-dropdown"></div>
            </div>

            <!-- Area utente: mostra pulsanti diversi se loggato o meno -->
            <div class="user-actions">
                <% model.Utente utente = (model.Utente) session.getAttribute("utente"); %>
                <% if (utente != null) { %>
                    <a href="<%= request.getContextPath() %>/profilo" class="icon-link user-info">👤 <%= utente.getNome() %></a>
                    <% if ("admin".equals(utente.getRuolo())) { %>
                        <a href="<%= request.getContextPath() %>/admin/index.jsp" class="icon-link">🎛️ Pannello Admin</a>
                    <% } %>
                    <a href="<%= request.getContextPath() %>/catalogo" class="icon-link">📖 Catalogo</a>
                    <a href="<%= request.getContextPath() %>/carrello" class="icon-link cart-link">
                        🛒 Carrello
                        <span id="cart-count" class="cart-badge">0</span>
                    </a>
                    <a href="<%= request.getContextPath() %>/logout" class="icon-link">Logout</a>
                <% } else { %>
                    <a href="<%= request.getContextPath() %>/catalogo" class="icon-link">📖 Catalogo</a>
                    <a href="<%= request.getContextPath() %>/carrello" class="icon-link cart-link">
                        🛒 Carrello
                        <span id="cart-count" class="cart-badge">0</span>
                    </a>
                    <a href="<%= request.getContextPath() %>/login.jsp" class="icon-link">➜ Accedi</a>
                    <a href="<%= request.getContextPath() %>/registrazione.jsp" class="icon-link">📝 Registrati</a>
                <% } %>
            </div>
        </header>

        <div class="container">
            <!-- Il contenuto principale viene inserito qui (tramite include delle altre JSP) -->
            <script>
                // Funzione per aggiornare il badge del carrello (numero di articoli)
                function updateCartCount() {
                    fetch(contextPath + '/carrello?action=count', {
                        method: 'GET',
                        headers: { 'X-Requested-With': 'XMLHttpRequest' }
                    })
                    .then(response => response.json())
                    .then(data => {
                        var badge = document.getElementById('cart-count');
                        if (badge) badge.innerText = data.count;
                    })
                    .catch(err => console.error('updateCartCount error:', err));
                }

                // Aggiorna il badge al caricamento e ogni 5 secondi (per sicurezza)
                document.addEventListener('DOMContentLoaded', updateCartCount);
                setInterval(updateCartCount, 5000);

                // Gestione della barra di ricerca con suggerimenti (tipo Google Suggest)
                const searchInput = document.getElementById('searchInputHeader');
                const suggestionsDiv = document.getElementById('searchSuggestions');
                const contextPath = "<%= request.getContextPath() %>";

                // Mostra suggerimenti durante la digitazione (AJAX)
                searchInput.addEventListener('input', function() {
                    const query = this.value.trim();
                    if (query.length < 2) {
                        suggestionsDiv.style.display = 'none';
                        return;
                    }
                    fetch(contextPath + '/SearchProduct?q=' + encodeURIComponent(query))
                        .then(response => response.json())
                        .then(data => {
                            if (data.length > 0) {
                                suggestionsDiv.innerHTML = '';
                                data.forEach(product => {
                                    const div = document.createElement('div');
                                    div.textContent = product.nome + ' - €' + product.prezzo;
                                    div.onclick = () => {
                                        window.location.href = contextPath + '/dettaglio?id=' + product.id;
                                    };
                                    suggestionsDiv.appendChild(div);
                                });
                                suggestionsDiv.style.display = 'block';
                            } else {
                                suggestionsDiv.innerHTML = '<div style="cursor:default;">Nessun risultato</div>';
                                suggestionsDiv.style.display = 'block';
                            }
                        })
                        .catch(err => console.error(err));
                });

                // Invio della ricerca premendo Invio
                searchInput.addEventListener('keypress', function(e) {
                    if (e.key === 'Enter') {
                        const query = searchInput.value.trim();
                        if (query.length > 0) {
                            window.location.href = contextPath + '/catalogo?search=' + encodeURIComponent(query);
                        }
                    }
                });

                // Pulsante di ricerca (lente)
                function searchNow() {
                    const query = searchInput.value.trim();
                    if (query.length > 0) {
                        window.location.href = contextPath + '/catalogo?search=' + encodeURIComponent(query);
                    }
                }

                // Chiude il dropdown dei suggerimenti se si clicca fuori
                document.addEventListener('click', function(e) {
                    if (!searchInput.contains(e.target) && !suggestionsDiv.contains(e.target)) {
                        suggestionsDiv.style.display = 'none';
                    }
                });
            </script>