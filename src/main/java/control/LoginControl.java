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

@WebServlet("/login")
public class LoginControl extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");

        if (email == null || password == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String hash = PasswordHasher.toHash(password);
        UtenteDAO dao = new UtenteDAO();
        try {
            Utente utente = dao.login(email, hash);
            if (utente != null) {
                HttpSession session = request.getSession();
                session.setAttribute("utente", utente);

                if ("on".equals(rememberMe)) {
                    String serie = RememberMeDAO.generaSerie();
                    String token = TokenGenerator.generaToken();
                    long durata = 30L * 24 * 60 * 60;
                    new RememberMeDAO().salvaToken(utente.getId(), serie, token, durata);

                    Cookie cookieSerie = new Cookie("remember_me_serie", serie);
                    Cookie cookieToken = new Cookie("remember_me_token", token);
                    cookieSerie.setMaxAge((int) durata);
                    cookieToken.setMaxAge((int) durata);
                    cookieSerie.setPath(request.getContextPath() + "/");
                    cookieToken.setPath(request.getContextPath() + "/");
                    response.addCookie(cookieSerie);
                    response.addCookie(cookieToken);
                }

                response.sendRedirect("catalogo");
            } else {
                request.setAttribute("errore", "Email o password errati");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp");
        }
    }
}