package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.RememberMeDAO;

/**
 * Servlet per gestire il logout dell'utente.
 * Invalida la sessione corrente e cancella i cookie del "remember me",
 * rimuovendo anche il token associato dal database.
 * Mappata sull'URL /logout.
 */
@WebServlet("/logout")
public class LogoutControl extends HttpServlet {

    /**
     * Gestisce la richiesta GET di logout.
     * La sessione viene invalidata, i cookie remember_me vengono cancellati
     * e il token rimosso dal database per evitare riutilizzo.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Invalida la sessione esistente (senza crearne una nuova)
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Variabile per memorizzare la serie del cookie remember_me
        String serie = null;

        // Itera su tutti i cookie della request per trovare quelli di remember me
        if (request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                if ("remember_me_serie".equals(c.getName())) {
                    serie = c.getValue();
                    // Cancella il cookie impostando la durata a 0
                    c.setMaxAge(0);
                    c.setPath(request.getContextPath() + "/");
                    response.addCookie(c);
                }
                if ("remember_me_token".equals(c.getName())) {
                    // Cancella anche il cookie del token
                    c.setMaxAge(0);
                    c.setPath(request.getContextPath() + "/");
                    response.addCookie(c);
                }
            }
        }

        // Se esiste una serie, elimina il token corrispondente dal database
        if (serie != null) {
            try {
                new RememberMeDAO().eliminaToken(serie);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Reindirizza alla home page
        response.sendRedirect("index.jsp");
    }
}