<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html lang="it">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Buy 4 Play - Catalogo chiavi videogiochi</title>
        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }

            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background-color: #f4f4f4;
                color: #333;
                line-height: 1.6;
            }

            header {
                background-color: #1a1a2e;
                color: white;
                padding: 1rem 2rem;
                display: flex;
                justify-content: space-between;
                align-items: center;
                flex-wrap: wrap;
            }

            .logo h1 {
                font-size: 1.8rem;
                letter-spacing: 1px;
            }

            .logo p {
                font-size: 0.8rem;
                color: #ccc;
            }

            nav ul {
                list-style: none;
                display: flex;
                gap: 1.5rem;
            }

            nav a {
                color: white;
                text-decoration: none;
                font-weight: bold;
                transition: color 0.3s;
            }

            nav a:hover {
                color: #e94560;
            }

            main {
                max-width: 1200px;
                margin: 2rem auto;
                padding: 0 1rem;
            }

            h2 {
                margin-bottom: 1.5rem;
                border-left: 5px solid #e94560;
                padding-left: 1rem;
            }

            .catalogo {
                display: grid;
                grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
                gap: 2rem;
            }

            .prodotto {
                background: white;
                border-radius: 12px;
                overflow: hidden;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                transition: transform 0.2s, box-shadow 0.2s;
            }

            .prodotto:hover {
                transform: translateY(-5px);
                box-shadow: 0 8px 16px rgba(0, 0, 0, 0.15);
            }

            .prodotto img {
                width: 100%;
                height: 200px;
                object-fit: cover;
                background-color: #e9e9e9;
            }

            .info {
                padding: 1rem;
            }

            .info h3 {
                font-size: 1.2rem;
                margin-bottom: 0.5rem;
            }

            .piattaforma {
                color: #e94560;
                font-weight: bold;
                margin-bottom: 0.5rem;
            }

            .prezzo {
                font-size: 1.3rem;
                font-weight: bold;
                color: #1a1a2e;
                margin: 0.5rem 0;
            }

            .btn {
                display: inline-block;
                background-color: #e94560;
                color: white;
                border: none;
                padding: 0.5rem 1rem;
                border-radius: 25px;
                cursor: pointer;
                font-weight: bold;
                text-align: center;
                transition: background 0.3s;
                width: 100%;
                margin-top: 0.5rem;
            }

            .btn:hover {
                background-color: #c5314b;
            }

            footer {
                background-color: #1a1a2e;
                color: #ccc;
                text-align: center;
                padding: 1.5rem;
                margin-top: 2rem;
                font-size: 0.9rem;
            }

            @media (max-width: 768px) {
                header {
                    flex-direction: column;
                    text-align: center;
                    gap: 1rem;
                }

                nav ul {
                    justify-content: center;
                    flex-wrap: wrap;
                    gap: 1rem;
                }

                .catalogo {
                    grid-template-columns: repeat(auto-fill, minmax(240px, 1fr));
                    gap: 1.5rem;
                }
            }

            @media (max-width: 480px) {
                main {
                    padding: 0 0.5rem;
                }

                .prodotto img {
                    height: 160px;
                }

                .info h3 {
                    font-size: 1rem;
                }

                .prezzo {
                    font-size: 1.1rem;
                }
            }
        </style>
    </head>

    <body>

        <header>
            <div class="logo">
                <h1>🎮 Buy 4 Play</h1>
                <p>Le migliori chiavi per i tuoi giochi</p>
            </div>
            <nav>
                <ul>
                    <li><a href="#">Home</a></li>
                    <li><a href="#">Catalogo</a></li>
                    <li><a href="#">Carrello</a></li>
                    <li><a href="#">Login</a></li>
                </ul>
            </nav>
        </header>

        <main>
            <h2>🔥 I più venduti</h2>
            <div class="catalogo">
                <div class="prodotto">
                    <img src="https://gaming-cdn.com/images/products/13588/orig/ea-sports-fc-24-pc-gioco-ea-app-cover.jpg?v=1696842619"
                        alt="FIFA 24">
                    <div class="info">
                        <h3>FIFA 24</h3>
                        <div class="piattaforma">PC / PS5 / Xbox</div>
                        <div class="prezzo">€ 49,99</div>
                        <button class="btn">🛒 Aggiungi al carrello</button>
                    </div>
                </div>
                <div class="prodotto">
                    <img src="https://gaming-cdn.com/images/products/15070/orig/call-of-duty-modern-warfare-iii-bundle-cross-gen-cross-gen-bundle-xbox-one-xbox-series-x-s-gioco-microsoft-store-cover.jpg?v=1739354531"
                        alt="Call of Duty">
                    <div class="info">
                        <h3>Call of Duty: Modern Warfare III</h3>
                        <div class="piattaforma">PC / PS5 / Xbox</div>
                        <div class="prezzo">€ 59,99</div>
                        <button class="btn">🛒 Aggiungi al carrello</button>
                    </div>
                </div>
                <div class="prodotto">
                    <img src="https://gaming-cdn.com/images/products/186/orig/grand-theft-auto-v-pc-gioco-rockstar-cover.jpg?v=1744367958"
                        alt="GTA V">
                    <div class="info">
                        <h3>Grand Theft Auto V</h3>
                        <div class="piattaforma">PC / PS5 / Xbox</div>
                        <div class="prezzo">€ 29,99</div>
                        <button class="btn">🛒 Aggiungi al carrello</button>
                    </div>
                </div>
                <div class="prodotto">
                    <img src="https://gaming-cdn.com/images/products/4824/orig/elden-ring-pc-steam-cover.jpg?v=1750231653"
                        alt="Elden Ring">
                    <div class="info">
                        <h3>Elden Ring</h3>
                        <div class="piattaforma">PC / PS5 / Xbox</div>
                        <div class="prezzo">€ 39,99</div>
                        <button class="btn">🛒 Aggiungi al carrello</button>
                    </div>
                </div>
            </div>
        </main>

        <footer>
            <p>&copy; 2026 Buy 4 Play - Tutti i diritti riservati. | Progetto TSW - Università di Salerno</p>
        </footer>

    </body>

    </html>