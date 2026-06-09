package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Utente;
import model.UtenteDAO;
import model.RememberMeDAO;
import utility.PasswordHasher;
import utility.TokenGenerator;

/**
 * Servlet per l'autenticazione degli utenti.
 * Gestisce il login, la creazione della sessione e l'impostazione
 * del cookie "remember me" per il login persistente.
 * Mappata sull'URL /login.
 */
@WebServlet("/login")
public class LoginControl extends HttpServlet {

    /**
     * Gestisce la richiesta POST di login.
     * Riceve email e password, verifica le credenziali,
     * crea la sessione e opzionalmente imposta cookie di persistenza.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Legge i parametri dal form di login
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe"); // "on" se spuntato

        // Validazione base dei campi
        if (email == null || password == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Calcola l'hash della password inserita (SHA-512)
        String hash = PasswordHasher.toHash(password);
        UtenteDAO dao = new UtenteDAO();
        try {
            // Verifica credenziali nel database
            Utente utente = dao.login(email, hash);
            if (utente != null) {
                // Login riuscito: crea la sessione e salva l'oggetto Utente
                HttpSession session = request.getSession();
                session.setAttribute("utente", utente);

                // ----- Gestione "Remember Me" (login persistente) -----
                if ("on".equals(rememberMe)) {
                    // Genera serie (identificativo univoco) e token casuale
                    String serie = RememberMeDAO.generaSerie();
                    String token = TokenGenerator.generaToken();
                    long durata = 30L * 24 * 60 * 60; // 30 giorni in secondi

                    // Salva la coppia (serie, token) nel database associata all'utente
                    new RememberMeDAO().salvaToken(utente.getId(), serie, token, durata);

                    // Crea i cookie per la serie e il token
                    Cookie cookieSerie = new Cookie("remember_me_serie", serie);
                    Cookie cookieToken = new Cookie("remember_me_token", token);
                    cookieSerie.setMaxAge((int) durata);
                    cookieToken.setMaxAge((int) durata);
                    // Imposta il path sul contesto dell'applicazione (per disponibilità su tutte le
                    // pagine)
                    cookieSerie.setPath(request.getContextPath() + "/");
                    cookieToken.setPath(request.getContextPath() + "/");
                    response.addCookie(cookieSerie);
                    response.addCookie(cookieToken);
                }

                // Reindirizza al catalogo (area pubblica dopo login)
                response.sendRedirect("catalogo");
            } else {
                // Credenziali errate: mostra messaggio di errore nella pagina di login
                request.setAttribute("errore", "Email o password errati");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // In caso di eccezione (es. errore DB), reindirizza alla pagina di login
            response.sendRedirect("login.jsp");
        }
    }
}