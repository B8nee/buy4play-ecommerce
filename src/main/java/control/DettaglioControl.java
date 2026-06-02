package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Prodotto;
import model.ProdottoDAO;

@WebServlet("/dettaglio")
public class DettaglioControl extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect("catalogo");
            return;
        }
        try {
            int id = Integer.parseInt(idParam);
            ProdottoDAO dao = new ProdottoDAO();
            Prodotto prodotto = dao.doRetrieveById(id);
            if (prodotto == null) {
                response.sendRedirect("catalogo");
                return;
            }
            request.setAttribute("prodotto", prodotto);
            request.getRequestDispatcher("/dettaglio.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("catalogo");
        }
    }
}