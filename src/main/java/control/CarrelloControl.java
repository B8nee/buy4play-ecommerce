package control;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import model.Cart;
import model.CartItem;
import model.Prodotto;
import model.ProdottoDAO;

@WebServlet("/carrello")
public class CarrelloControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Gson gson = new Gson();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        boolean isAjax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));

        if ("count".equals(action)) {
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            JsonObject json = new JsonObject();
            int count = 0;
            for (CartItem item : cart.getItems()) {
                count += item.getQuantita();
            }
            json.addProperty("count", count);
            out.print(gson.toJson(json));
            out.flush();
            return;
        }

        if (action == null && !isAjax) {
            request.getRequestDispatcher("/carrello.jsp").forward(request, response);
            return;
        }

        JsonObject jsonResponse = new JsonObject();
        try {
            if ("add".equals(action)) {
                String idParam = request.getParameter("id");
                if (idParam != null) {
                    int id = Integer.parseInt(idParam);
                    ProdottoDAO dao = new ProdottoDAO();
                    Prodotto p = dao.doRetrieveById(id);
                    if (p != null) cart.addItem(p);
                }
                jsonResponse.addProperty("success", true);
            } 
            else if ("remove".equals(action)) {
                String removeId = request.getParameter("id");
                if (removeId != null) {
                    cart.removeItem(Integer.parseInt(removeId));
                }
                jsonResponse.addProperty("success", true);
            } 
            else if ("update".equals(action)) {
                String updateId = request.getParameter("id");
                String qty = request.getParameter("qty");
                if (updateId != null && qty != null) {
                    int id = Integer.parseInt(updateId);
                    int newQty = Integer.parseInt(qty);
                    cart.updateQuantity(id, newQty);
                    int newQuantity = 0;
                    double newSubtotal = 0;
                    for (CartItem item : cart.getItems()) {
                        if (item.getProdotto().getId() == id) {
                            newQuantity = item.getQuantita();
                            newSubtotal = item.getSubtotale();
                            break;
                        }
                    }
                    jsonResponse.addProperty("success", true);
                    jsonResponse.addProperty("newQuantity", newQuantity);
                    jsonResponse.addProperty("newSubtotal", newSubtotal);
                } else {
                    jsonResponse.addProperty("success", false);
                    jsonResponse.addProperty("message", "Parametri mancanti");
                }
            } 
            else if ("clear".equals(action)) {
                cart.clear();
                jsonResponse.addProperty("success", true);
            } 
            else {
                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message", "Azione non valida");
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "Errore del server: " + e.getMessage());
        }

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(jsonResponse));
        out.flush();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}