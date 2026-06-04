package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Utente;
import model.UtenteDAO;

@WebServlet("/profilo")
public class ProfiloControl extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Utente utente = (session != null) ? (Utente) session.getAttribute("utente") : null;
        if (utente == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        request.setAttribute("utente", utente);
        request.getRequestDispatcher("/profilo.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Utente utenteLoggato = (session != null) ? (Utente) session.getAttribute("utente") : null;
        if (utenteLoggato == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String indirizzo = request.getParameter("indirizzo");
        String citta = request.getParameter("citta");
        String provincia = request.getParameter("provincia");
        String cap = request.getParameter("cap");

        utenteLoggato.setNome(nome);
        utenteLoggato.setCognome(cognome);
        utenteLoggato.setIndirizzo(indirizzo);
        utenteLoggato.setCitta(citta);
        utenteLoggato.setProvincia(provincia);
        utenteLoggato.setCap(cap);

        UtenteDAO dao = new UtenteDAO();
        try {
            dao.aggiornaUtente(utenteLoggato);
            session.setAttribute("utente", utenteLoggato);
            request.setAttribute("messaggio", "Profilo aggiornato con successo");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errore", "Errore durante l'aggiornamento");
        }
        request.setAttribute("utente", utenteLoggato);
        request.getRequestDispatcher("/profilo.jsp").forward(request, response);
    }
}