package control;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Ordine;
import model.OrdineDAO;

@WebServlet("/admin/VisualizzaOrdini")
public class VisualizzaOrdini extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String dataDa = request.getParameter("dataDa");
        String dataA = request.getParameter("dataA");
        String clienteEmail = request.getParameter("clienteEmail");

        OrdineDAO dao = new OrdineDAO();
        try {
            List<Ordine> ordini = dao.getAllOrdiniConFiltri(dataDa, dataA, clienteEmail);
            request.setAttribute("ordini", ordini);
            request.getRequestDispatcher("/admin/visualizzaOrdini.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "Errore nel recupero degli ordini");
        }
    }
}