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

@WebServlet("/logout")
public class LogoutControl extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        String serie = null;
        if (request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                if ("remember_me_serie".equals(c.getName())) {
                    serie = c.getValue();
                    c.setMaxAge(0);
                    c.setPath(request.getContextPath() + "/");
                    response.addCookie(c);
                }
                if ("remember_me_token".equals(c.getName())) {
                    c.setMaxAge(0);
                    c.setPath(request.getContextPath() + "/");
                    response.addCookie(c);
                }
            }
        }
        if (serie != null) {
            try {
                new RememberMeDAO().eliminaToken(serie);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        response.sendRedirect("index.jsp");
    }
}