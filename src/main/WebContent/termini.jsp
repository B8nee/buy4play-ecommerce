<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header.jsp" %>

<!--
    Pagina informativa dei Termini e condizioni d'uso del sito.
    Contiene sezioni standard su accettazione, account, prodotti digitali,
    prezzi, consegna, resi, proprietà intellettuale, limitazioni di responsabilità,
    modifiche e contatti.
-->

<div class="terms-container">
    <h2>📜 Termini e condizioni</h2>
    <p class="terms-subtitle">Leggi attentamente i termini che regolano l'utilizzo di Buy4Play</p>

    <div class="terms-content">
        <!-- Sezione 1: Accettazione -->
        <div class="terms-section">
            <h3>1. Accettazione dei termini</h3>
            <p>Accedendo o utilizzando il sito web Buy4Play ("il Sito"), accetti di essere vincolato da questi Termini e condizioni. Se non accetti una qualsiasi parte dei termini, non puoi utilizzare il Sito.</p>
        </div>

        <!-- Sezione 2: Account utente -->
        <div class="terms-section">
            <h3>2. Account utente</h3>
            <p>Per effettuare acquisti è necessario registrarsi fornendo informazioni veritiere e aggiornate. Sei responsabile della riservatezza delle tue credenziali e di tutte le attività che avvengono sotto il tuo account. Ci riserviamo il diritto di sospendere o cancellare account in caso di violazione dei presenti termini.</p>
        </div>

        <!-- Sezione 3: Prodotti e chiavi digitali -->
        <div class="terms-section">
            <h3>3. Prodotti e chiavi digitali</h3>
            <p>I prodotti venduti sono chiavi digitali per videogiochi, valide per le piattaforme indicate (Steam, PlayStation Network, Xbox Live, ecc.). Le chiavi sono fornite dai nostri fornitori; non garantiamo la compatibilità con tutte le regioni. Controlla sempre la descrizione del prodotto prima dell'acquisto.</p>
        </div>

        <!-- Sezione 4: Prezzi e pagamenti -->
        <div class="terms-section">
            <h3>4. Prezzi e pagamenti</h3>
            <p>I prezzi sono espressi in Euro (€) e includono l'IVA (dove applicabile). Ci riserviamo il diritto di modificare i prezzi in qualsiasi momento, ma le modifiche non influenzeranno gli ordini già confermati. I pagamenti sono elaborati in modo sicuro tramite i nostri partner (PayPal, carte di credito, ecc.).</p>
        </div>

        <!-- Sezione 5: Consegna e attivazione -->
        <div class="terms-section">
            <h3>5. Consegna e attivazione</h3>
            <p>Le chiavi digitali vengono consegnate immediatamente dopo la conferma del pagamento all'indirizzo email associato all'account. In caso di ritardi tecnici, potrebbero occorrere fino a 24 ore. Una volta ricevuta la chiave, l'attivazione è a tuo carico seguendo le istruzioni della piattaforma.</p>
        </div>

        <!-- Sezione 6: Diritto di recesso e resi -->
        <div class="terms-section">
            <h3>6. Diritto di recesso e resi</h3>
            <p>I prodotti digitali (chiavi) sono esclusi dal diritto di recesso previsto dal Codice del Consumo (art. 59 D.Lgs. 206/2005) perché vengono forniti immediatamente e perdono la natura di bene durevole dopo la rivelazione della chiave. Non sono possibili resi o rimborsi, salvo che la chiave sia difettosa o non funzionante (in tal caso contatta l'assistenza).</p>
        </div>

        <!-- Sezione 7: Proprietà intellettuale -->
        <div class="terms-section">
            <h3>7. Proprietà intellettuale</h3>
            <p>Tutti i contenuti del Sito (testi, immagini, loghi, marchi) sono di proprietà di Buy4Play o dei rispettivi licenziatari. È vietata la riproduzione non autorizzata.</p>
        </div>

        <!-- Sezione 8: Limitazione di responsabilità -->
        <div class="terms-section">
            <h3>8. Limitazione di responsabilità</h3>
            <p>Buy4Play non sarà responsabile per danni indiretti, incidentali o consequenziali derivanti dall'uso o dall'incapacità di utilizzare il Sito o i prodotti, nella misura massima consentita dalla legge.</p>
        </div>

        <!-- Sezione 9: Modifiche ai termini -->
        <div class="terms-section">
            <h3>9. Modifiche ai termini</h3>
            <p>Ci riserviamo il diritto di aggiornare questi termini periodicamente. La versione più recente sarà sempre pubblicata su questa pagina. L'uso continuato del Sito dopo le modifiche costituisce accettazione dei nuovi termini.</p>
        </div>

        <!-- Sezione 10: Contatti -->
        <div class="terms-section">
            <h3>10. Contatti</h3>
            <p>Per qualsiasi domanda relativa a questi Termini e condizioni, contattaci all'indirizzo: <strong>legale@buy4play.it</strong>.</p>
        </div>
    </div>

    <!-- Footer della pagina: data aggiornamento e link di ritorno -->
    <div class="terms-footer">
        <p>Ultimo aggiornamento: 7 giugno 2026</p>
        <a href="<%= request.getContextPath() %>/catalogo" class="btn btn-small">Torna al catalogo</a>
    </div>
</div>

<style>
    /* Stili per la pagina termini e condizioni */
    .terms-container {
        max-width: 1000px;
        margin: 2rem auto;
        background: rgba(18, 22, 28, 0.85);
        backdrop-filter: blur(10px);
        border-radius: 32px;
        padding: 2rem;
        border: 1px solid rgba(46, 213, 115, 0.3);
    }
    .terms-container h2 {
        text-align: center;
        margin-bottom: 0.5rem;
    }
    .terms-subtitle {
        text-align: center;
        color: #b9c7d9;
        margin-bottom: 2rem;
    }
    .terms-content {
        display: flex;
        flex-direction: column;
        gap: 1.8rem;
    }
    .terms-section h3 {
        color: #2ed573;
        margin-bottom: 0.5rem;
        font-size: 1.2rem;
        border-left: 3px solid #2ed573;
        padding-left: 0.8rem;
    }
    .terms-section p {
        color: #eef2ff;
        line-height: 1.5;
        margin-top: 0.5rem;
    }
    .terms-footer {
        text-align: center;
        margin-top: 2rem;
        padding-top: 1.5rem;
        border-top: 1px solid rgba(46, 213, 115, 0.2);
    }
    .terms-footer p {
        font-size: 0.8rem;
        color: #8e9aaf;
        margin-bottom: 1rem;
    }
    .btn-small {
        padding: 0.5rem 1.2rem;
        font-size: 0.9rem;
    }
    @media (max-width: 768px) {
        .terms-container {
            margin: 1rem;
            padding: 1rem;
        }
        .terms-section h3 {
            font-size: 1rem;
        }
    }
</style>

<%@ include file="footer.jsp" %>