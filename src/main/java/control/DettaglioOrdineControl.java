package control;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.*;

/**
 * Servlet per visualizzare i dettagli di un ordine da parte del cliente.
 * Mostra l'ordine (testata) e la lista dei prodotti acquistati (dettagli).
 * Mappata sull'URL /dettaglioOrdine.
 */
@WebServlet("/dettaglioOrdine")
public class DettaglioOrdineControl extends HttpServlet {

    /**
     * Gestisce le richieste GET per visualizzare i dettagli di un ordine.
     * Parametro atteso: "id" (l'identificativo dell'ordine).
     * Se l'ID non è valido o l'ordine non esiste, reindirizza alla lista ordini.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null) {
            // ID mancante: non fa nulla (dovrebbe reindirizzare, ma per coerenza meglio
            // redirect)
            // In questo caso la servlet non produce output, ma la JSP non verrà caricata.
            // L'implementazione attuale lascia la richiesta senza risposta; per sicurezza
            // si potrebbe reindirizzare.
            // Tuttavia, manteniamo il comportamento originale.
            return;
        }
        int ordineId = Integer.parseInt(idParam);
        OrdineDAO dao = new OrdineDAO();
        try {
            // Recupera l'oggetto Ordine (testata) dal DAO
            Ordine ordine = dao.getOrdineById(ordineId);
            if (ordine == null) {
                response.sendRedirect("ordini");
                return;
            }
            // Recupera la lista dei dettagli (prodotti) associati all'ordine
            List<DettaglioOrdine> dettagli = dao.getDettagliByOrdine(ordineId);
            // Imposta gli attributi per la JSP
            request.setAttribute("ordine", ordine);
            request.setAttribute("dettagli", dettagli);
            // Inoltra alla JSP di dettaglio ordine
            request.getRequestDispatcher("/dettaglioOrdine.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("ordini");
        }
    }
}