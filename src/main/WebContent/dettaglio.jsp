<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header.jsp" %>
<%@ page import="model.Prodotto" %>

<%
    // Recupera il prodotto passato dalla servlet DettaglioControl
    Prodotto p = (Prodotto) request.getAttribute("prodotto");
    if (p == null) {
        // Se non esiste, reindirizza al catalogo
        response.sendRedirect("catalogo");
        return;
    }
%>

<!--
    Pagina di dettaglio di un singolo prodotto.
    Mostra immagine, nome, piattaforma, prezzo, descrizione generica,
    badge "Popolare" (se presente) e pulsante per aggiungere al carrello.
-->

<div class="dettaglio-container">
    <div class="dettaglio-grid">
        <!-- Colonna sinistra: immagine e badge -->
        <div class="dettaglio-img">
            <img src="<%= p.getImmagineUrl() %>" alt="<%= p.getNome() %>">
            <% if (p.isPopolare()) { %>
                <div class="badge-popolare">🔥 Popolare</div>
            <% } %>
        </div>
        <!-- Colonna destra: informazioni prodotto -->
        <div class="dettaglio-info">
            <h1 class="dettaglio-titolo"><%= p.getNome() %></h1>
            <div class="dettaglio-piattaforma"><%= p.getPiattaforma() %></div>
            <div class="dettaglio-prezzo">&euro; <%= String.format("%.2f", p.getPrezzo()) %></div>
            <div class="dettaglio-descrizione">
                <p>Esperienza di gioco garantita con attivazione immediata.  
                   Acquista la chiave originale per <%= p.getPiattaforma() %>.</p>
            </div>
            <!-- Pulsante AJAX per aggiungere al carrello -->
            <button id="addToCartBtn" class="btn add-to-cart" data-id="<%= p.getId() %>">🛒 Aggiungi al carrello</button>
            <div class="dettaglio-link">
                <a href="catalogo">← Torna al catalogo</a>
            </div>
        </div>
    </div>
</div>

<style>
    /* Stili per la pagina di dettaglio: layout a due colonne responsive */
    .dettaglio-container {
        max-width: 1200px;
        margin: 2rem auto;
        background: rgba(18, 22, 28, 0.7);
        border-radius: 32px;
        padding: 2rem;
    }
    .dettaglio-grid {
        display: flex;
        gap: 2rem;
        flex-wrap: wrap;
    }
    .dettaglio-img {
        flex: 1;
        min-width: 250px;
        position: relative;
    }
    .dettaglio-img img {
        width: 100%;
        border-radius: 28px;
        box-shadow: 0 10px 30px rgba(0,0,0,0.3);
    }
    .badge-popolare {
        position: absolute;
        top: 10px;
        left: 10px;
        background: #e94560;
        color: white;
        padding: 0.3rem 0.8rem;
        border-radius: 40px;
        font-weight: bold;
        font-size: 0.8rem;
    }
    .dettaglio-info {
        flex: 1;
        display: flex;
        flex-direction: column;
        gap: 1rem;
    }
    .dettaglio-titolo {
        font-size: 2rem;
        margin: 0;
        color: white;
    }
    .dettaglio-piattaforma {
        background: #1e232c;
        display: inline-block;
        padding: 0.3rem 1rem;
        border-radius: 40px;
        color: #2ed573;
        font-weight: bold;
        width: fit-content;
    }
    .dettaglio-prezzo {
        font-size: 2rem;
        font-weight: bold;
        color: #2ed573;
    }
    .dettaglio-descrizione {
        color: #b9c7d9;
        line-height: 1.6;
    }
    .dettaglio-link {
        margin-top: 1rem;
    }
    @media (max-width: 768px) {
        .dettaglio-container {
            margin: 1rem;
            padding: 1rem;
        }
        .dettaglio-titolo {
            font-size: 1.5rem;
        }
        .dettaglio-prezzo {
            font-size: 1.5rem;
        }
    }
</style>

<script>
    // Aggiunta al carrello tramite AJAX
    document.getElementById('addToCartBtn').addEventListener('click', function() {
        const productId = this.getAttribute('data-id');
        const originalText = this.innerHTML;
        this.innerHTML = '⏳ Caricamento...';
        fetch('<%= request.getContextPath() %>/carrello?action=add&id=' + productId, {
            method: 'GET',
            headers: { 'X-Requested-With': 'XMLHttpRequest' }
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                this.innerHTML = '✓ Aggiunto!';
                setTimeout(() => { this.innerHTML = originalText; }, 1500);
                // Aggiorna il badge del carrello nell'header
                if (typeof updateCartCount === 'function') updateCartCount();
            } else {
                this.innerHTML = '❌ Errore';
                setTimeout(() => { this.innerHTML = originalText; }, 2000);
            }
        })
        .catch(() => {
            this.innerHTML = '❌ Errore';
            setTimeout(() => { this.innerHTML = originalText; }, 2000);
        });
    });
</script>

<%@ include file="footer.jsp" %>