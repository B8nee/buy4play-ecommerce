<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    </div> <!-- Chiusura del div.container aperto in header.jsp -->

    <!-- 
        ============================================================================
        Piè di pagina (footer) del sito, comune a tutte le pagine.
        Contiene:
        - Descrizione del sito
        - Link rapidi (dinamici in base alla sessione utente)
        - Link di supporto (FAQ, Termini, Privacy, Contatti)
        - Icone social (con link esterni)
        - Modulo newsletter (simulazione lato client con toast di feedback)
        ============================================================================
    -->

    <footer class="site-footer">
        <div class="footer-container">
            <!-- Colonna 1: Logo e descrizione -->
            <div class="footer-col">
                <h3>🎮 Buy 4 Play</h3>
                <p>Il tuo negozio di fiducia per chiavi videogiochi digitali. Offerte imperdibili su PC, PlayStation e Xbox.</p>
            </div>

            <!-- Colonna 2: Link rapidi (catalogo, carrello, profilo/login/registrazione) -->
            <div class="footer-col">
                <h4>Link rapidi</h4>
                <ul>
                    <li><a href="<%= request.getContextPath() %>/catalogo">Catalogo</a></li>
                    <li><a href="<%= request.getContextPath() %>/carrello">Carrello</a></li>
                    <% if (session.getAttribute("utente") != null) { %>
                        <!-- Se l'utente è loggato, mostra il link al profilo -->
                        <li><a href="<%= request.getContextPath() %>/profilo">Il mio profilo</a></li>
                    <% } else { %>
                        <!-- Altrimenti mostra i link di accesso e registrazione -->
                        <li><a href="<%= request.getContextPath() %>/login.jsp">Accedi</a></li>
                        <li><a href="<%= request.getContextPath() %>/registrazione.jsp">Registrati</a></li>
                    <% } %>
                </ul>
            </div>

            <!-- Colonna 3: Supporto (pagine informative e di contatto) -->
            <div class="footer-col">
                <h4>Supporto</h4>
                <ul>
                    <li><a href="<%= request.getContextPath() %>/faq.jsp">FAQ</a></li>
                    <li><a href="<%= request.getContextPath() %>/termini.jsp">Termini e condizioni</a></li>
                    <li><a href="<%= request.getContextPath() %>/privacy.jsp">Privacy Policy</a></li>
                    <li><a href="<%= request.getContextPath() %>/contattaci.jsp">Contattaci</a></li>
                </ul>
            </div>

            <!-- Colonna 4: Social network e newsletter (simulata) -->
            <div class="footer-col">
                <h4>Seguici</h4>
                <div class="social-icons">
                    <!-- Link ai social (aprono in nuova scheda) -->
                    <a href="https://facebook.com" target="_blank" rel="noopener noreferrer">
                        <img src="https://cdn-icons-png.flaticon.com/512/124/124010.png" alt="Facebook">
                    </a>
                    <a href="https://instagram.com" target="_blank" rel="noopener noreferrer">
                        <img src="https://cdn-icons-png.flaticon.com/512/2111/2111463.png" alt="Instagram">
                    </a>
                    <a href="https://twitter.com" target="_blank" rel="noopener noreferrer">
                        <img src="https://cdn-icons-png.flaticon.com/512/733/733579.png" alt="Twitter">
                    </a>
                    <a href="https://discord.com" target="_blank" rel="noopener noreferrer">
                        <img src="https://cdn-icons-png.flaticon.com/512/2111/2111370.png" alt="Discord">
                    </a>
                </div>
                <!-- Modulo newsletter: simulazione client-side (nessuna persistenza) -->
                <p class="newsletter">Iscriviti alla newsletter:<br>
                    <input type="email" id="newsletterEmail" placeholder="La tua email">
                    <button id="newsletterBtn" class="btn-small">Iscriviti</button>
                </p>
            </div>
        </div>
        <!-- Copyright e crediti -->
        <div class="footer-bottom">
            <p>&copy; 2026 Buy 4 Play - Progetto TSW - Università di Salerno. Tutti i diritti riservati.</p>
        </div>
    </footer>

    <!-- Toast per il feedback della newsletter (simulazione) -->
    <div id="newsletterToast" class="newsletter-toast">✅ Grazie per esserti iscritto alla newsletter!</div>

    <style>
        /* Stili per il footer: tema scuro, layout flessibile, responsive */
        .site-footer {
            background: rgba(10, 14, 23, 0.98);
            border-top: 1px solid rgba(46, 213, 115, 0.3);
            margin-top: 3rem;
            padding: 2rem 0 1rem;
            font-size: 0.9rem;
            color: #b9c7d9;
        }
        .footer-container {
            max-width: 1200px;
            margin: 0 auto;
            display: flex;
            flex-wrap: wrap;
            justify-content: space-between;
            gap: 2rem;
            padding: 0 2rem;
        }
        .footer-col {
            flex: 1;
            min-width: 180px;
        }
        .footer-col h3, .footer-col h4 {
            color: #ffffff;
            margin-bottom: 1rem;
            font-weight: 600;
        }
        /* Gradiente per il logo del footer */
        .footer-col h3 {
            background: linear-gradient(135deg, #ffffff, #2ed573);
            -webkit-background-clip: text;
            background-clip: text;
            color: transparent;
            display: inline-block;
        }
        .footer-col p {
            line-height: 1.5;
            margin-top: 0.5rem;
        }
        .footer-col ul {
            list-style: none;
            padding: 0;
        }
        .footer-col ul li {
            margin-bottom: 0.5rem;
        }
        .footer-col ul li a {
            color: #b9c7d9;
            text-decoration: none;
            transition: color 0.2s;
        }
        .footer-col ul li a:hover {
            color: #2ed573;
        }
        /* Icone social (effetto hover di scala e luminosità) */
        .social-icons {
            display: flex;
            justify-content: center;
            gap: 1.5rem;
        }
        .social-icons a {
            display: inline-block;
            transition: transform 0.2s;
        }
        .social-icons a:hover {
            transform: scale(1.1);
        }
        .social-icons img {
            width: 28px;
            height: 28px;
            filter: brightness(0.8);
            transition: filter 0.2s;
        }
        .social-icons img:hover {
            filter: brightness(1);
        }
        /* Input e bottone per newsletter */
        .newsletter input {
            background: #0a0c10;
            border: 1px solid #2c3e3a;
            border-radius: 40px;
            padding: 0.4rem 1rem;
            width: 100%;
            max-width: 200px;
            color: white;
            margin-top: 0.5rem;
            margin-bottom: 0.5rem;
        }
        .btn-small {
            background: #2ed573;
            border: none;
            border-radius: 40px;
            padding: 0.3rem 1rem;
            color: #0a0c10;
            font-weight: bold;
            cursor: pointer;
            transition: background 0.2s;
        }
        .btn-small:hover {
            background: #3ee684;
        }
        /* Footer bottom (copyright) */
        .footer-bottom {
            text-align: center;
            padding-top: 2rem;
            margin-top: 2rem;
            border-top: 1px solid rgba(46, 213, 115, 0.2);
            font-size: 0.8rem;
            color: #8e9aaf;
        }
        /* Toast posizionato al centro in basso */
        .newsletter-toast {
            position: fixed;
            bottom: 30px;
            left: 50%;
            transform: translateX(-50%);
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
            white-space: nowrap;
        }
        .newsletter-toast.show {
            opacity: 1;
        }
        /* Responsività per schermi piccoli */
        @media (max-width: 768px) {
            .footer-container {
                flex-direction: column;
                text-align: center;
                align-items: center;
            }
            .social-icons {
                justify-content: center;
            }
            .newsletter-toast {
                white-space: normal;
                text-align: center;
                font-size: 0.8rem;
                padding: 10px 20px;
            }
        }
    </style>

    <script>
        // ------------------------------------------------------------
        // Simulazione iscrizione newsletter (lato client)
        // ------------------------------------------------------------
        document.getElementById('newsletterBtn').addEventListener('click', function() {
            var emailInput = document.getElementById('newsletterEmail');
            var email = emailInput.value.trim();
            var toast = document.getElementById('newsletterToast');
            
            // Validazione dell'email tramite regex
            var emailRegex = /^[^\s@]+@([^\s@]+\.)+[^\s@]+$/;
            if (email === '') {
                alert('Inserisci un indirizzo email.');
                emailInput.focus();
                return;
            }
            if (!emailRegex.test(email)) {
                alert('Inserisci un indirizzo email valido (esempio: nome@dominio.com).');
                emailInput.focus();
                return;
            }
            
            // Mostra il toast di conferma
            toast.classList.add('show');
            setTimeout(function() {
                toast.classList.remove('show');
            }, 3000);
            
            // Resetta il campo email dopo la simulazione
            emailInput.value = '';
        });
    </script>

    </body>
</html>