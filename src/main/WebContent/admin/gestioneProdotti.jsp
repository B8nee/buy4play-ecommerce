<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, model.Prodotto" %>
<%
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
        .admin-container {
            background: rgba(18, 22, 28, 0.7);
            border-radius: 32px;
            padding: 2rem;
            margin: 2rem 0;
        }
        .admin-title {
            font-size: 1.8rem;
            margin-bottom: 1.5rem;
            border-left: 4px solid #2ed573;
            padding-left: 1rem;
        }
        .btn-add {
            background: #2ed573;
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
    </style>
</head>
<body>
<%@ include file="../header.jsp" %>

<div class="admin-container">
    <h2 class="admin-title">📦 Gestione Catalogo Prodotti</h2>
    <button class="btn btn-add" onclick="openModal()">➕ Aggiungi nuovo prodotto</button>

    <table class="product-table">
        <thead>
            <tr><th>ID</th><th>Nome</th><th>Piattaforma</th><th>Prezzo</th><th>Popolare</th><th>Azioni</th></tr>
        </thead>
        <tbody>
            <%
                List<Prodotto> prodotti = (List<Prodotto>) request.getAttribute("prodotti");
                if (prodotti != null) {
                    for (Prodotto p : prodotti) {
            %>
            <tr>
                <td><%= p.getId() %></td>
                <td><%= p.getNome() %></td>
                <td><%= p.getPiattaforma() %></td>
                <td>&euro; <%= String.format("%.2f", p.getPrezzo()) %></td>
                <td><%= p.isPopolare() ? "🔥 Sì" : "No" %></td>
                <td class="action-buttons">
                    <a href="javascript:void(0)" onclick="openEditModal(<%= p.getId() %>)" class="btn-edit">Modifica</a>
                    <a href="GestioneProdotti?action=delete&id=<%= p.getId() %>" class="btn-delete" onclick="return confirm('Eliminare questo prodotto?')">Elimina</a>
                </td>
            </tr>
            <%      }
                } else { %>
            <tr><td colspan="6">Nessun prodotto trovato.</td></tr>
            <% } %>
        </tbody>
    </table>
</div>

<div id="productModal" class="modal">
    <div class="modal-content">
        <span class="close-modal" onclick="closeModal()">&times;</span>
        <h3 id="modalTitle">Aggiungi Prodotto</h3>
        <form id="productForm" action="GestioneProdotti" method="post">
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
            <label>
                <input type="checkbox" name="popolare" id="popolare"> 🔥 Prodotto popolare
            </label>
            <input type="submit" value="Salva" class="btn">
        </form>
    </div>
</div>

<script>
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
    function openEditModal(id) {
        fetch('GestioneProdotti?action=get&id=' + id)
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
    function closeModal() {
        document.getElementById('productModal').style.display = 'none';
    }
</script>

<%@ include file="../footer.jsp" %>
</body>
</html>