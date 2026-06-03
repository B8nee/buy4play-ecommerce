package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Cart;
import model.Prodotto;
import model.ProdottoDAO;

@WebServlet("/carrello")
public class CarrelloControl extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        if (action == null) {
            request.getRequestDispatcher("/carrello.jsp").forward(request, response);
            return;
        }

        try {
            switch (action) {
                case "add":
                    String idParam = request.getParameter("id");
                    if (idParam != null && !idParam.isEmpty()) {
                        int id = Integer.parseInt(idParam);
                        ProdottoDAO dao = new ProdottoDAO();
                        Prodotto p = dao.doRetrieveById(id);
                        if (p != null) cart.addItem(p);
                    }
                    break;
                case "remove":
                    String removeId = request.getParameter("id");
                    if (removeId != null && !removeId.isEmpty()) {
                        cart.removeItem(Integer.parseInt(removeId));
                    }
                    break;
                case "update":
                    String updateId = request.getParameter("id");
                    String qty = request.getParameter("qty");
                    if (updateId != null && !updateId.isEmpty() && qty != null && !qty.isEmpty()) {
                        cart.updateQuantity(Integer.parseInt(updateId), Integer.parseInt(qty));
                    }
                    break;
                case "clear":
                    cart.clear();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("carrello");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}