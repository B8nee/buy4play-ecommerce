package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.*;

/**
 * Servlet per la finalizzazione dell'ordine (checkout).
 * Verifica che l'utente sia autenticato e che il carrello non sia vuoto,
 * poi salva l'ordine e i relativi dettagli nel database.
 * Al termine, svuota il carrello e reindirizza alla pagina degli ordini.
 * Mappata sull'URL /checkout.
 */
@WebServlet("/checkout")
public class CheckoutControl extends HttpServlet {

    /**
     * Gestisce la richiesta GET per il checkout.
     * Processa l'ordine, lo salva nel DB e redireziona.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Recupera la sessione senza crearne una nuova
        HttpSession session = request.getSession(false);
        // Ottiene l'utente loggato dalla sessione
        Utente utente = (session != null) ? (Utente) session.getAttribute("utente") : null;
        if (utente == null) {
            // Utente non autenticato: rimanda al login
            response.sendRedirect("login.jsp");
            return;
        }

        // Recupera il carrello dalla sessione
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            // Carrello vuoto: torna al carrello
            response.sendRedirect("carrello");
            return;
        }

        // Crea l'oggetto Ordine (testata dell'ordine)
        Ordine ordine = new Ordine();
        ordine.setUtenteId(utente.getId());
        ordine.setTotale(cart.getTotal()); // Totale calcolato dal carrello
        // Compone l'indirizzo di spedizione dai dati del profilo utente
        ordine.setIndirizzoSpedizione(utente.getIndirizzo() + ", " + utente.getCitta() + " " + utente.getCap());

        OrdineDAO odao = new OrdineDAO();
        try {
            // Salva la testata dell'ordine e ottiene l'ID generato
            int ordineId = odao.salvaOrdine(ordine);
            // Per ogni prodotto nel carrello, crea un dettaglio ordine
            for (CartItem item : cart.getItems()) {
                DettaglioOrdine det = new DettaglioOrdine();
                det.setOrdineId(ordineId);
                det.setProdottoId(item.getProdotto().getId());
                det.setQuantita(item.getQuantita());
                det.setPrezzoUnitario(item.getProdotto().getPrezzo()); // Prezzo al momento dell'acquisto
                det.setIva(22.0); // IVA fissa al 22% (in futuro potrebbe essere letta dal DB per ogni prodotto)
                odao.salvaDettaglio(det);
            }
            // Svuota il carrello dalla sessione
            cart.clear();
            session.setAttribute("cart", cart);
            // Reindirizza alla pagina che mostra gli ordini dell'utente
            response.sendRedirect("ordini");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errore", "Errore durante il checkout");
            request.getRequestDispatcher("/carrello.jsp").forward(request, response);
        }
    }
}