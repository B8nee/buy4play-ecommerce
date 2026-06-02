package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Carrello;
import model.Prodotto;
import model.ProdottoDAO;

@WebServlet("/carrello")
public class CarrelloControl extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        Carrello carrello = (Carrello) session.getAttribute("carrello");
        if (carrello == null) {
            carrello = new Carrello();
            session.setAttribute("carrello", carrello);
        }

        if (action == null) {
            request.getRequestDispatcher("/carrello.jsp").forward(request, response);
            return;
        }

        switch (action) {
            case "add":
                String idProdotto = request.getParameter("id");
                if (idProdotto != null) {
                    try {
                        int id = Integer.parseInt(idProdotto);
                        ProdottoDAO dao = new ProdottoDAO();
                        Prodotto p = dao.doRetrieveById(id);
                        if (p != null) {
                            carrello.addProdotto(p);
                        }
                    } catch (Exception e) { e.printStackTrace(); }
                }
                String referer = request.getHeader("referer");
                response.sendRedirect(referer != null ? referer : "catalogo");
                break;

            case "remove":
                String removeId = request.getParameter("id");
                if (removeId != null) {
                    carrello.removeProdotto(Integer.parseInt(removeId));
                }
                response.sendRedirect("carrello");
                break;

            case "update":
                String idUpdate = request.getParameter("id");
                String qty = request.getParameter("quantita");
                if (idUpdate != null && qty != null) {
                    carrello.updateQuantita(Integer.parseInt(idUpdate), Integer.parseInt(qty));
                }
                response.sendRedirect("carrello");
                break;

            case "clear":
                carrello.clear();
                response.sendRedirect("carrello");
                break;

            default:
                response.sendRedirect("catalogo");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
