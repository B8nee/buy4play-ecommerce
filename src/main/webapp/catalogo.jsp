<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.Prodotto" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Buy 4 Play - Catalogo</title>
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
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            transition: transform 0.2s, box-shadow 0.2s;
        }
        .prodotto:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 16px rgba(0,0,0,0.15);
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
        .pagination {
		    text-align: center;
		    margin: 2rem 0;
		}
		.pagination a, .pagination span {
		    display: inline-block;
		    padding: 0.5rem 0.8rem;
		    margin: 0 0.2rem;
		    border: 1px solid #ddd;
		    border-radius: 5px;
		    text-decoration: none;
		    color: #333;
		}
		.pagination a:hover {
		    background-color: #e94560;
		    color: white;
		    border-color: #e94560;
		}
		.pagination span.active {
		    background-color: #e94560;
		    color: white;
		    border-color: #e94560;
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
        <%
            List<Prodotto> prodotti = (List<Prodotto>) request.getAttribute("listaProdotti");
            if (prodotti != null && !prodotti.isEmpty()) {
                for (Prodotto p : prodotti) {
        %>
        <div class="prodotto">
            <img src="<%= p.getImmagineUrl() %>" alt="<%= p.getNome() %>">
            <div class="info">
                <a href="dettaglio?id=<%= p.getId() %>">
    				<h3><%= p.getNome() %></h3>
				</a>
                <div class="piattaforma"><%= p.getPiattaforma() %></div>
                <div class="prezzo">€ <%= String.format("%.2f", p.getPrezzo()) %></div>
                <form action="carrello" method="get">
                    <input type="hidden" name="action" value="add">
                    <input type="hidden" name="id" value="<%= p.getId() %>">
                    <button type="submit" class="btn">🛒 Aggiungi al carrello</button>
                </form>
            </div>
        </div>
        <%
                }
            } else {
        %>
        <p>Nessun prodotto disponibile al momento.</p>
        <% } %>
    </div>
	<div class="pagination">
	    <%
	        int currentPage = (Integer) request.getAttribute("currentPage");
	        int totalPages = (Integer) request.getAttribute("totalPages");
	        int limit = (Integer) request.getAttribute("limit");
	        
	        if (totalPages > 1) {
	            if (currentPage > 1) {
	    %>
	                <a href="catalogo?page=<%= currentPage - 1 %>&limit=<%= limit %>">« Precedente</a>
	    <%
	            }
	            for (int p = 1; p <= totalPages; p++) {
	                if (p == currentPage) {
	    %>
	                    <span class="active"><%= p %></span>
	    <%
	                } else {
	    %>
	                    <a href="catalogo?page=<%= p %>&limit=<%= limit %>"><%= p %></a>
	    <%
	                }
	            }
	            if (currentPage < totalPages) {
	    %>
	                <a href="catalogo?page=<%= currentPage + 1 %>&limit=<%= limit %>">Successivo »</a>
	    <%
	            }
	        }
	    %>
	</div>
</main>

<footer>
    <p>&copy; 2026 Buy 4 Play - Tutti i diritti riservati. | Progetto TSW - Università di Salerno</p>
</footer>

</body>
</html>
