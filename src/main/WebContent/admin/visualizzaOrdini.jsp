<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, model.Ordine, model.Utente, java.text.SimpleDateFormat" %>
<%
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
    <title>Gestione Ordini - Buy4Play Admin</title>
    <style>
        .admin-container {
            background: rgba(18, 22, 28, 0.7);
            border-radius: 32px;
            padding: 2rem;
            margin: 2rem 0;
        }
        .filters {
            display: flex;
            gap: 1rem;
            margin-bottom: 2rem;
            flex-wrap: wrap;
            align-items: flex-end;
        }
        .filter-group {
            display: flex;
            flex-direction: column;
            gap: 0.3rem;
        }
        .filter-group label {
            font-size: 0.8rem;
            color: #b9c7d9;
        }
        .filter-group input, .filter-group select {
            background: #0a0c10;
            border: 1px solid #2ed573;
            padding: 0.5rem;
            border-radius: 20px;
            color: white;
        }
        .orders-table {
            width: 100%;
            background: #0a0c10;
            border-radius: 20px;
            overflow-x: auto;
        }
        .orders-table th, .orders-table td {
            padding: 1rem;
            text-align: left;
            border-bottom: 1px solid #2c3e3a;
        }
        .orders-table th {
            background: #1e232c;
            color: #2ed573;
        }
        .status-select {
            background: #0a0c10;
            border: 1px solid #2ed573;
            color: white;
            padding: 0.3rem;
            border-radius: 20px;
        }
        .btn-status, .btn-detail, .btn-delete-order {
            padding: 0.3rem 0.8rem;
            border-radius: 20px;
            text-decoration: none;
            font-size: 0.7rem;
            border: none;
            cursor: pointer;
            transition: 0.2s;
        }
        .btn-status {
            background: #2ed573;
            color: #0a0c10;
        }
        .btn-detail {
            background: #2ed573;
            color: #0a0c10;
            text-decoration: none;
            display: inline-block;
        }
        .btn-delete-order {
            background: #e94560;
            color: white;
        }
        .btn-delete-order:hover {
            background: #c5314b;
        }
        .status-badge {
            background: #ffc107;
            color: #0a0c10;
            padding: 0.2rem 0.6rem;
            border-radius: 20px;
            font-size: 0.7rem;
            font-weight: bold;
            display: inline-block;
        }
        .status-consegnato { background: #2ed573; }
        .status-in_lavorazione { background: #ffc107; }
        .status-spedito { background: #17a2b8; }
    </style>
</head>
<body>
<%@ include file="../header.jsp" %>

<div class="admin-container">
    <h2 class="admin-title">📋 Gestione Ordini</h2>

    <form method="get" action="VisualizzaOrdini" class="filters" id="filterForm">
        <div class="filter-group">
            <label>Data da:</label>
            <input type="date" name="dataDa" value="<%= request.getParameter("dataDa") != null ? request.getParameter("dataDa") : "" %>">
        </div>
        <div class="filter-group">
            <label>Data a:</label>
            <input type="date" name="dataA" value="<%= request.getParameter("dataA") != null ? request.getParameter("dataA") : "" %>">
        </div>
        <div class="filter-group">
            <label>Cliente (email):</label>
            <input type="text" name="clienteEmail" placeholder="email cliente" value="<%= request.getParameter("clienteEmail") != null ? request.getParameter("clienteEmail") : "" %>">
        </div>
        <div class="filter-group">
            <label>Stato:</label>
            <select name="stato">
                <option value="">Tutti</option>
                <option value="in_lavorazione" <%= "in_lavorazione".equals(request.getParameter("stato")) ? "selected" : "" %>>In lavorazione</option>
                <option value="spedito" <%= "spedito".equals(request.getParameter("stato")) ? "selected" : "" %>>Spedito</option>
                <option value="consegnato" <%= "consegnato".equals(request.getParameter("stato")) ? "selected" : "" %>>Consegnato</option>
            </select>
        </div>
        <div class="filter-group">
            <button type="submit" class="btn">Filtra</button>
            <a href="VisualizzaOrdini" class="btn-outline" style="display: inline-block; padding: 0.5rem 1rem;">Reset</a>
        </div>
    </form>

    <div style="overflow-x: auto;">
        <table class="orders-table">
            <thead>
                <tr>
                    <th>ID</th><th>Cliente</th><th>Email</th><th>Data</th><th>Totale</th><th>Stato</th><th>Azioni</th><th>Elimina</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Ordine> ordini = (List<Ordine>) request.getAttribute("ordini");
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    if (ordini != null && !ordini.isEmpty()) {
                        for (Ordine o : ordini) {
                            String statoClasse = "";
                            if ("consegnato".equals(o.getStato())) statoClasse = "status-consegnato";
                            else if ("spedito".equals(o.getStato())) statoClasse = "status-spedito";
                            else statoClasse = "status-in_lavorazione";
                %>
                <tr>
                    <td><%= o.getId() %></td>
                    <td><%= o.getUtente().getNome() + " " + o.getUtente().getCognome() %></td>
                    <td><%= o.getUtente().getEmail() %></td>
                    <td><%= sdf.format(o.getDataOrdine()) %></td>
                    <td>&euro; <%= String.format("%.2f", o.getTotale()) %></td>
                    <td>
                        <span class="status-badge <%= statoClasse %>"><%= o.getStato() != null ? o.getStato().replace("_", " ") : "In lavorazione" %></span>
                    </td>
                    <td>
                        <a href="DettaglioOrdineAdmin?id=<%= o.getId() %>" class="btn-detail">Dettagli</a>
                        <select class="status-select" data-id="<%= o.getId() %>">
                            <option value="in_lavorazione" <%= "in_lavorazione".equals(o.getStato()) ? "selected" : "" %>>In lavorazione</option>
                            <option value="spedito" <%= "spedito".equals(o.getStato()) ? "selected" : "" %>>Spedito</option>
                            <option value="consegnato" <%= "consegnato".equals(o.getStato()) ? "selected" : "" %>>Consegnato</option>
                        </select>
                        <button class="btn-status" onclick="updateStatus(<%= o.getId() %>)">Aggiorna</button>
                    </td>
                    <td>
                        <button class="btn-delete-order" data-id="<%= o.getId() %>">🗑️ Elimina</button>
                    </td>
                </tr>
                <%      }
                    } else { %>
                <tr><td colspan="8">Nessun ordine trovato.</td></tr>
                <% } %>
            </tbody>
        </table>
    </div>
</div>

<script>
    function updateStatus(orderId) {
        var select = document.querySelector('.status-select[data-id="' + orderId + '"]');
        var newStatus = select.value;
        fetch('<%= request.getContextPath() %>/admin/VisualizzaOrdini?action=updateStatus&id=' + orderId + '&stato=' + newStatus, {
            method: 'POST',
            headers: { 'X-Requested-With': 'XMLHttpRequest' }
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                location.reload();
            } else {
                alert('Errore durante l\'aggiornamento: ' + data.message);
            }
        })
        .catch(err => alert('Errore di comunicazione'));
    }

    document.querySelectorAll('.btn-delete-order').forEach(btn => {
        btn.addEventListener('click', function() {
            const orderId = this.getAttribute('data-id');
            if (confirm('Eliminare definitivamente l\'ordine #' + orderId + '? Verranno rimossi anche i dettagli.')) {
                fetch('<%= request.getContextPath() %>/admin/VisualizzaOrdini?action=deleteOrder&id=' + orderId, {
                    method: 'POST',
                    headers: { 'X-Requested-With': 'XMLHttpRequest' }
                })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        alert('Ordine eliminato');
                        location.reload();
                    } else {
                        alert('Errore: ' + (data.message || 'operazione fallita'));
                    }
                })
                .catch(err => alert('Errore di comunicazione'));
            }
        });
    });
</script>

<%@ include file="../footer.jsp" %>
</body>
</html>