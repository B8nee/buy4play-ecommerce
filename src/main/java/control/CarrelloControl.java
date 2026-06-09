package control;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import model.Cart;
import model.CartItem;
import model.Prodotto;
import model.ProdottoDAO;

/**
 * Servlet per la gestione del carrello della spesa.
 * Supporta operazioni AJAX (aggiunta, rimozione, aggiornamento quantità,
 * svuotamento)
 * e restituisce risposte in formato JSON per aggiornamenti dinamici della
 * pagina.
 * Fornisce anche un'azione 'count' per ottenere il numero totale di articoli
 * (usato dal badge).
 * Mappata sull'URL /carrello.
 */
@WebServlet("/carrello")
public class CarrelloControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Gson gson = new Gson(); // Istanza di Gson per la serializzazione JSON

    /**
     * Gestisce sia richieste GET che POST (delega POST a GET).
     * Distingue tra richieste normali (visualizzazione JSP) e AJAX (operazioni sul
     * carrello).
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        // Recupera il carrello dalla sessione (o ne crea uno nuovo)
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        // Controlla se la richiesta è AJAX tramite l'header X-Requested-With
        boolean isAjax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));

        // Azione speciale 'count' per il badge del carrello (restituisce il numero
        // totale di articoli)
        if ("count".equals(action)) {
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            JsonObject json = new JsonObject();
            int count = 0;
            for (CartItem item : cart.getItems()) {
                count += item.getQuantita(); // Somma le quantità di tutti gli articoli
            }
            json.addProperty("count", count);
            out.print(gson.toJson(json));
            out.flush();
            return;
        }

        // Se non c'è azione e non è AJAX, mostra la JSP del carrello (caricamento
        // normale)
        if (action == null && !isAjax) {
            request.getRequestDispatcher("/carrello.jsp").forward(request, response);
            return;
        }

        // Per tutte le altre azioni (add, remove, update, clear) restituisce JSON
        JsonObject jsonResponse = new JsonObject();
        try {
            if ("add".equals(action)) {
                // Aggiunge un prodotto al carrello
                String idParam = request.getParameter("id");
                if (idParam != null) {
                    int id = Integer.parseInt(idParam);
                    ProdottoDAO dao = new ProdottoDAO();
                    Prodotto p = dao.doRetrieveById(id);
                    if (p != null) {
                        cart.addItem(p); // Metodo del carrello che aggiunge o incrementa quantità
                    }
                }
                jsonResponse.addProperty("success", true);
            } else if ("remove".equals(action)) {
                // Rimuove un prodotto dal carrello
                String removeId = request.getParameter("id");
                if (removeId != null) {
                    cart.removeItem(Integer.parseInt(removeId));
                }
                jsonResponse.addProperty("success", true);
            } else if ("update".equals(action)) {
                // Aggiorna la quantità di un prodotto
                String updateId = request.getParameter("id");
                String qty = request.getParameter("qty");
                if (updateId != null && qty != null) {
                    int id = Integer.parseInt(updateId);
                    int newQty = Integer.parseInt(qty);
                    cart.updateQuantity(id, newQty);
                    // Calcola i nuovi valori per la UI (quantità e subtotale aggiornati)
                    int newQuantity = 0;
                    double newSubtotal = 0;
                    for (CartItem item : cart.getItems()) {
                        if (item.getProdotto().getId() == id) {
                            newQuantity = item.getQuantita();
                            newSubtotal = item.getSubtotale();
                            break;
                        }
                    }
                    jsonResponse.addProperty("success", true);
                    jsonResponse.addProperty("newQuantity", newQuantity);
                    jsonResponse.addProperty("newSubtotal", newSubtotal);
                } else {
                    jsonResponse.addProperty("success", false);
                    jsonResponse.addProperty("message", "Parametri mancanti");
                }
            } else if ("clear".equals(action)) {
                // Svuota completamente il carrello
                cart.clear();
                jsonResponse.addProperty("success", true);
            } else {
                // Azione non riconosciuta
                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message", "Azione non valida");
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "Errore del server: " + e.getMessage());
        }

        // Invia la risposta JSON al client per l'aggiornamento dinamico della pagina
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(jsonResponse));
        out.flush();
    }

    /**
     * Delega le richieste POST al metodo doGet per uniformare la gestione.
     * In questo modo le stesse operazioni possono essere invocate sia con GET che
     * con POST.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}