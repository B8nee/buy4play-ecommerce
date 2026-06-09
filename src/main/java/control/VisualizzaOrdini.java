package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.JsonObject;
import model.Ordine;
import model.OrdineDAO;
import model.Utente;

/**
 * Servlet per la gestione degli ordini da parte dell'amministratore.
 * Fornisce la visualizzazione degli ordini con filtri (data e cliente)
 * e supporta operazioni AJAX per aggiornare lo stato o eliminare un ordine.
 * Mappata sull'URL /admin/VisualizzaOrdini.
 */
@WebServlet("/admin/VisualizzaOrdini")
public class VisualizzaOrdini extends HttpServlet {

    /**
     * Gestisce la richiesta GET: mostra l'elenco degli ordini filtrati.
     * Parametri opzionali: dataDa, dataA, clienteEmail.
     * Solo l'amministratore autenticato può accedere.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Verifica che l'utente sia autenticato e abbia ruolo admin
        Utente admin = (Utente) request.getSession().getAttribute("utente");
        if (admin == null || !"admin".equals(admin.getRuolo())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        // Legge i parametri di filtro dalla request
        String dataDa = request.getParameter("dataDa");
        String dataA = request.getParameter("dataA");
        String clienteEmail = request.getParameter("clienteEmail");

        OrdineDAO dao = new OrdineDAO();
        try {
            // Recupera gli ordini con i filtri applicati
            List<Ordine> ordini = dao.getOrdiniConFiltri(dataDa, dataA, clienteEmail);
            request.setAttribute("ordini", ordini);
            // Inoltra alla JSP per la visualizzazione
            request.getRequestDispatcher("/admin/visualizzaOrdini.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500); // Errore interno del server
        }
    }

    /**
     * Gestisce le richieste POST per azioni sugli ordini (updateStatus,
     * deleteOrder).
     * Le richieste sono tipicamente AJAX e restituiscono una risposta JSON.
     * Solo l'amministratore può eseguire queste operazioni.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Verifica privilegi admin
        Utente admin = (Utente) request.getSession().getAttribute("utente");
        if (admin == null || !"admin".equals(admin.getRuolo())) {
            response.sendError(403); // Forbidden
            return;
        }
        String action = request.getParameter("action");
        JsonObject json = new JsonObject();
        OrdineDAO dao = new OrdineDAO();
        try {
            if ("updateStatus".equals(action)) {
                // Aggiorna lo stato di un ordine (es. "in_lavorazione", "spedito",
                // "consegnato")
                int id = Integer.parseInt(request.getParameter("id"));
                String stato = request.getParameter("stato");
                boolean ok = dao.aggiornaStatoOrdine(id, stato);
                json.addProperty("success", ok);
                json.addProperty("message", ok ? "Stato aggiornato" : "Errore");
            } else if ("deleteOrder".equals(action)) {
                // Elimina un ordine (solo se non ci sono vincoli di integrità)
                int id = Integer.parseInt(request.getParameter("id"));
                boolean ok = dao.eliminaOrdine(id);
                json.addProperty("success", ok);
                if (!ok)
                    json.addProperty("message", "Impossibile eliminare l'ordine");
            } else {
                json.addProperty("success", false);
                json.addProperty("message", "Azione non valida");
            }
        } catch (Exception e) {
            json.addProperty("success", false);
            json.addProperty("message", e.getMessage());
        }
        // Invia la risposta JSON al client
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(json.toString());
        out.flush();
    }
}