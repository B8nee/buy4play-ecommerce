package control;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.*;

@WebServlet("/dettaglioOrdine")
public class DettaglioOrdineControl extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null) return;
        int ordineId = Integer.parseInt(idParam);
        OrdineDAO dao = new OrdineDAO();
        try {
            Ordine ordine = dao.getOrdineById(ordineId);
            if (ordine == null) {
                response.sendRedirect("ordini");
                return;
            }
            List<DettaglioOrdine> dettagli = dao.getDettagliByOrdine(ordineId);
            request.setAttribute("ordine", ordine);
            request.setAttribute("dettagli", dettagli);
            request.getRequestDispatcher("/dettaglioOrdine.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("ordini");
        }
    }
}