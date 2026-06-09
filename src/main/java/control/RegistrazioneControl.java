package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Utente;
import model.UtenteDAO;
import utility.PasswordHasher;

/**
 * Servlet per la registrazione di un nuovo utente.
 * Riceve i dati del form di registrazione, valida i campi,
 * verifica che l'email non sia già utilizzata, crea l'utente
 * nel database (con password hashata) e reindirizza alla pagina di login.
 * Mappata sull'URL /registrazione.
 */
@WebServlet("/registrazione")
public class RegistrazioneControl extends HttpServlet {

    /**
     * Gestisce la richiesta POST di registrazione.
     * Legge tutti i parametri dal form, esegue controlli di validità,
     * salva l'utente se i dati sono corretti, altrimenti mostra errori.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Legge tutti i campi del form di registrazione
        String email = request.getParameter("email");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String indirizzo = request.getParameter("indirizzo");
        String citta = request.getParameter("citta");
        String provincia = request.getParameter("provincia");
        String cap = request.getParameter("cap");
        String password = request.getParameter("password");
        String conferma = request.getParameter("conferma");

        // Validazione di base: campi obbligatori e corrispondenza password
        if (email == null || password == null || nome == null || cognome == null || !password.equals(conferma)) {
            request.setAttribute("errore", "Dati non validi o password non coincidenti");
            request.getRequestDispatcher("/registrazione.jsp").forward(request, response);
            return;
        }

        UtenteDAO dao = new UtenteDAO();
        try {
            // Verifica se l'email è già stata registrata
            if (dao.emailEsistente(email)) {
                request.setAttribute("errore", "Email già registrata");
                request.getRequestDispatcher("/registrazione.jsp").forward(request, response);
                return;
            }

            // Crea un nuovo oggetto Utente con i dati forniti
            Utente u = new Utente();
            u.setEmail(email);
            u.setNome(nome);
            u.setCognome(cognome);
            u.setIndirizzo(indirizzo);
            u.setCitta(citta);
            u.setProvincia(provincia);
            u.setCap(cap);
            u.setPasswordHash(PasswordHasher.toHash(password)); // Hash della password (SHA-512)
            u.setRuolo("cliente"); // Il ruolo di default è "cliente"

            // Tenta di salvare l'utente nel database
            if (dao.registraUtente(u)) {
                // Registrazione riuscita: mostra messaggio di successo e reindirizza al login
                request.setAttribute("messaggio", "Registrazione completata. Ora puoi fare login.");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            } else {
                // Registrazione fallita per errore sconosciuto del DAO
                request.setAttribute("errore", "Errore durante la registrazione");
                request.getRequestDispatcher("/registrazione.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errore", "Errore interno");
            request.getRequestDispatcher("/registrazione.jsp").forward(request, response);
        }
    }
}