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
import model.Utente;

/**
 * Servlet per la gestione dei prodotti da parte dell'amministratore.
 * Gestisce le operazioni CRUD (Create, Read, Update, Delete) e fornisce
 * l'elenco dei prodotti con paginazione.
 * Mappata sull'URL /admin/GestioneProdotti.
 */
@WebServlet("/admin/GestioneProdotti")
public class AdminProdottiControl extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Gestisce le richieste HTTP GET.
     * Le azioni supportate:
     * - "get" : restituisce i dati di un singolo prodotto in formato JSON (per la
     * modifica via AJAX).
     * - "delete" : elimina un prodotto (con controllo di integrità referenziale).
     * - nessuna : visualizza la lista dei prodotti con paginazione.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Verifica che l'utente sia autenticato e abbia ruolo admin
        Utente admin = (Utente) request.getSession().getAttribute("utente");
        if (admin == null || !"admin".equals(admin.getRuolo())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String action = request.getParameter("action");
        ProdottoDAO dao = new ProdottoDAO();

        try {
            // ---- Azione "get": restituisce i dati di un prodotto in JSON ----
            if ("get".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                Prodotto p = dao.doRetrieveById(id);
                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                Gson gson = new Gson(); // Libreria Google Gson per convertire oggetti in JSON
                out.print(gson.toJson(p)); // Invia il prodotto come JSON
                out.flush();
                return;
            }
            // ---- Azione "delete": elimina un prodotto ----
            else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                boolean deleted = dao.doDelete(id);
                if (deleted) {
                    request.getSession().setAttribute("messaggio", "Prodotto eliminato con successo");
                } else {
                    // Se il prodotto è referenziato in ordini, la cancellazione non è permessa
                    request.getSession().setAttribute("errore",
                            "Impossibile eliminare: prodotto presente in ordini esistenti");
                }
                // Redireziona alla stessa servlet per ricaricare la lista
                response.sendRedirect(request.getContextPath() + "/admin/GestioneProdotti");
                return;
            }

            // ---- Azione di default: mostra la lista dei prodotti con paginazione ----
            int page = 1;
            int limit = 10; // Numero di prodotti per pagina
            String pageParam = request.getParameter("page");
            if (pageParam != null) {
                page = Integer.parseInt(pageParam);
                if (page < 1)
                    page = 1;
            }
            int offset = (page - 1) * limit; // Calcolo OFFSET per la query SQL
            List<Prodotto> prodotti = dao.doRetrieveAll(offset, limit);
            int totalProdotti = dao.countProdotti();
            int totalPages = (int) Math.ceil((double) totalProdotti / limit);

            // Passa i dati alla JSP
            request.setAttribute("prodotti", prodotti);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.getRequestDispatcher("/admin/gestioneProdotti.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500); // Errore interno del server
        }
    }

    /**
     * Gestisce le richieste HTTP POST.
     * Le azioni supportate:
     * - "add" : inserisce un nuovo prodotto.
     * - "update" : aggiorna un prodotto esistente.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Verifica che l'utente sia autenticato e abbia ruolo admin
        Utente admin = (Utente) request.getSession().getAttribute("utente");
        if (admin == null || !"admin".equals(admin.getRuolo())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String action = request.getParameter("action");
        ProdottoDAO dao = new ProdottoDAO();
        try {
            // Crea un oggetto Prodotto con i dati inviati dal form
            Prodotto p = new Prodotto();
            p.setNome(request.getParameter("nome"));
            p.setPiattaforma(request.getParameter("piattaforma"));
            p.setPrezzo(Double.parseDouble(request.getParameter("prezzo")));
            p.setImmagineUrl(request.getParameter("immagineUrl"));
            // Il checkbox "popolare" è presente nella request solo se spuntato
            p.setPopolare(request.getParameter("popolare") != null);

            if ("add".equals(action)) {
                dao.doSave(p); // Inserimento nel DB
                request.getSession().setAttribute("messaggio", "Prodotto aggiunto con successo");
            } else if ("update".equals(action)) {
                p.setId(Integer.parseInt(request.getParameter("id")));
                dao.doUpdate(p); // Aggiornamento nel DB
                request.getSession().setAttribute("messaggio", "Prodotto aggiornato con successo");
            }
            // Al termine, redireziona alla servlet GET per visualizzare la lista aggiornata
            response.sendRedirect(request.getContextPath() + "/admin/GestioneProdotti");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errore", "Errore durante il salvataggio");
            response.sendRedirect(request.getContextPath() + "/admin/GestioneProdotti");
        }
    }
}