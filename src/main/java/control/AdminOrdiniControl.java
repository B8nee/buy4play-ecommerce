package control;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Ordine;
import model.OrdineDAO;

@WebServlet("/admin/visualizzaOrdini")
public class AdminOrdiniControl extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String dataInizio = request.getParameter("dataInizio");
        String dataFine = request.getParameter("dataFine");
        String clienteId = request.getParameter("clienteId");
        OrdineDAO dao = new OrdineDAO();
        try {
            List<Ordine> ordini = dao.getOrdiniConFiltri(dataInizio, dataFine, clienteId);
            request.setAttribute("ordini", ordini);
            request.getRequestDispatcher("/admin/ordini.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("index.jsp");
        }
    }
}