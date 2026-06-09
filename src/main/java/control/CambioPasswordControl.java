package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Utente;
import model.UtenteDAO;
import utility.PasswordHasher;

/**
 * Servlet per il cambio password da parte dell'utente autenticato.
 * Richiede la vecchia password, la nuova e la conferma.
 * La nuova password viene hashata prima di essere salvata nel database.
 */
@WebServlet("/cambioPassword")
public class CambioPasswordControl extends HttpServlet {

    /**
     * Gestisce la richiesta POST di cambio password.
     * Verifica l'autenticazione, valida i campi e aggiorna la password nel DB.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Recupera la sessione corrente (senza crearne una nuova)
        HttpSession session = request.getSession(false);
        // Ottiene l'utente loggato dalla sessione
        Utente utente = (session != null) ? (Utente) session.getAttribute("utente") : null;
        if (utente == null) {
            // Se l'utente non è autenticato, reindirizza al login
            response.sendRedirect("login.jsp");
            return;
        }

        // Legge i parametri inviati dal form
        String oldPass = request.getParameter("oldPassword");
        String newPass = request.getParameter("newPassword");
        String confirm = request.getParameter("confirmNewPassword");

        // Verifica che la nuova password e la conferma coincidano
        if (!newPass.equals(confirm)) {
            request.setAttribute("errore", "Le nuove password non coincidono");
            request.getRequestDispatcher("/cambioPassword.jsp").forward(request, response);
            return;
        }

        // Calcola l'hash della vecchia password inserita
        String oldHash = PasswordHasher.toHash(oldPass);
        UtenteDAO dao = new UtenteDAO();
        try {
            // Verifica che la vecchia password sia corretta confrontando l'hash
            Utente verificato = dao.login(utente.getEmail(), oldHash);
            if (verificato == null) {
                // La password attuale è errata
                request.setAttribute("errore", "Password attuale errata");
                request.getRequestDispatcher("/cambioPassword.jsp").forward(request, response);
                return;
            }

            // Genera l'hash della nuova password
            String newHash = PasswordHasher.toHash(newPass);
            if (dao.aggiornaPassword(utente.getId(), newHash)) {
                // Aggiornamento riuscito
                request.setAttribute("messaggio", "Password cambiata con successo");
                request.getRequestDispatcher("/cambioPassword.jsp").forward(request, response);
            } else {
                // Aggiornamento fallito (errore del DAO)
                request.setAttribute("errore", "Errore durante l'aggiornamento");
                request.getRequestDispatcher("/cambioPassword.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // In caso di eccezione generica, reindirizza alla pagina di errore
            response.sendRedirect("error.jsp");
        }
    }
}