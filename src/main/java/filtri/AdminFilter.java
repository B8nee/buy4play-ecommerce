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
import model.Utente;

/**
 * Filtro per la protezione delle risorse nell'area admin.
 * Intercetta tutte le richieste che iniziano con /admin/* e verifica
 * che l'utente sia autenticato e abbia ruolo "admin".
 * In caso contrario, reindirizza alla pagina di login.
 */
@WebFilter({ "/admin/*" })
public class AdminFilter implements Filter {

    /**
     * Metodo principale del filtro, eseguito prima che la richiesta raggiunga la
     * servlet/JSP.
     * Controlla la presenza di un utente admin nella sessione.
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // Cast a HttpServletRequest e HttpServletResponse per accedere a metodi
        // specifici
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // Recupera la sessione (senza crearne una nuova)
        HttpSession session = req.getSession(false);
        Utente utente = (session != null) ? (Utente) session.getAttribute("utente") : null;

        // Se l'utente non è loggato o non è admin, reindirizza al login
        if (utente == null || !"admin".equals(utente.getRuolo())) {
            res.sendRedirect(req.getContextPath() + "/login.jsp");
        } else {
            // Altrimenti, lascia proseguire la richiesta
            chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig fConfig) throws ServletException {
        // Inizializzazione del filtro (non richiede operazioni)
    }

    public void destroy() {
        // Pulizia del filtro (non richiede operazioni)
    }
}