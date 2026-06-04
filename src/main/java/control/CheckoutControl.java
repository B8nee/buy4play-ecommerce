package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.*;

@WebServlet("/checkout")
public class CheckoutControl extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Utente utente = (session != null) ? (Utente) session.getAttribute("utente") : null;
        if (utente == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            response.sendRedirect("carrello");
            return;
        }

        Ordine ordine = new Ordine();
        ordine.setUtenteId(utente.getId());
        ordine.setTotale(cart.getTotal());
        ordine.setIndirizzoSpedizione(utente.getIndirizzo() + ", " + utente.getCitta() + " " + utente.getCap());

        OrdineDAO odao = new OrdineDAO();
        try {
            int ordineId = odao.salvaOrdine(ordine);
            for (CartItem item : cart.getItems()) {
                DettaglioOrdine det = new DettaglioOrdine();
                det.setOrdineId(ordineId);
                det.setProdottoId(item.getProdotto().getId());
                det.setQuantita(item.getQuantita());
                det.setPrezzoUnitario(item.getProdotto().getPrezzo());
                det.setIva(22.0); // IVA fissa 22% (se vuoi dinamicamente da DB, aggiungi campo IVA nel prodotto)
                odao.salvaDettaglio(det);
            }
            cart.clear();
            session.setAttribute("cart", cart);
            response.sendRedirect("ordini");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errore", "Errore durante il checkout");
            request.getRequestDispatcher("/carrello.jsp").forward(request, response);
        }
    }
}
