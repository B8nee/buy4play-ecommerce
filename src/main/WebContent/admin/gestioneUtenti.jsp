<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, model.Utente" %>

<%-- 
    Pagina per la gestione degli utenti da parte dell'amministratore.
    Mostra una tabella con tutti gli utenti registrati, consente di:
    - Modificare il ruolo (cliente/admin) tramite select e pulsante AJAX.
    - Eliminare un utente (con conferma) - l'admin non può eliminare se stesso.
--%>

<%
    // Verifica che l'utente corrente sia amministratore, altrimenti reindirizza al login
    Utente admin = (Utente) session.getAttribute("utente");
    if (admin == null || !"admin".equals(admin.getRuolo())) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Gestione Utenti - Buy4Play Admin</title>
        <style>
            /* Stili specifici per la tabella degli utenti */
            .admin-container {
                background: rgba(18, 22, 28, 0.7);
                border-radius: 32px;
                padding: 2rem;
                margin: 2rem 0;
            }
            .users-table {
                width: 100%;
                background: #0a0c10;
                border-radius: 20px;
                overflow-x: auto;
            }
            .users-table th, .users-table td {
                padding: 1rem;
                text-align: left;
                border-bottom: 1px solid #2c3e3a;
            }
            .users-table th {
                background: #1e232c;
                color: #2ed573;
            }
            .role-select {
                background: #0a0c10;
                border: 1px solid #2ed573;
                color: white;
                padding: 0.3rem;
                border-radius: 20px;
            }
            .btn-role, .btn-delete {
                background: #2ed573;
                padding: 0.2rem 0.6rem;
                border-radius: 20px;
                color: #0a0c10;
                text-decoration: none;
                font-size: 0.7rem;
                border: none;
                cursor: pointer;
            }
            .btn-delete {
                background: #e94560;
                color: white;
            }
            .btn-delete:hover {
                background: #c5314b;
            }
        </style>
    </head>
    <body>
    <%@ include file="../header.jsp" %>

    <div class="admin-container">
        <h2 class="admin-title">👥 Gestione Utenti</h2>

        <div style="overflow-x: auto;">
            <table class="users-table">
                <thead>
                    <tr><th>ID</th><th>Email</th><th>Nome</th><th>Cognome</th><th>Ruolo</th><th>Azioni</th></tr>
                </thead>
                <tbody>
                    <%
                        // Recupera la lista degli utenti passata dalla servlet
                        List<Utente> utenti = (List<Utente>) request.getAttribute("utenti");
                        if (utenti != null && !utenti.isEmpty()) {
                            for (Utente u : utenti) {
                    %>
                    <tr>
                        <td><%= u.getId() %></td>
                        <td><%= u.getEmail() %></td>
                        <td><%= u.getNome() %></td>
                        <td><%= u.getCognome() %></td>
                        <td>
                            <!-- Select per scegliere il ruolo (valore attuale selezionato) -->
                            <select class="role-select" data-id="<%= u.getId() %>">
                                <option value="cliente" <%= "cliente".equals(u.getRuolo()) ? "selected" : "" %>>Cliente</option>
                                <option value="admin" <%= "admin".equals(u.getRuolo()) ? "selected" : "" %>>Amministratore</option>
                            </select>
                        </td>
                        <td>
                            <!-- Pulsante per aggiornare il ruolo tramite AJAX -->
                            <button class="btn-role" onclick="updateRole(<%= u.getId() %>)">Aggiorna ruolo</button>
                            <!-- Pulsante elimina: nascosto se l'utente è l'admin stesso -->
                            <% if (u.getId() != admin.getId()) { %>
                                <button class="btn-delete" onclick="deleteUser(<%= u.getId() %>)">Elimina</button>
                            <% } else { %>
                                <span style="color:#888;">(tu)</span>
                            <% } %>
                        </td>
                    </tr>
                    <%      }
                        } else { %>
                    <tr><td colspan="6">Nessun utente trovato.</td></tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>

    <script>
        // Funzione per aggiornare il ruolo di un utente (chiamata AJAX)
        function updateRole(userId) {
            var select = document.querySelector('.role-select[data-id="' + userId + '"]');
            var newRole = select.value;
            fetch('<%= request.getContextPath() %>/admin/gestioneUtenti?action=updateRole&id=' + userId + '&ruolo=' + newRole, {
                method: 'POST',
                headers: { 'X-Requested-With': 'XMLHttpRequest' }
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert('Ruolo aggiornato con successo');
                    location.reload();   // Ricarica la pagina per aggiornare la tabella
                } else {
                    alert('Errore: ' + data.message);
                }
            })
            .catch(err => alert('Errore di comunicazione'));
        }

        // Funzione per eliminare un utente (chiamata AJAX con conferma)
        function deleteUser(userId) {
            if (confirm('Eliminare definitivamente questo utente? Verranno cancellati anche i suoi ordini (in cascata).')) {
                fetch('<%= request.getContextPath() %>/admin/gestioneUtenti?action=delete&id=' + userId, {
                    method: 'POST',
                    headers: { 'X-Requested-With': 'XMLHttpRequest' }
                })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        alert('Utente eliminato');
                        location.reload();
                    } else {
                        alert('Errore: ' + data.message);
                    }
                })
                .catch(err => alert('Errore di comunicazione'));
            }
        }
    </script>

    <%@ include file="../footer.jsp" %>
    </body>
</html>