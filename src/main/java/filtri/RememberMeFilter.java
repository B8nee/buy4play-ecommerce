package filtri;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Utente;
import model.UtenteDAO;
import model.RememberMeDAO;

@WebFilter("/*")
public class RememberMeFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        if (uri.endsWith("/login") || uri.endsWith("/logout") ||
            uri.contains("/css/") || uri.contains("/js/") || uri.contains("/images/") ||
            uri.endsWith(".css") || uri.endsWith(".js") || uri.endsWith(".png") || uri.endsWith(".jpg")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("utente") == null) {
            String serie = null, token = null;
            if (req.getCookies() != null) {
                for (Cookie c : req.getCookies()) {
                    if ("remember_me_serie".equals(c.getName())) serie = c.getValue();
                    if ("remember_me_token".equals(c.getName())) token = c.getValue();
                }
            }
            if (serie != null && token != null) {
                RememberMeDAO rmDao = new RememberMeDAO();
                try {
                    int utenteId = rmDao.validaToken(serie, token);
                    if (utenteId != -1) {
                        UtenteDAO uDao = new UtenteDAO();
                        Utente utente = uDao.getUtenteById(utenteId);
                        if (utente != null) {
                            session = req.getSession(true);
                            session.setAttribute("utente", utente);
                        }
                    } else {
                        Cookie cSerie = new Cookie("remember_me_serie", "");
                        cSerie.setMaxAge(0);
                        cSerie.setPath(req.getContextPath() + "/");
                        res.addCookie(cSerie);
                        Cookie cToken = new Cookie("remember_me_token", "");
                        cToken.setMaxAge(0);
                        cToken.setPath(req.getContextPath() + "/");
                        res.addCookie(cToken);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        chain.doFilter(request, response);
    }

    @Override public void init(FilterConfig filterConfig) throws ServletException {}
    @Override public void destroy() {}
}