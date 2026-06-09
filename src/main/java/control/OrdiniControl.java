package control;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.*;

/**
 * Servlet per la visualizzazione della lista degli ordini di un utente.
 * Richiede che l'utente sia autenticato; altrimenti reindirizza al login.
 * Mappata sull'URL /ordini.
 */
@WebServlet("/ordini")
public class OrdiniControl extends HttpServlet {

	/**
	 * Gestisce le richieste GET per mostrare gli ordini dell'utente loggato.
	 * Recupera gli ordini dal DAO e li passa alla JSP.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Recupera la sessione (senza crearne una nuova) e l'utente autenticato
		HttpSession session = request.getSession(false);
		Utente utente = (session != null) ? (Utente) session.getAttribute("utente") : null;

		// Se l'utente non è autenticato, reindirizza al login
		if (utente == null) {
			response.sendRedirect("login.jsp");
			return;
		}

		OrdineDAO dao = new OrdineDAO();
		try {
			// Recupera tutti gli ordini dell'utente (ordinati per data decrescente dal DAO)
			List<Ordine> ordini = dao.getOrdiniByUtente(utente.getId());
			// Imposta l'attributo per la JSP
			request.setAttribute("ordini", ordini);
			// Inoltra alla JSP di visualizzazione ordini
			request.getRequestDispatcher("/ordini.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			// In caso di errore, reindirizza al catalogo (comportamento di fallback)
			response.sendRedirect("catalogo");
		}
	}
}