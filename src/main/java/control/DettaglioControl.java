package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Prodotto;
import model.ProdottoDAO;

/**
 * Servlet per visualizzare i dettagli di un singolo prodotto.
 * Riceve l'ID del prodotto come parametro, recupera i dati dal DB e li passa
 * alla JSP.
 * Mappata sull'URL /dettaglio.
 */
@WebServlet("/dettaglio")
public class DettaglioControl extends HttpServlet {

    /**
     * Gestisce le richieste GET per visualizzare i dettagli di un prodotto.
     * Parametro atteso: "id" (l'identificativo del prodotto).
     * Se l'ID non è valido o il prodotto non esiste, reindirizza al catalogo.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Legge il parametro "id" dalla request
        String idParam = request.getParameter("id");

        // Se l'ID non è presente o è vuoto, reindirizza al catalogo
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect("catalogo");
            return;
        }

        try {
            // Converte l'ID in intero
            int id = Integer.parseInt(idParam);
            ProdottoDAO dao = new ProdottoDAO();
            // Recupera il prodotto dal database tramite DAO
            Prodotto prodotto = dao.doRetrieveById(id);

            // Se il prodotto non esiste, reindirizza al catalogo
            if (prodotto == null) {
                response.sendRedirect("catalogo");
                return;
            }

            // Imposta l'attributo "prodotto" per la JSP
            request.setAttribute("prodotto", prodotto);
            // Inoltra la richiesta alla JSP di dettaglio
            request.getRequestDispatcher("/dettaglio.jsp").forward(request, response);

        } catch (Exception e) {
            // In caso di errore (es. formato ID non valido, eccezioni DB), reindirizza al
            // catalogo
            e.printStackTrace();
            response.sendRedirect("catalogo");
        }
    }
}