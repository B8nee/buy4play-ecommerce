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
import utility.PasswordHasher;

@WebServlet("/cambioPassword")
public class CambioPasswordControl extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Utente utente = (session != null) ? (Utente) session.getAttribute("utente") : null;
        if (utente == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String oldPass = request.getParameter("oldPassword");
        String newPass = request.getParameter("newPassword");
        String confirm = request.getParameter("confirmNewPassword");

        if (!newPass.equals(confirm)) {
            request.setAttribute("errore", "Le nuove password non coincidono");
            request.getRequestDispatcher("/cambioPassword.jsp").forward(request, response);
            return;
        }

        String oldHash = PasswordHasher.toHash(oldPass);
        UtenteDAO dao = new UtenteDAO();
        try {
            Utente verificato = dao.login(utente.getEmail(), oldHash);
            if (verificato == null) {
                request.setAttribute("errore", "Password attuale errata");
                request.getRequestDispatcher("/cambioPassword.jsp").forward(request, response);
                return;
            }
            String newHash = PasswordHasher.toHash(newPass);
            if (dao.aggiornaPassword(utente.getId(), newHash)) {
                request.setAttribute("messaggio", "Password cambiata con successo");
                request.getRequestDispatcher("/cambioPassword.jsp").forward(request, response);
            } else {
                request.setAttribute("errore", "Errore durante l'aggiornamento");
                request.getRequestDispatcher("/cambioPassword.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}