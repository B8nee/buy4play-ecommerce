<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header.jsp" %>

<!-- 
    Pagina "Contattaci" (statica, non invia realmente email).
    Mostra i contatti (indirizzo, telefono, email) e un modulo di contatto.
    Il modulo esegue validazione lato client (JavaScript) e simula l'invio
    con un toast di successo. Non viene effettuata alcuna chiamata al server.
-->

<div class="contact-container">
    <h2>📧 Contattaci</h2>
    <p class="contact-subtitle">Hai domande? Scrivici! Risponderemo il prima possibile.</p>

    <div class="contact-wrapper">
        <!-- Sezione informazioni di contatto -->
        <div class="contact-info">
            <div class="info-card">
                <div class="info-icon">📍</div>
                <h3>Indirizzo</h3>
                <p>Via Giovanni Paolo II, 132<br>84084 Fisciano (SA)</p>
            </div>
            <div class="info-card">
                <div class="info-icon">📞</div>
                <h3>Telefono</h3>
                <p>+39 089 123 4567</p>
            </div>
            <div class="info-card">
                <div class="info-icon">✉️</div>
                <h3>Email</h3>
                <p>info@buy4play.it<br>assistenza@buy4play.it</p>
            </div>
        </div>

        <!-- Form di contatto (simulazione) -->
        <form class="contact-form" id="contactForm">
            <div class="form-group">
                <label for="nome">Nome e cognome *</label>
                <input type="text" id="nome" name="nome" required>
            </div>
            <div class="form-group">
                <label for="email">Email *</label>
                <input type="email" id="email" name="email" required>
            </div>
            <div class="form-group">
                <label for="oggetto">Oggetto *</label>
                <input type="text" id="oggetto" name="oggetto" required>
            </div>
            <div class="form-group">
                <label for="messaggio">Messaggio *</label>
                <textarea id="messaggio" name="messaggio" rows="5" required></textarea>
            </div>
            <div class="form-check">
                <input type="checkbox" id="privacy" name="privacy" required>
                <label for="privacy">Accetto l'informativa sulla privacy *</label>
            </div>
            <button type="submit" class="btn btn-submit">Invia messaggio</button>
        </form>
    </div>
</div>

<!-- Toast per feedback di invio simulato -->
<div id="toastMessage" class="toast-message">📨 Messaggio inviato con successo! Ti risponderemo presto.</div>

<style>
    /* Stili per la pagina contatti: card, form, toast */
    .contact-container {
        max-width: 1200px;
        margin: 2rem auto;
        background: rgba(18, 22, 28, 0.85);
        backdrop-filter: blur(10px);
        border-radius: 32px;
        padding: 2rem;
        border: 1px solid rgba(46, 213, 115, 0.3);
    }
    .contact-container h2 {
        text-align: center;
        margin-bottom: 0.5rem;
    }
    .contact-subtitle {
        text-align: center;
        color: #b9c7d9;
        margin-bottom: 2rem;
    }
    .contact-wrapper {
        display: flex;
        flex-wrap: wrap;
        gap: 2rem;
    }
    .contact-info {
        flex: 1;
        min-width: 200px;
        display: flex;
        flex-direction: column;
        gap: 1.5rem;
    }
    .info-card {
        background: rgba(0,0,0,0.3);
        border-radius: 24px;
        padding: 1.5rem;
        text-align: center;
        border: 1px solid rgba(46, 213, 115, 0.2);
    }
    .info-icon {
        font-size: 2.5rem;
        margin-bottom: 0.5rem;
    }
    .info-card h3 {
        margin-bottom: 0.5rem;
        color: #2ed573;
    }
    .info-card p {
        color: #b9c7d9;
    }
    .contact-form {
        flex: 2;
        min-width: 300px;
        background: rgba(0,0,0,0.3);
        border-radius: 32px;
        padding: 1.5rem;
    }
    .contact-form .form-group {
        margin-bottom: 1rem;
    }
    .contact-form label {
        display: block;
        margin-bottom: 0.4rem;
        font-weight: 500;
        color: #b9c7d9;
    }
    .contact-form input, .contact-form textarea {
        width: 100%;
        padding: 0.8rem;
        background: #0a0c10;
        border: 1px solid #2c3e3a;
        border-radius: 20px;
        color: #fff;
        font-size: 0.95rem;
    }
    .contact-form input:focus, .contact-form textarea:focus {
        outline: none;
        border-color: #2ed573;
    }
    .form-check {
        display: flex;
        justify-content: center;
        align-items: center;
        gap: 0.5rem;
        margin: 1rem 0;
    }
    .form-check input {
        width: auto;
        margin: 0;
        vertical-align: middle;
    }
    .form-check label {
        margin: 0;
        line-height: normal;
        vertical-align: middle;
    }
    .btn-submit {
        display: block;
        width: fit-content;
        margin: 0.5rem auto 0;
        text-align: center;
    }
    .toast-message {
        position: fixed;
        bottom: 30px;
        right: 30px;
        background: #2ed573;
        color: #0a0c10;
        padding: 12px 24px;
        border-radius: 40px;
        font-weight: bold;
        font-size: 0.9rem;
        box-shadow: 0 5px 15px rgba(0,0,0,0.3);
        opacity: 0;
        transition: opacity 0.3s ease;
        z-index: 9999;
        pointer-events: none;
    }
    .toast-message.show {
        opacity: 1;
    }
    @media (max-width: 800px) {
        .contact-wrapper {
            flex-direction: column;
        }
        .contact-info {
            flex-direction: row;
            flex-wrap: wrap;
            justify-content: center;
        }
        .info-card {
            flex: 1;
        }
    }
</style>

<script>
    // Validazione e simulazione invio messaggio (toast, senza chiamate al server)
    document.addEventListener('DOMContentLoaded', function() {
        var form = document.getElementById('contactForm');
        var toast = document.getElementById('toastMessage');

        if (form) {
            form.addEventListener('submit', function(e) {
                e.preventDefault();

                // Legge i valori dei campi
                var nome = document.getElementById('nome').value.trim();
                var email = document.getElementById('email').value.trim();
                var oggetto = document.getElementById('oggetto').value.trim();
                var messaggio = document.getElementById('messaggio').value.trim();
                var privacy = document.getElementById('privacy').checked;

                // Validazione campi obbligatori e privacy
                if (!nome || !email || !oggetto || !messaggio) {
                    alert('Per favore, compila tutti i campi obbligatori.');
                    return;
                }
                if (!privacy) {
                    alert('Devi accettare l\'informativa sulla privacy.');
                    return;
                }
                // Validazione formato email con regex semplice
                var emailRegex = /^[^\s@]+@([^\s@]+\.)+[^\s@]+$/;
                if (!emailRegex.test(email)) {
                    alert('Inserisci un indirizzo email valido.');
                    return;
                }

                // Mostra il toast di successo e lo nasconde dopo 3.5 secondi
                toast.classList.add('show');
                setTimeout(function() {
                    toast.classList.remove('show');
                }, 3500);

                // Resetta il form
                form.reset();
            });
        } else {
            console.error('Form non trovato!');
        }
    });
</script>

<%@ include file="footer.jsp" %>