package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.JsonObject;
import model.Utente;
import model.UtenteDAO;

@WebServlet("/admin/gestioneUtenti")
public class GestioneUtentiControl extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Utente admin = (Utente) request.getSession().getAttribute("utente");
        if (admin == null || !"admin".equals(admin.getRuolo())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        UtenteDAO dao = new UtenteDAO();
        try {
            List<Utente> utenti = dao.getAllUtenti();
            request.setAttribute("utenti", utenti);
            request.getRequestDispatcher("/admin/gestioneUtenti.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Utente admin = (Utente) request.getSession().getAttribute("utente");
        if (admin == null || !"admin".equals(admin.getRuolo())) {
            response.sendError(403);
            return;
        }
        String action = request.getParameter("action");
        JsonObject json = new JsonObject();
        UtenteDAO dao = new UtenteDAO();
        try {
            if ("updateRole".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String ruolo = request.getParameter("ruolo");
                boolean ok = dao.aggiornaRuoloUtente(id, ruolo);
                json.addProperty("success", ok);
            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                boolean ok = dao.eliminaUtente(id);
                json.addProperty("success", ok);
            } else {
                json.addProperty("success", false);
                json.addProperty("message", "Azione non valida");
            }
        } catch (Exception e) {
            json.addProperty("success", false);
            json.addProperty("message", e.getMessage());
        }
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(json.toString());
        out.flush();
    }
}