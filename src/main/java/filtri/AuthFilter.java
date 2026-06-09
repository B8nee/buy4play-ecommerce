package filtri;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Filtro di autenticazione per proteggere le pagine che richiedono un utente
 * loggato.
 * Intercetta le richieste verso /checkout, /profilo, /ordini.
 * Se l'utente non è autenticato (nessuna sessione o attributo "utente"),
 * viene reindirizzato alla pagina di login.
 * Mappato sugli URL /checkout, /profilo, /ordini.
 */
@WebFilter({ "/checkout", "/profilo", "/ordini" })
public class AuthFilter implements Filter {

    /**
     * Metodo principale del filtro. Verifica la presenza di un utente nella
     * sessione.
     * Se presente, prosegue; altrimenti reindirizza al login.
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        // Recupera l'oggetto utente dalla sessione (può essere di tipo Utente)
        Object utente = (session != null) ? session.getAttribute("utente") : null;

        if (utente == null) {
            // Utente non autenticato: reindirizza al login
            res.sendRedirect(req.getContextPath() + "/login.jsp");
        } else {
            // Utente autenticato: continua l'elaborazione della richiesta
            chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig fConfig) throws ServletException {
        // Inizializzazione (non richiede operazioni)
    }

    public void destroy() {
        // Pulizia (non richiede operazioni)
    }
}