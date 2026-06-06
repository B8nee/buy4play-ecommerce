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

@WebServlet("/admin/gestioneProdotti")
public class AdminProdottiControl extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null)
            action = "list";

        ProdottoDAO dao = new ProdottoDAO();
        try {
            switch (action) {
                case "list":
                    List<Prodotto> prodotti = dao.doRetrieveAll(0, 100);
                    request.setAttribute("prodotti", prodotti);
                    request.getRequestDispatcher("/admin/prodotti.jsp").forward(request, response);
                    break;
                case "addForm":
                    request.getRequestDispatcher("/admin/prodottoForm.jsp").forward(request, response);
                    break;
                case "editForm":
                    int id = Integer.parseInt(request.getParameter("id"));
                    Prodotto p = dao.doRetrieveById(id);
                    request.setAttribute("prodotto", p);
                    request.getRequestDispatcher("/admin/prodottoForm.jsp").forward(request, response);
                    break;
                case "delete":
                    id = Integer.parseInt(request.getParameter("id"));
                    boolean ok = dao.doDelete(id);
                    if (!ok)
                        request.setAttribute("error", "Prodotto non eliminabile (presente in ordini)");
                    response.sendRedirect("gestioneProdotti?action=list");
                    break;
                default:
                    response.sendRedirect("gestioneProdotti?action=list");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("gestioneProdotti?action=list");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null)
            action = "add";
        ProdottoDAO dao = new ProdottoDAO();
        try {
            Prodotto p = new Prodotto();
            p.setNome(request.getParameter("nome"));
            p.setPiattaforma(request.getParameter("piattaforma"));
            p.setPrezzo(Double.parseDouble(request.getParameter("prezzo")));
            p.setImmagineUrl(request.getParameter("immagineUrl"));
            if ("add".equals(action)) {
                dao.doSave(p);
            } else if ("update".equals(action)) {
                p.setId(Integer.parseInt(request.getParameter("id")));
                dao.doUpdate(p);
            }
            response.sendRedirect("gestioneProdotti?action=list");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("gestioneProdotti?action=addForm");
        }
    }
}