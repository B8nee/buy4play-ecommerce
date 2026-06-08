<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header.jsp" %>

<div class="privacy-container">
    <h2>🔒 Informativa sulla privacy</h2>
    <p class="privacy-subtitle">Come trattiamo i tuoi dati personali su Buy4Play</p>

    <div class="privacy-content">
        <div class="privacy-section">
            <h3>1. Titolare del trattamento</h3>
            <p>Buy4Play – Via Giovanni Paolo II, 132 – 84084 Fisciano (SA) – P.IVA 12345678901 – email: privacy@buy4play.it</p>
        </div>
        <div class="privacy-section">
            <h3>2. Dati raccolti</h3>
            <p>Raccogliamo i seguenti dati personali:</p>
            <ul>
                <li>Nome, cognome, email, indirizzo di spedizione (per l'evasione degli ordini).</li>
                <li>Dati di pagamento (gestiti da fornitori terzi, non memorizziamo i dettagli delle carte).</li>
                <li>Dati di navigazione (indirizzo IP, tipo di browser, pagine visitate) per finalità statistiche e di sicurezza.</li>
            </ul>
        </div>
        <div class="privacy-section">
            <h3>3. Finalità e base giuridica</h3>
            <p>I tuoi dati vengono trattati per:</p>
            <ul>
                <li>Gestire l'account e gli ordini (esecuzione del contratto).</li>
                <li>Inviare comunicazioni relative agli acquisti (adempimenti contrattuali).</li>
                <li>Adempiere a obblighi di legge (es. fiscali).</li>
                <li>Migliorare i nostri servizi (interesse legittimo).</li>
            </ul>
        </div>
        <div class="privacy-section">
            <h3>4. Conservazione dei dati</h3>
            <p>I dati vengono conservati per il tempo necessario a fornire i servizi richiesti e per adempiere agli obblighi di legge (es. 10 anni per le fatture).</p>
        </div>
        <div class="privacy-section">
            <h3>5. Diritti dell'interessato</h3>
            <p>Hai diritto di accedere, rettificare, cancellare i tuoi dati, limitare il trattamento, opporre al trattamento e ottenere la portabilità. Puoi esercitare i tuoi diritti scrivendo a privacy@buy4play.it.</p>
        </div>
        <div class="privacy-section">
            <h3>6. Cookie</h3>
            <p>Utilizziamo cookie tecnici (necessari al funzionamento del sito) e cookie analitici anonimizzati. Non utilizziamo cookie di profilazione. Puoi gestire le preferenze nel tuo browser.</p>
        </div>
        <div class="privacy-section">
            <h3>7. Modifiche alla privacy policy</h3>
            <p>Ci riserviamo di aggiornare questa informativa. La versione più recente è sempre disponibile su questa pagina.</p>
        </div>
    </div>

    <div class="privacy-footer">
        <p>Ultimo aggiornamento: 7 giugno 2026</p>
        <a href="<%= request.getContextPath() %>/catalogo" class="btn btn-small">Torna al catalogo</a>
    </div>
</div>

<style>
    .privacy-container {
        max-width: 1000px;
        margin: 2rem auto;
        background: rgba(18, 22, 28, 0.85);
        backdrop-filter: blur(10px);
        border-radius: 32px;
        padding: 2rem;
        border: 1px solid rgba(46, 213, 115, 0.3);
    }
    .privacy-container h2 {
        text-align: center;
        margin-bottom: 0.5rem;
    }
    .privacy-subtitle {
        text-align: center;
        color: #b9c7d9;
        margin-bottom: 2rem;
    }
    .privacy-content {
        display: flex;
        flex-direction: column;
        gap: 1.8rem;
    }
    .privacy-section h3 {
        color: #2ed573;
        margin-bottom: 0.5rem;
        font-size: 1.2rem;
        border-left: 3px solid #2ed573;
        padding-left: 0.8rem;
    }
    .privacy-section p, .privacy-section li {
        color: #eef2ff;
        line-height: 1.5;
        margin-top: 0.5rem;
    }
    .privacy-section ul {
        padding-left: 1.5rem;
        margin-top: 0.5rem;
    }
    .privacy-footer {
        text-align: center;
        margin-top: 2rem;
        padding-top: 1.5rem;
        border-top: 1px solid rgba(46, 213, 115, 0.2);
    }
    .privacy-footer p {
        font-size: 0.8rem;
        color: #8e9aaf;
        margin-bottom: 1rem;
    }
    .btn-small {
        padding: 0.5rem 1.2rem;
        font-size: 0.9rem;
    }
    @media (max-width: 768px) {
        .privacy-container {
            margin: 1rem;
            padding: 1rem;
        }
        .privacy-section h3 {
            font-size: 1rem;
        }
    }
</style>

<%@ include file="footer.jsp" %>