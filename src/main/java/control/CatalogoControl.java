package control;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Prodotto;
import model.ProdottoDAO;

@WebServlet("/catalogo")
public class CatalogoControl extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ProdottoDAO dao = new ProdottoDAO();
            List<Prodotto> prodotti = dao.doRetrieveAll();
            request.setAttribute("listaProdotti", prodotti);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("listaProdotti", new java.util.ArrayList<>());
        }
        request.getRequestDispatcher("/catalogo.jsp").forward(request, response);
    }
}
