package control;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Utente;
import model.UtenteDAO;

@WebServlet("/admin/gestioneUtenti")
public class GestioneUtentiControl extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UtenteDAO dao = new UtenteDAO();
        try {
            List<Utente> utenti = dao.getAllUtenti();
            request.setAttribute("utenti", utenti);
            request.getRequestDispatcher("/admin/gestioneUtenti.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("../error/500.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            String idParam = request.getParameter("id");
            if (idParam != null) {
                try {
                    int id = Integer.parseInt(idParam);
                    UtenteDAO dao = new UtenteDAO();
                    dao.eliminaUtente(id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            response.sendRedirect("gestioneUtenti");
        } else if ("updateRole".equals(action)) {
            String idParam = request.getParameter("id");
            String ruolo = request.getParameter("ruolo");
            if (idParam != null && ruolo != null) {
                try {
                    int id = Integer.parseInt(idParam);
                    UtenteDAO dao = new UtenteDAO();
                    dao.aggiornaRuolo(id, ruolo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            response.sendRedirect("gestioneUtenti");
        } else {
            response.sendRedirect("gestioneUtenti");
        }
    }
}