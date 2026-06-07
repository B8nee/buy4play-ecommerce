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
	    int page = 1;
	    int limit = 12;
	    String pageParam = request.getParameter("page");
	    String limitParam = request.getParameter("limit");
	    String platform = request.getParameter("platform");
	    String sort = request.getParameter("sort");
	    
	    if (pageParam != null && !pageParam.isEmpty()) {
	        try { page = Integer.parseInt(pageParam); if (page < 1) page = 1; } catch (NumberFormatException e) {}
	    }
	    if (limitParam != null && !limitParam.isEmpty()) {
	        try { limit = Integer.parseInt(limitParam); if (limit < 1) limit = 12; } catch (NumberFormatException e) {}
	    }
	    
	    int offset = (page - 1) * limit;
	    ProdottoDAO dao = new ProdottoDAO();
	    try {
	        List<Prodotto> prodotti = dao.doRetrieveAll(offset, limit, platform, sort);
	        int totalProdotti = dao.countProdotti(platform);
	        int totalPages = (int) Math.ceil((double) totalProdotti / limit);
	        
	        request.setAttribute("listaProdotti", prodotti);
	        request.setAttribute("currentPage", page);
	        request.setAttribute("totalPages", totalPages);
	        request.setAttribute("limit", limit);
	        request.setAttribute("platform", platform);
	        request.setAttribute("sort", sort);
	    } catch (Exception e) {
	        e.printStackTrace();
	        request.setAttribute("error", "Errore nel caricamento del catalogo");
	    }
	    request.getRequestDispatcher("/catalogo.jsp").forward(request, response);
	}
}
