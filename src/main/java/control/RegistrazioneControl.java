package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Utente;
import model.UtenteDAO;
import utility.PasswordHasher;

@WebServlet("/registrazione")
public class RegistrazioneControl extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String indirizzo = request.getParameter("indirizzo");
        String citta = request.getParameter("citta");
        String provincia = request.getParameter("provincia");
        String cap = request.getParameter("cap");
        String password = request.getParameter("password");
        String conferma = request.getParameter("conferma");

        if (email == null || password == null || nome == null || cognome == null || !password.equals(conferma)) {
            request.setAttribute("errore", "Dati non validi o password non coincidenti");
            request.getRequestDispatcher("/registrazione.jsp").forward(request, response);
            return;
        }

        UtenteDAO dao = new UtenteDAO();
        try {
            if (dao.emailEsistente(email)) {
                request.setAttribute("errore", "Email già registrata");
                request.getRequestDispatcher("/registrazione.jsp").forward(request, response);
                return;
            }

            Utente u = new Utente();
            u.setEmail(email);
            u.setNome(nome);
            u.setCognome(cognome);
            u.setIndirizzo(indirizzo);
            u.setCitta(citta);
            u.setProvincia(provincia);
            u.setCap(cap);
            u.setPasswordHash(PasswordHasher.toHash(password));
            u.setRuolo("cliente");

            if (dao.registraUtente(u)) {
                request.setAttribute("messaggio", "Registrazione completata. Ora puoi fare login.");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            } else {
                request.setAttribute("errore", "Errore durante la registrazione");
                request.getRequestDispatcher("/registrazione.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errore", "Errore interno");
            request.getRequestDispatcher("/registrazione.jsp").forward(request, response);
        }
    }
}