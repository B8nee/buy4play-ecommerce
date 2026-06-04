package control;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.*;

@WebServlet("/ordini")
public class OrdiniControl extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Utente utente = (session != null) ? (Utente) session.getAttribute("utente") : null;
        if (utente == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        OrdineDAO dao = new OrdineDAO();
        try {
            List<Ordine> ordini = dao.getOrdiniByUtente(utente.getId());
            request.setAttribute("ordini", ordini);
            request.getRequestDispatcher("/ordini.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("index.jsp");
        }
    }
}
