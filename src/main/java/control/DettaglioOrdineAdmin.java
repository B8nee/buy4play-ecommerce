package control;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DettaglioOrdine;
import model.OrdineDAO;

/**
 * Servlet per la visualizzazione dei dettagli di un ordine nell'area
 * amministrativa.
 * Riceve l'ID dell'ordine e recupera la lista dei prodotti acquistati
 * (dettagli).
 * Mappata sull'URL /admin/DettaglioOrdineAdmin.
 */
@WebServlet("/admin/DettaglioOrdineAdmin")
public class DettaglioOrdineAdmin extends HttpServlet {

    /**
     * Gestisce le richieste GET per visualizzare i dettagli di un ordine.
     * Parametro atteso: "id" (ID dell'ordine).
     * Se l'ID non è fornito, reindirizza alla lista degli ordini
     * (VisualizzaOrdini).
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Legge il parametro "id" dalla request
        String idParam = request.getParameter("id");
        if (idParam == null) {
            // Se manca l'ID, torna alla pagina di gestione ordini
            response.sendRedirect("VisualizzaOrdini");
            return;
        }

        // Converte l'ID in intero
        int ordineId = Integer.parseInt(idParam);
        OrdineDAO dao = new OrdineDAO();

        try {
            // Recupera i dettagli dell'ordine (prodotti, quantità, prezzi, IVA)
            List<DettaglioOrdine> dettagli = dao.getDettagliByOrdine(ordineId);
            // Imposta gli attributi per la JSP
            request.setAttribute("dettagli", dettagli);
            request.setAttribute("ordineId", ordineId);
            // Inoltra alla JSP di dettaglio (area admin)
            request.getRequestDispatcher("/admin/dettaglioOrdineAdmin.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            // In caso di errore, torna alla pagina di gestione ordini
            response.sendRedirect("VisualizzaOrdini");
        }
    }
}