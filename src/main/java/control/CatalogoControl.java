package control;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Prodotto;
import model.ProdottoDAO;

/**
 * Servlet per la visualizzazione del catalogo prodotti.
 * Gestisce la ricerca testuale, la paginazione, il filtraggio per piattaforma
 * e l'ordinamento per prezzo.
 * Mappata sull'URL /catalogo.
 */
@WebServlet("/catalogo")
public class CatalogoControl extends HttpServlet {

    /**
     * Gestisce le richieste GET per il catalogo.
     * Se è presente un parametro "search", mostra i risultati della ricerca.
     * Altrimenti, gestisce la paginazione, i filtri e l'ordinamento.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Parametri per la ricerca testuale (es. dalla barra di ricerca)
        String search = request.getParameter("search");
        // Parametro per il filtro di piattaforma (es. PC, PS5, Xbox)
        String platform = request.getParameter("platform");
        // Parametro per l'ordinamento (es. prezzo_asc, prezzo_desc)
        String sort = request.getParameter("sort");

        // Impostazioni di paginazione: pagina corrente (default 1) e prodotti per
        // pagina (default 12)
        int page = 1;
        int limit = 12;
        String pageParam = request.getParameter("page");
        String limitParam = request.getParameter("limit");

        // Lettura e validazione della pagina corrente
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                page = Integer.parseInt(pageParam);
                if (page < 1)
                    page = 1; // La pagina non può essere minore di 1
            } catch (NumberFormatException e) {
            }
        }
        // Lettura e validazione del limite di prodotti per pagina
        if (limitParam != null && !limitParam.isEmpty()) {
            try {
                limit = Integer.parseInt(limitParam);
                if (limit < 1)
                    limit = 12; // Valore minimo di sicurezza
            } catch (NumberFormatException e) {
            }
        }
        // Calcolo dell'offset (primo prodotto da recuperare) per la query SQL
        int offset = (page - 1) * limit;

        // Istanza del DAO per l'accesso al database
        ProdottoDAO dao = new ProdottoDAO();
        try {
            List<Prodotto> prodotti;
            int totalProdotti;

            // ----------------------------------------------------------------
            // Se è stata effettuata una ricerca testuale (parametro "search" non vuoto)
            // ----------------------------------------------------------------
            if (search != null && !search.trim().isEmpty()) {
                // Utilizza il metodo che combina la ricerca testuale con filtri e paginazione
                prodotti = dao.searchProductsWithFilters(search.trim(), offset, limit, platform, sort);
                // Conteggio dei prodotti che corrispondono alla ricerca (con eventuale filtro
                // piattaforma)
                totalProdotti = dao.countSearchProducts(search.trim(), platform);
                // Passa la stringa di ricerca alla JSP per mostrarla nell'interfaccia
                request.setAttribute("searchQuery", search);
            } else {
                // ----------------------------------------------------------------
                // Modalità normale (catalogo completo) con filtri, ordinamento e paginazione
                // ----------------------------------------------------------------
                prodotti = dao.doRetrieveAll(offset, limit, platform, sort);
                totalProdotti = dao.countProdotti(platform);
            }

            // Calcolo del numero totale di pagine (arrotondato per eccesso)
            int totalPages = (int) Math.ceil((double) totalProdotti / limit);

            // Imposta gli attributi da passare alla JSP
            request.setAttribute("listaProdotti", prodotti); // Lista dei prodotti per la pagina corrente
            request.setAttribute("currentPage", page); // Pagina corrente
            request.setAttribute("totalPages", totalPages); // Numero totale di pagine
            request.setAttribute("limit", limit); // Prodotti per pagina
            request.setAttribute("platform", platform); // Filtro piattaforma selezionato
            request.setAttribute("sort", sort); // Ordinamento selezionato

            // Inoltra la richiesta alla JSP che visualizzerà il catalogo
            request.getRequestDispatcher("/catalogo.jsp").forward(request, response);
        } catch (Exception e) {
            // In caso di errore (es. problemi di connessione al DB), stampa lo stack trace
            e.printStackTrace();
            // Imposta un attributo di errore per la JSP e inoltra comunque alla pagina
            // catalogo,
            // che mostrerà un messaggio appropriato
            request.setAttribute("error", "Errore nel caricamento del catalogo");
            request.getRequestDispatcher("/catalogo.jsp").forward(request, response);
        }
    }
}