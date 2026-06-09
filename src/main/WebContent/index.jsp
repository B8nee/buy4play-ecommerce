<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, model.Prodotto, model.ProdottoDAO" %>
<%@ include file="header.jsp" %>

<!-- 
    Pagina principale (home) del sito.
    Mostra una sezione hero, una griglia di prodotti "popolari" (primi 4 prodotti),
    una sezione con i vantaggi e una con le categorie.
-->

<!-- Hero section: immagine di sfondo, titolo, descrizione e pulsante -->
<div class="hero">
    <div class="hero-content">
        <h1>Benvenuto su <span class="gradient-text">Buy 4 Play</span></h1>
        <p>Le migliori chiavi per i tuoi videogiochi, ai prezzi più bassi del web.</p>
        <a href="catalogo" class="btn">Esplora il catalogo</a>
    </div>
</div>

<%
    // Recupera i primi 4 prodotti dal catalogo per la sezione "Giochi più popolari"
    List<Prodotto> popolari = null;
    try {
        ProdottoDAO dao = new ProdottoDAO();
        // Parametri: offset=0, limit=4, nessun filtro piattaforma, nessun ordinamento
        popolari = dao.doRetrieveAll(0, 4, null, null);
    } catch (Exception e) {
        e.printStackTrace();
    }
    if (popolari != null && !popolari.isEmpty()) {
%>
<div class="featured-section">
    <div class="section-title">
        <h2>🔥 Giochi più popolari</h2>
    </div>
    <div class="featured-grid">
        <% for (Prodotto p : popolari) { %>
        <div class="featured-card">
            <img src="<%= p.getImmagineUrl() %>" alt="<%= p.getNome() %>">
            <h3><%= p.getNome() %></h3>
            <p><%= p.getPiattaforma() %></p>
            <div class="price">&euro; <%= String.format("%.2f", p.getPrezzo()) %></div>
            <a href="dettaglio?id=<%= p.getId() %>" class="btn small">Acquista</a>
        </div>
        <% } %>
    </div>
</div>
<% } %>

<!-- Sezione dei vantaggi (perché scegliere Buy4Play) -->
<div class="benefits-section">
    <div class="section-title">
        <h2>✨ Perché scegliere Buy4Play</h2>
    </div>
    <div class="benefits-grid">
        <div class="benefit-card">
            <div class="benefit-icon">💰</div>
            <h3>Prezzi imbattibili</h3>
            <p>Offerte esclusive e sconti fino al 70% sui giochi più attesi.</p>
        </div>
        <div class="benefit-card">
            <div class="benefit-icon">⚡</div>
            <h3>Consegna immediata</h3>
            <p>Ricevi la chiave digitale subito dopo il pagamento.</p>
        </div>
        <div class="benefit-card">
            <div class="benefit-icon">🔒</div>
            <h3>Pagamento sicuro</h3>
            <p>Transazioni protette con i migliori standard di sicurezza.</p>
        </div>
    </div>
</div>

<!-- Sezione delle categorie (filtri per piattaforma) -->
<div class="categories-section">
    <div class="section-title">
        <h2>🎮 Categorie</h2>
    </div>
    <div class="categories-grid">
        <a href="catalogo?platform=PC" class="category-card">
            <span class="category-icon">🖥️</span>
            <span>PC</span>
        </a>
        <a href="catalogo?platform=PS5" class="category-card">
            <span class="category-icon">🎮</span>
            <span>PlayStation 5</span>
        </a>
        <a href="catalogo?platform=Xbox" class="category-card">
            <span class="category-icon">❌</span>
            <span>Xbox</span>
        </a>
        <a href="catalogo" class="category-card">
            <span class="category-icon">🔥</span>
            <span>Tutti i giochi</span>
        </a>
    </div>
</div>

<style>
    /* Stili specifici per la pagina home (hero, card, vantaggi, categorie) */
    .hero {
        background: linear-gradient(rgba(0,0,0,0.7), rgba(0,0,0,0.7)), url('https://r4.wallpaperflare.com/wallpaper/162/894/557/colorful-neon-computer-keyboards-wallpaper-12e102d0cd167edb8ac8829560685912.jpg');
        background-size: cover;
        background-position: center;
        border-radius: 32px;
        margin: 2rem auto;
        max-width: 1200px;
        padding: 4rem 2rem;
        text-align: center;
    }
    .hero h1 {
        font-size: 3rem;
        margin-bottom: 1rem;
    }
    .gradient-text {
        background: linear-gradient(135deg, #ffffff, #2ed573);
        -webkit-background-clip: text;
        background-clip: text;
        color: transparent;
    }
    .hero p {
        font-size: 1.2rem;
        margin-bottom: 2rem;
        color: #e0e0e0;
        max-width: 600px;
        margin-left: auto;
        margin-right: auto;
    }
    /* Sezioni generali */
    .featured-section, .benefits-section, .categories-section {
        max-width: 1200px;
        margin: 3rem auto;
        padding: 0 1rem;
    }
    .section-title {
        text-align: center;
        width: 100%;
        margin-bottom: 2rem;
    }
    .section-title h2 {
        display: inline-block;
        font-size: 2rem;
        margin: 0 auto;
    }
    /* Griglia prodotti popolari (flex centrato) */
    .featured-grid {
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
        gap: 2rem;
    }
    .featured-card {
        background: rgba(18, 22, 28, 0.8);
        backdrop-filter: blur(4px);
        border-radius: 24px;
        padding: 1rem;
        text-align: center;
        transition: transform 0.3s, border-color 0.2s;
        border: 1px solid rgba(46, 213, 115, 0.2);
        width: 240px;
        flex-shrink: 0;
    }
    .featured-card:hover {
        transform: translateY(-6px);
        border-color: #2ed573;
    }
    .featured-card img {
        width: 100%;
        height: 180px;
        object-fit: cover;
        border-radius: 16px;
    }
    .featured-card h3 {
        margin: 0.8rem 0 0.3rem;
        font-size: 1.2rem;
    }
    .featured-card .price {
        font-size: 1.2rem;
        font-weight: bold;
        color: #2ed573;
        margin: 0.5rem 0;
    }
    .btn.small {
        padding: 0.4rem 1rem;
        font-size: 0.8rem;
    }
    /* Vantaggi */
    .benefits-grid {
        display: flex;
        justify-content: center;
        gap: 2rem;
        flex-wrap: wrap;
    }
    .benefit-card {
        background: rgba(18, 22, 28, 0.8);
        border-radius: 32px;
        padding: 2rem;
        text-align: center;
        width: 280px;
        transition: transform 0.2s;
        border: 1px solid rgba(46, 213, 115, 0.2);
    }
    .benefit-card:hover {
        transform: translateY(-5px);
        border-color: #2ed573;
    }
    .benefit-icon {
        font-size: 3rem;
        margin-bottom: 1rem;
    }
    .benefit-card p {
        color: #b9c7d9;
        font-size: 0.9rem;
    }
    /* Categorie */
    .categories-grid {
        display: flex;
        justify-content: center;
        gap: 2rem;
        flex-wrap: wrap;
    }
    .category-card {
        background: rgba(18, 22, 28, 0.7);
        border-radius: 60px;
        padding: 0.8rem 1.8rem;
        text-decoration: none;
        display: flex;
        align-items: center;
        gap: 0.5rem;
        color: white;
        font-weight: 500;
        transition: all 0.2s;
        border: 1px solid rgba(46, 213, 115, 0.3);
    }
    .category-card:hover {
        background: #2ed573;
        color: #0a0c10;
        border-color: #2ed573;
    }
    /* Responsive */
    @media (max-width: 768px) {
        .hero h1 {
            font-size: 2rem;
        }
        .hero p {
            font-size: 1rem;
        }
        .benefit-card {
            width: 100%;
            max-width: 280px;
        }
        .category-card {
            padding: 0.5rem 1.2rem;
        }
        .featured-card {
            width: 200px;
        }
    }
</style>

<%@ include file="footer.jsp" %>