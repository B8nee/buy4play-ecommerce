package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import model.Prodotto;
import model.ProdottoDAO;

@WebServlet("/SearchProduct")
public class SearchProduct extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String query = request.getParameter("q");
        if (query == null || query.trim().isEmpty()) {
            response.setContentType("application/json");
            response.getWriter().write("[]");
            return;
        }
        ProdottoDAO dao = new ProdottoDAO();
        try {
            List<Prodotto> results = dao.searchProducts(query);
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            Gson gson = new Gson();
            out.print(gson.toJson(results));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
        }
    }
}