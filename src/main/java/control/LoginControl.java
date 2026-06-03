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

@WebServlet("/login")
public class LoginControl extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

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
                response.sendRedirect("index.jsp");
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