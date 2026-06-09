package control;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.JsonObject;
import model.UtenteDAO;

/**
 * Servlet che verifica se un indirizzo email è già presente nel database.
 * Utilizzata durante la registrazione (AJAX) per fornire un feedback immediato
 * all'utente.
 * Risponde con un oggetto JSON { "exists": true/false }.
 * Mappata sull'URL /CheckEmail.
 */
@WebServlet("/CheckEmail")
public class CheckEmail extends HttpServlet {

    /**
     * Gestisce le richieste GET (di solito chiamate via AJAX).
     * Il parametro "email" viene letto dalla query string.
     * Restituisce un JSON con la proprietà "exists" true se l'email è già
     * registrata, false altrimenti.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        boolean exists = false;

        if (email != null && !email.isEmpty()) {
            UtenteDAO dao = new UtenteDAO();
            try {
                exists = dao.emailEsistente(email); // Interroga il DB per verificare l'esistenza dell'email
            } catch (Exception e) {
                e.printStackTrace();
                // In caso di errore, exists rimane false (comportamento prudenziale)
            }
        }

        // Imposta il tipo di contenuto come JSON
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JsonObject json = new JsonObject();
        json.addProperty("exists", exists);
        out.print(json.toString()); // Invia la risposta JSON
    }
}