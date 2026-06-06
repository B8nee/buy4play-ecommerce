package control;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.JsonObject;
import model.UtenteDAO;

@WebServlet("/CheckEmail")
public class CheckEmail extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        boolean exists = false;
        if (email != null && !email.isEmpty()) {
            UtenteDAO dao = new UtenteDAO();
            try {
                exists = dao.emailEsistente(email);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JsonObject json = new JsonObject();
        json.addProperty("exists", exists);
        out.print(json.toString());
    }
}