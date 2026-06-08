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
import model.Ordine;
import model.OrdineDAO;
import model.Utente;

@WebServlet("/admin/VisualizzaOrdini")
public class VisualizzaOrdini extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Utente admin = (Utente) request.getSession().getAttribute("utente");
        if (admin == null || !"admin".equals(admin.getRuolo())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        String dataDa = request.getParameter("dataDa");
        String dataA = request.getParameter("dataA");
        String clienteEmail = request.getParameter("clienteEmail");

        OrdineDAO dao = new OrdineDAO();
        try {
            List<Ordine> ordini = dao.getOrdiniConFiltri(dataDa, dataA, clienteEmail);
            request.setAttribute("ordini", ordini);
            request.getRequestDispatcher("/admin/visualizzaOrdini.jsp").forward(request, response);
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
        OrdineDAO dao = new OrdineDAO();
        try {
            if ("updateStatus".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String stato = request.getParameter("stato");
                boolean ok = dao.aggiornaStatoOrdine(id, stato);
                json.addProperty("success", ok);
                json.addProperty("message", ok ? "Stato aggiornato" : "Errore");
            } 
            else if ("deleteOrder".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                boolean ok = dao.eliminaOrdine(id);
                json.addProperty("success", ok);
                if (!ok) json.addProperty("message", "Impossibile eliminare l'ordine");
            }
            else {
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