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
import model.Utente;
import model.UtenteDAO;

/**
 * Servlet per la gestione degli utenti da parte dell'amministratore.
 * Permette di visualizzare la lista degli utenti, aggiornare il ruolo
 * (cliente/admin)
 * ed eliminare un utente.
 * Le operazioni di modifica sono gestite in POST con risposta JSON.
 * Mappata sull'URL /admin/gestioneUtenti.
 */
@WebServlet("/admin/gestioneUtenti")
public class GestioneUtentiControl extends HttpServlet {

    /**
     * Gestisce le richieste GET: mostra la lista di tutti gli utenti.
     * Solo l'amministratore può accedere.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Verifica che l'utente sia autenticato e abbia ruolo admin
        Utente admin = (Utente) request.getSession().getAttribute("utente");
        if (admin == null || !"admin".equals(admin.getRuolo())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        UtenteDAO dao = new UtenteDAO();
        try {
            // Recupera tutti gli utenti dal DAO
            List<Utente> utenti = dao.getAllUtenti();
            request.setAttribute("utenti", utenti);
            // Inoltra alla JSP per la visualizzazione
            request.getRequestDispatcher("/admin/gestioneUtenti.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500); // Errore interno del server
        }
    }

    /**
     * Gestisce le richieste POST: azioni di aggiornamento ruolo o eliminazione
     * utente.
     * Le richieste sono tipicamente AJAX (JSON) e restituiscono un oggetto JSON con
     * un campo "success".
     * Parametro "action" può essere "updateRole" o "delete".
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
        UtenteDAO dao = new UtenteDAO();
        try {
            if ("updateRole".equals(action)) {
                // Aggiorna il ruolo di un utente
                int id = Integer.parseInt(request.getParameter("id"));
                String ruolo = request.getParameter("ruolo");
                boolean ok = dao.aggiornaRuoloUtente(id, ruolo);
                json.addProperty("success", ok);
            } else if ("delete".equals(action)) {
                // Elimina un utente (e i suoi ordini in cascata, se la FK lo prevede)
                int id = Integer.parseInt(request.getParameter("id"));
                boolean ok = dao.eliminaUtente(id);
                json.addProperty("success", ok);
            } else {
                json.addProperty("success", false);
                json.addProperty("message", "Azione non valida");
            }
        } catch (Exception e) {
            json.addProperty("success", false);
            json.addProperty("message", e.getMessage());
        }
        // Invia risposta JSON al client
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(json.toString());
        out.flush();
    }
}