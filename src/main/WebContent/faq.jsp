<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header.jsp" %>

<!--
    Pagina delle Domande Frequenti (FAQ).
    Presenta un elenco di domande e risposte con effetto "accordion" (apri/chiudi).
    Le domande sono statiche (non provengono da database).
-->

<div class="faq-container">
    <h2>❓ Domande frequenti</h2>
    <p class="faq-subtitle">Trova le risposte alle domande più comuni su Buy4Play</p>

    <div class="faq-list">
        <!-- Ogni elemento faq-item contiene una domanda (faq-question) e una risposta (faq-answer) -->
        <div class="faq-item">
            <div class="faq-question">🎮 Come funziona l'acquisto di una chiave videogioco?</div>
            <div class="faq-answer">
                Dopo aver selezionato il gioco e aver completato l'acquisto, riceverai immediatamente la chiave digitale nella tua email associata all'account. Potrai attivarla sulla piattaforma corrispondente (Steam, Xbox, PlayStation Store, ecc.).
            </div>
        </div>

        <div class="faq-item">
            <div class="faq-question">💳 Quali metodi di pagamento sono accettati?</div>
            <div class="faq-answer">
                Accettiamo carte di credito/debito (Visa, Mastercard, American Express), PayPal, e anche bonifico bancario. Tutti i pagamenti sono elaborati in modo sicuro.
            </div>
        </div>

        <div class="faq-item">
            <div class="faq-question">⏱️ Quanto tempo ci vuole per ricevere la chiave?</div>
            <div class="faq-answer">
                La consegna è immediata subito dopo la conferma del pagamento. In rari casi di verifiche di sicurezza, possono occorrere fino a 5-10 minuti.
            </div>
        </div>

        <div class="faq-item">
            <div class="faq-question">🔄 Posso effettuare il reso di una chiave?</div>
            <div class="faq-answer">
                Le chiavi digitali, una volta visualizzate, non possono essere restituite. Tuttavia, se non hai ancora visualizzato la chiave, puoi richiedere il rimborso entro 14 giorni. Contatta l'assistenza.
            </div>
        </div>

        <div class="faq-item">
            <div class="faq-question">🌍 Le chiavi sono valide in tutto il mondo?</div>
            <div class="faq-answer">
                La maggior parte delle chiavi è globale (worldwide), ma alcune potrebbero essere limitate a regioni specifiche (es. Europa, Nord America). Leggi sempre la descrizione del prodotto.
            </div>
        </div>

        <div class="faq-item">
            <div class="faq-question">🔒 Come vengono protetti i miei dati?</div>
            <div class="faq-answer">
                Utilizziamo connessioni cifrate SSL/TLS e non memorizziamo informazioni di pagamento sensibili. I tuoi dati personali sono al sicuro.
            </div>
        </div>

        <div class="faq-item">
            <div class="faq-question">👥 Come posso contattare l'assistenza clienti?</div>
            <div class="faq-answer">
                Puoi scriverci all'indirizzo <strong>assistenza@buy4play.it</strong> oppure utilizzare il modulo di contatto disponibile nella sezione "Contatti".
            </div>
        </div>
    </div>
</div>

<style>
    /* Stili per la pagina FAQ: card scura, effetto accordion, responsive */
    .faq-container {
        max-width: 900px;
        margin: 2rem auto;
        background: rgba(18, 22, 28, 0.7);
        backdrop-filter: blur(8px);
        border-radius: 32px;
        padding: 2rem;
        border: 1px solid rgba(46, 213, 115, 0.3);
    }
    .faq-container h2 {
        text-align: center;
        margin-bottom: 0.5rem;
    }
    .faq-subtitle {
        text-align: center;
        color: #b9c7d9;
        margin-bottom: 2rem;
    }
    .faq-list {
        display: flex;
        flex-direction: column;
        gap: 1rem;
    }
    .faq-item {
        background: #0a0c10;
        border-radius: 20px;
        overflow: hidden;
        border-left: 4px solid #2ed573;
        transition: all 0.2s;
    }
    .faq-question {
        padding: 1rem 1.5rem;
        font-weight: 600;
        cursor: pointer;
        background: #1e232c;
        color: #eef2ff;
        transition: background 0.2s;
    }
    .faq-question:hover {
        background: #2a2f3a;
    }
    .faq-answer {
        padding: 0 1.5rem;
        max-height: 0;
        overflow: hidden;
        transition: all 0.3s ease-out;
        color: #b9c7d9;
        border-top: 0px solid transparent;
    }
    /* Classe open aggiunta via JS: mostra la risposta */
    .faq-item.open .faq-answer {
        padding: 1rem 1.5rem;
        max-height: 300px;  /* Altezza sufficiente per contenere la risposta */
        border-top: 1px solid rgba(46, 213, 115, 0.2);
    }
    @media (max-width: 600px) {
        .faq-container {
            margin: 1rem;
            padding: 1rem;
        }
        .faq-question {
            padding: 0.8rem 1rem;
        }
        .faq-answer {
            font-size: 0.9rem;
        }
    }
</style>

<script>
    // JavaScript per effetto accordion: click sulla domanda alterna la classe 'open'
    document.querySelectorAll('.faq-question').forEach(question => {
        question.addEventListener('click', () => {
            const parent = question.parentElement;
            parent.classList.toggle('open');
        });
    });
</script>

<%@ include file="footer.jsp" %>