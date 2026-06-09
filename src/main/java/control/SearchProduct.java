package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import model.Prodotto;
import model.ProdottoDAO;

/**
 * Servlet per la ricerca in tempo reale dei prodotti (tipo "Google Suggest").
 * Riceve una query parziale (parametro "q") e restituisce un elenco di prodotti
 * che corrispondono al nome, in formato JSON.
 * Utilizzata da funzioni AJAX per autocompletamento nella barra di ricerca.
 * Mappata sull'URL /SearchProduct.
 */
@WebServlet("/SearchProduct")
public class SearchProduct extends HttpServlet {

    /**
     * Gestisce le richieste GET per la ricerca asincrona.
     * Parametro atteso: "q" (stringa di ricerca parziale).
     * Restituisce un array JSON di prodotti (nome, prezzo, id, ecc.)
     * oppure un array vuoto se la query è assente.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Legge il parametro "q" (query string) dalla richiesta
        String query = request.getParameter("q");

        // Se la query è nulla o vuota, restituisce un array JSON vuoto
        if (query == null || query.trim().isEmpty()) {
            response.setContentType("application/json");
            response.getWriter().write("[]");
            return;
        }

        ProdottoDAO dao = new ProdottoDAO();
        try {
            // Esegue la ricerca prodotti nel DB (match parziale sul nome)
            List<Prodotto> results = dao.searchProducts(query.trim());

            // Prepara la risposta come JSON
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            Gson gson = new Gson(); // Converte l'oggetto Java in JSON
            out.print(gson.toJson(results)); // Scrive il JSON nel body della response
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            // In caso di errore restituisce codice HTTP 500 (Internal Server Error)
            response.setStatus(500);
        }
    }
}