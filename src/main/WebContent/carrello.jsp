<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header.jsp" %>
<%@ page import="model.Cart, model.CartItem, model.Prodotto" %>
<%
    Cart cart = (Cart) session.getAttribute("cart");
    if (cart == null) cart = new Cart();
%>
<div class="cart-container">
    <h2>🛒 Il tuo carrello</h2>

    <% if (cart.isEmpty()) { %>
        <div class="cart-empty">
            <div class="empty-icon">🛒</div>
            <p>Il tuo carrello è vuoto</p>
            <p class="empty-sub">Sfoglia il catalogo e aggiungi i tuoi giochi preferiti!</p>
            <a href="catalogo" class="btn">Continua gli acquisti</a>
        </div>
    <% } else { %>
        <div class="cart-items">
            <table class="cart-table">
                <thead>
                    <tr><th>Prodotto</th><th>Prezzo</th><th>Quantità</th><th>Subtotale</th><th></th></tr>
                </thead>
                <tbody>
                    <% for (CartItem item : cart.getItems()) { 
                        Prodotto p = item.getProdotto();
                    %>
                    <tr id="cart-item-<%= p.getId() %>">
                        <td><%= p.getNome() %></td>
                        <td class="cart-price" data-price="<%= p.getPrezzo() %>">&euro; <%= String.format("%.2f", p.getPrezzo()) %></td>
                        <td>
                            <div class="quantity-control">
                                <button class="qty-btn minus" data-id="<%= p.getId() %>">-</button>
                                <span id="qty-<%= p.getId() %>" class="qty-value"><%= item.getQuantita() %></span>
                                <button class="qty-btn plus" data-id="<%= p.getId() %>">+</button>
                            </div>
                        </td>
                        <td class="cart-subtotal" id="subtotal-<%= p.getId() %>">&euro; <%= String.format("%.2f", item.getSubtotale()) %></td>
                        <td><button class="btn-remove" data-id="<%= p.getId() %>">🗑️ Rimuovi</button></td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
            <div class="cart-total">
                <strong>Totale:</strong> <span id="cart-total">&euro; <%= String.format("%.2f", cart.getTotal()) %></span>
            </div>
            <div class="cart-actions">
                <a href="catalogo" class="btn">Continua acquisti</a>
                <button id="clear-cart" class="btn btn-outline">Svuota carrello</button>
                <a href="checkout" class="btn">Procedi al checkout</a>
            </div>
        </div>
    <% } %>
</div>

<style>
    .cart-container {
        max-width: 1200px;
        margin: 2rem auto;
        background: rgba(18, 22, 28, 0.7);
        border-radius: 32px;
        padding: 2rem;
    }
    .cart-table {
        width: 100%;
        border-collapse: collapse;
    }
    .cart-table th, .cart-table td {
        padding: 1rem;
        text-align: left;
        border-bottom: 1px solid rgba(46, 213, 115, 0.2);
    }
    .cart-table th {
        color: #2ed573;
    }
    .quantity-control {
        display: flex;
        align-items: center;
        gap: 0.5rem;
    }
    .qty-btn {
        background: #2ed573;
        color: #0a0c10;
        border: none;
        width: 30px;
        height: 30px;
        border-radius: 50%;
        font-weight: bold;
        cursor: pointer;
        transition: 0.2s;
    }
    .qty-btn:hover {
        background: #3ee684;
    }
    .qty-value {
        min-width: 30px;
        text-align: center;
        font-weight: bold;
    }
    .btn-remove {
        background: #e94560;
        color: white;
        border: none;
        padding: 0.3rem 0.8rem;
        border-radius: 20px;
        cursor: pointer;
        transition: 0.2s;
    }
    .btn-remove:hover {
        background: #c5314b;
    }
    .cart-total {
        text-align: right;
        font-size: 1.5rem;
        margin-top: 2rem;
        padding-top: 1rem;
        border-top: 1px solid rgba(46, 213, 115, 0.2);
    }
    .cart-actions {
        display: flex;
        justify-content: space-between;
        margin-top: 2rem;
        gap: 1rem;
        flex-wrap: wrap;
    }
    .cart-empty {
        text-align: center;
        padding: 3rem 2rem;
    }
    .empty-icon {
        font-size: 4rem;
        margin-bottom: 1rem;
        opacity: 0.6;
    }
    .cart-empty p {
        font-size: 1.2rem;
        margin-bottom: 0.5rem;
    }
    .cart-empty .empty-sub {
        font-size: 0.9rem;
        color: #b9c7d9;
        margin-bottom: 2rem;
    }
    .cart-empty .btn {
        margin-top: 0.5rem;
    }
    .feedback {
        position: fixed;
        bottom: 20px;
        right: 20px;
        background: #2ed573;
        color: #0a0c10;
        padding: 0.8rem 1.2rem;
        border-radius: 40px;
        font-weight: bold;
        z-index: 1000;
        opacity: 0;
        transition: opacity 0.3s;
    }
</style>

<script>
    function showFeedback(message, isError = false) {
        let fb = document.getElementById('cart-feedback');
        if (!fb) {
            fb = document.createElement('div');
            fb.id = 'cart-feedback';
            fb.className = 'feedback';
            document.body.appendChild(fb);
        }
        fb.textContent = message;
        fb.style.backgroundColor = isError ? '#e94560' : '#2ed573';
        fb.style.color = isError ? 'white' : '#0a0c10';
        fb.style.opacity = '1';
        setTimeout(() => { fb.style.opacity = '0'; }, 2000);
    }

    function updateTotal() {
        let subtotals = document.querySelectorAll('.cart-subtotal');
        let total = 0;
        subtotals.forEach(el => {
            let val = el.textContent.replace('€', '').replace('&euro;', '').trim();
            total += parseFloat(val);
        });
        document.getElementById('cart-total').innerHTML = '€ ' + total.toFixed(2);
    }

    function updateItemUI(productId, newQuantity, newSubtotal) {
        document.getElementById('qty-' + productId).innerText = newQuantity;
        document.getElementById('subtotal-' + productId).innerHTML = '€ ' + newSubtotal.toFixed(2);
        updateTotal();
        if (typeof updateCartCount === 'function') updateCartCount();
    }

    function removeItemUI(productId) {
        const row = document.getElementById('cart-item-' + productId);
        row.remove();
        updateTotal();
        if (typeof updateCartCount === 'function') updateCartCount();
        let tableBody = document.querySelector('.cart-table tbody');
        if (tableBody.children.length === 0) {
            location.reload();
        }
    }

    function updateCart(action, productId, quantity) {
        let params = new URLSearchParams();
        params.append('action', action);
        if (productId) params.append('id', productId);
        if (quantity !== undefined) params.append('qty', quantity);
        
        fetch('carrello?' + params.toString(), {
            method: 'GET',
            headers: { 'X-Requested-With': 'XMLHttpRequest' }
        })
        .then(response => {
            if (!response.ok) throw new Error('HTTP error ' + response.status);
            return response.json();
        })
        .then(data => {
            if (data.success) {
                if (action === 'update') {
                    updateItemUI(productId, data.newQuantity, data.newSubtotal);
                    showFeedback('Quantità aggiornata');
                } else if (action === 'remove') {
                    removeItemUI(productId);
                    showFeedback('Prodotto rimosso');
                } else if (action === 'clear') {
                    location.reload();
                } else if (action === 'add') {
                    location.reload();
                }
            } else {
                showFeedback('Errore: ' + (data.message || 'operazione fallita'), true);
            }
        })
        .catch(err => {
            console.error('Fetch error:', err);
            showFeedback('Errore di comunicazione con il server', true);
        });
    }

    document.querySelectorAll('.qty-btn.minus').forEach(btn => {
        btn.addEventListener('click', () => {
            let productId = btn.getAttribute('data-id');
            let currentQty = parseInt(document.getElementById('qty-' + productId).innerText);
            if (currentQty > 1) {
                updateCart('update', productId, currentQty - 1);
            } else {
                updateCart('remove', productId);
            }
        });
    });

    document.querySelectorAll('.qty-btn.plus').forEach(btn => {
        btn.addEventListener('click', () => {
            let productId = btn.getAttribute('data-id');
            let currentQty = parseInt(document.getElementById('qty-' + productId).innerText);
            updateCart('update', productId, currentQty + 1);
        });
    });

    document.querySelectorAll('.btn-remove').forEach(btn => {
        btn.addEventListener('click', () => {
            let productId = btn.getAttribute('data-id');
            if (confirm('Rimuovere questo prodotto?')) {
                updateCart('remove', productId);
            }
        });
    });

    const clearBtn = document.getElementById('clear-cart');
    if (clearBtn) {
        clearBtn.addEventListener('click', () => {
            if (confirm('Svuotare completamente il carrello?')) {
                updateCart('clear');
            }
        });
    }
</script>

<%@ include file="footer.jsp" %>