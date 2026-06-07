package control;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DettaglioOrdine;
import model.OrdineDAO;

@WebServlet("/admin/DettaglioOrdineAdmin")
public class DettaglioOrdineAdmin extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendRedirect("VisualizzaOrdini");
            return;
        }
        int ordineId = Integer.parseInt(idParam);
        OrdineDAO dao = new OrdineDAO();
        try {
            List<DettaglioOrdine> dettagli = dao.getDettagliByOrdine(ordineId);
            request.setAttribute("dettagli", dettagli);
            request.setAttribute("ordineId", ordineId);
            request.getRequestDispatcher("/admin/dettaglioOrdineAdmin.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("VisualizzaOrdini");
        }
    }
}