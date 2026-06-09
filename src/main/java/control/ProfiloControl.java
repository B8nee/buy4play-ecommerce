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

/**
 * Servlet per la gestione del profilo utente.
 * Permette di visualizzare e modificare i dati personali dell'utente
 * autenticato.
 * Mappata sull'URL /profilo.
 */
@WebServlet("/profilo")
public class ProfiloControl extends HttpServlet {

    /**
     * Gestisce la richiesta GET: mostra il profilo dell'utente.
     * Recupera l'utente dalla sessione e lo passa alla JSP per la visualizzazione.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Ottiene la sessione corrente (senza crearne una nuova)
        HttpSession session = request.getSession(false);
        // Recupera l'oggetto Utente dalla sessione
        Utente utente = (session != null) ? (Utente) session.getAttribute("utente") : null;
        if (utente == null) {
            // Se l'utente non è loggato, reindirizza al login
            response.sendRedirect("login.jsp");
            return;
        }
        // Passa l'utente alla JSP come attributo
        request.setAttribute("utente", utente);
        // Inoltra alla pagina profilo.jsp per la visualizzazione
        request.getRequestDispatcher("/profilo.jsp").forward(request, response);
    }

    /**
     * Gestisce la richiesta POST: aggiorna i dati del profilo.
     * Riceve i nuovi valori dal form, aggiorna l'oggetto Utente in sessione
     * e salva le modifiche nel database tramite DAO.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Ottiene la sessione corrente
        HttpSession session = request.getSession(false);
        Utente utenteLoggato = (session != null) ? (Utente) session.getAttribute("utente") : null;
        if (utenteLoggato == null) {
            // Se l'utente non è loggato, reindirizza al login
            response.sendRedirect("login.jsp");
            return;
        }

        // Legge i parametri inviati dal form (nuovi valori)
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String indirizzo = request.getParameter("indirizzo");
        String citta = request.getParameter("citta");
        String provincia = request.getParameter("provincia");
        String cap = request.getParameter("cap");

        // Aggiorna i campi dell'oggetto Utente con i nuovi valori
        utenteLoggato.setNome(nome);
        utenteLoggato.setCognome(cognome);
        utenteLoggato.setIndirizzo(indirizzo);
        utenteLoggato.setCitta(citta);
        utenteLoggato.setProvincia(provincia);
        utenteLoggato.setCap(cap);

        // Aggiorna i dati nel database tramite DAO
        UtenteDAO dao = new UtenteDAO();
        try {
            dao.aggiornaUtente(utenteLoggato); // Persiste le modifiche
            session.setAttribute("utente", utenteLoggato); // Aggiorna anche l'oggetto in sessione
            request.setAttribute("messaggio", "Profilo aggiornato con successo");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errore", "Errore durante l'aggiornamento");
        }
        // Passa l'utente aggiornato alla JSP e inoltra
        request.setAttribute("utente", utenteLoggato);
        request.getRequestDispatcher("/profilo.jsp").forward(request, response);
    }
}