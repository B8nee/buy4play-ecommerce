<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, model.Prodotto" %>

<%-- 
    ============================================================================
    Pagina per la gestione del catalogo prodotti (area amministrativa).
    Funzionalità:
      - Visualizzazione tabellare dei prodotti con paginazione
      - Aggiunta di un nuovo prodotto (modale AJAX)
      - Modifica di un prodotto esistente (modale AJAX con caricamento dati)
      - Eliminazione di un prodotto (con conferma)
    ============================================================================
--%>

<%
    // ------------------------------------------------------------------------
    // 1. Controllo di sicurezza: solo utenti con ruolo "admin" possono accedere
    // ------------------------------------------------------------------------
    model.Utente admin = (model.Utente) session.getAttribute("utente");
    if (admin == null || !"admin".equals(admin.getRuolo())) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Gestione Prodotti - Buy4Play Admin</title>
        <style>
            /* 
                Stili per la sezione admin (coerenti con il tema dark del sito)
                - .admin-container: card principale
                - .product-table: tabella prodotti
                - .modal: finestra modale per aggiunta/modifica
                - .pagination: controlli di navigazione tra pagine
            */
            .admin-container {
                background: rgba(18, 22, 28, 0.7);
                border-radius: 32px;
                padding: 2rem;
                margin: 2rem 0;
            }
            .admin-title {
                font-size: 1.8rem;
                margin-bottom: 0.5rem;
                border-left: 4px solid #2ed573;
                padding-left: 1rem;
            }
            .btn-add {
                background: #2ed573;
                margin-left: 1.5rem;
                margin-bottom: 2rem;
                display: inline-block;
            }
            .product-table {
                width: 100%;
                background: #0a0c10;
                border-radius: 20px;
                overflow: hidden;
            }
            .product-table th, .product-table td {
                padding: 1rem;
                text-align: left;
                border-bottom: 1px solid #2c3e3a;
            }
            .product-table th {
                background: #1e232c;
                color: #2ed573;
            }
            .product-table tr:hover {
                background: #1a1f2a;
            }
            .action-buttons {
                display: flex;
                gap: 0.5rem;
            }
            .btn-edit, .btn-delete {
                padding: 0.3rem 1rem;
                border-radius: 30px;
                font-size: 0.8rem;
                text-decoration: none;
                transition: 0.2s;
            }
            .btn-edit {
                background: #ffc107;
                color: #0a0c10;
            }
            .btn-delete {
                background: #e94560;
                color: white;
            }
            .btn-edit:hover {
                background: #e0a800;
            }
            .btn-delete:hover {
                background: #c5314b;
            }
            /* Modale per l'aggiunta/modifica prodotto (si apre al click dei pulsanti) */
            .modal {
                display: none;
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: rgba(0,0,0,0.7);
                justify-content: center;
                align-items: center;
                z-index: 1000;
            }
            .modal-content {
                background: #12161c;
                border-radius: 32px;
                padding: 2rem;
                width: 500px;
                max-width: 90%;
                border: 1px solid #2ed573;
            }
            .modal-content input, .modal-content textarea {
                width: 100%;
                margin-bottom: 1rem;
            }
            .close-modal {
                float: right;
                cursor: pointer;
                font-size: 1.5rem;
                color: #b9c7d9;
            }
            /* Riga per la checkbox "Prodotto popolare" centrata orizzontalmente */
            .checkbox-row {
                display: flex;
                justify-content: center;
                align-items: center;
                gap: 1rem;
                margin: 1rem 0 2rem 0;
            }
            .checkbox-row label {
                margin: 0;
                cursor: pointer;
                font-weight: normal;
            }
            .checkbox-row input {
                width: auto;
                margin: 0;
            }
            /* Pulsante "Salva" centrato nella modale (forzato con !important per sicurezza) */
            .btn-save {
                display: block !important;
                width: fit-content !important;
                margin-left: auto !important;
                margin-right: auto !important;
                float: none !important;
                text-align: center !important;
            }
            /* Stili per la paginazione (sotto la tabella) */
            .pagination {
                text-align: center;
                margin: 2rem 0;
            }
            .pagination a, .pagination span {
                display: inline-block;
                padding: 0.5rem 1rem;
                margin: 0 0.3rem;
                border-radius: 30px;
                background: rgba(255,255,255,0.05);
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
        </style>
    </head>
    <body>
    <%@ include file="../header.jsp" %>  <!-- include dell'header comune a tutto il sito -->

    <div class="admin-container">
        <h2 class="admin-title">📦 Gestione Catalogo Prodotti</h2>
        <!-- Pulsante che apre la modale in modalità "aggiungi" -->
        <button class="btn btn-add" onclick="openModal()">➕ Aggiungi nuovo prodotto</button>

        <!-- Tabella per l'elenco dei prodotti -->
        <table class="product-table">
            <thead>
                <tr><th>ID</th><th>Nome</th><th>Piattaforma</th><th>Prezzo</th><th>Popolare</th><th>Azioni</th></tr>
            </thead>
            <tbody>
                <%
                    // --------------------------------------------------------------------
                    // 2. Caricamento dei prodotti dalla request (impostati dalla servlet)
                    //    La servlet passa anche currentPage e totalPages per la paginazione.
                    // --------------------------------------------------------------------
                    List<Prodotto> prodotti = (List<Prodotto>) request.getAttribute("prodotti");
                    if (prodotti != null && !prodotti.isEmpty()) {
                        for (Prodotto p : prodotti) {
                %>
                <tr>
                    <td><%= p.getId() %></td>
                    <td><%= p.getNome() %></td>
                    <td><%= p.getPiattaforma() %></td>
                    <td>&euro; <%= String.format("%.2f", p.getPrezzo()) %></td>
                    <td><%= p.isPopolare() ? "🔥 Sì" : "No" %></td>
                    <td class="action-buttons">
                        <!-- Modifica: apre la modale e carica i dati del prodotto via AJAX -->
                        <a href="javascript:void(0)" onclick="openEditModal(<%= p.getId() %>)" class="btn-edit">Modifica</a>
                        <!-- Elimina: invia richiesta GET con action=delete e id -->
                        <a href="<%= request.getContextPath() %>/admin/GestioneProdotti?action=delete&id=<%= p.getId() %>" class="btn-delete" onclick="return confirm('Eliminare questo prodotto?')">Elimina</a>
                    </td>
                </tr>
                <%      }
                    } else { %>
                <tr><td colspan="6">Nessun prodotto trovato.</tr>
                <% } %>
            </tbody>
        </table>

        <!-- ------------------------------------------------------------------------
            Sezione di paginazione: viene visualizzata solo se ci sono più pagine
            I link puntano alla servlet con il parametro page (la servlet gestisce offset/limit)
        ------------------------------------------------------------------------- -->
        <%
            Integer currentPage = (Integer) request.getAttribute("currentPage");
            Integer totalPages = (Integer) request.getAttribute("totalPages");
            if (totalPages != null && totalPages > 1) {
        %>
        <div class="pagination">
            <% if (currentPage > 1) { %>
                <a href="<%= request.getContextPath() %>/admin/GestioneProdotti?page=<%= currentPage-1 %>">« Precedente</a>
            <% } %>
            <% for (int p = 1; p <= totalPages; p++) { %>
                <% if (p == currentPage) { %>
                    <span class="active"><%= p %></span>
                <% } else { %>
                    <a href="<%= request.getContextPath() %>/admin/GestioneProdotti?page=<%= p %>"><%= p %></a>
                <% } %>
            <% } %>
            <% if (currentPage < totalPages) { %>
                <a href="<%= request.getContextPath() %>/admin/GestioneProdotti?page=<%= currentPage+1 %>">Successivo »</a>
            <% } %>
        </div>
        <% } %>
    </div>

    <!-- ------------------------------------------------------------------------
        Modale per l'aggiunta e la modifica dei prodotti.
        Viene riutilizzata per entrambe le operazioni:
        - "action=add" (default) per nuovo prodotto
        - "action=update" + id per modifica (i campi vengono prepopolati via AJAX)
    ------------------------------------------------------------------------ -->
    <div id="productModal" class="modal">
        <div class="modal-content">
            <span class="close-modal" onclick="closeModal()">&times;</span>
            <h3 id="modalTitle">Aggiungi Prodotto</h3>
            <form id="productForm" action="<%= request.getContextPath() %>/admin/GestioneProdotti" method="post">
                <input type="hidden" name="action" id="formAction" value="add">
                <input type="hidden" name="id" id="productId">
                <label>Nome:</label>
                <input type="text" name="nome" id="nome" required>
                <label>Piattaforma:</label>
                <input type="text" name="piattaforma" id="piattaforma" required>
                <label>Prezzo (€):</label>
                <input type="number" step="0.01" name="prezzo" id="prezzo" required>
                <label>Immagine URL:</label>
                <input type="text" name="immagineUrl" id="immagineUrl">
                <div class="checkbox-row">
                    <label for="popolare">🔥 Prodotto popolare</label>
                    <input type="checkbox" name="popolare" id="popolare">
                </div>
                <input type="submit" value="Salva" class="btn btn-save">
            </form>
        </div>
    </div>

    <script>
        // ----------------------------------------------------------------
        // Funzioni JavaScript per il controllo della modale e AJAX
        // ----------------------------------------------------------------

        // Apre la modale in modalità "aggiungi" (campi vuoti, formAction=add)
        function openModal() {
            document.getElementById('modalTitle').innerText = 'Aggiungi Prodotto';
            document.getElementById('formAction').value = 'add';
            document.getElementById('productId').value = '';
            document.getElementById('nome').value = '';
            document.getElementById('piattaforma').value = '';
            document.getElementById('prezzo').value = '';
            document.getElementById('immagineUrl').value = '';
            document.getElementById('popolare').checked = false;
            document.getElementById('productModal').style.display = 'flex';
        }

        // Apre la modale in modalità "modifica" e carica i dati del prodotto via AJAX
        // La servlet risponde con un JSON contenente i campi del prodotto.
        function openEditModal(id) {
            fetch('<%= request.getContextPath() %>/admin/GestioneProdotti?action=get&id=' + id)
                .then(response => response.json())
                .then(product => {
                    document.getElementById('modalTitle').innerText = 'Modifica Prodotto';
                    document.getElementById('formAction').value = 'update';
                    document.getElementById('productId').value = product.id;
                    document.getElementById('nome').value = product.nome;
                    document.getElementById('piattaforma').value = product.piattaforma;
                    document.getElementById('prezzo').value = product.prezzo;
                    document.getElementById('immagineUrl').value = product.immagineUrl;
                    document.getElementById('popolare').checked = product.popolare;
                    document.getElementById('productModal').style.display = 'flex';
                });
        }

        // Chiude la modale
        function closeModal() {
            document.getElementById('productModal').style.display = 'none';
        }
    </script>

    <%@ include file="../footer.jsp" %>
    </body>
</html>