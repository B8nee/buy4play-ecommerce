package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import model.Prodotto;
import model.ProdottoDAO;
import model.Utente;

@WebServlet("/admin/GestioneProdotti")
public class AdminProdottiControl extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Utente admin = (Utente) request.getSession().getAttribute("utente");
        if (admin == null || !"admin".equals(admin.getRuolo())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String action = request.getParameter("action");
        ProdottoDAO dao = new ProdottoDAO();

        try {
            if ("get".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                Prodotto p = dao.doRetrieveById(id);
                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                Gson gson = new Gson();
                out.print(gson.toJson(p));
                out.flush();
                return;
            } 
            else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                boolean deleted = dao.doDelete(id);
                if (deleted) {
                    request.getSession().setAttribute("messaggio", "Prodotto eliminato con successo");
                } else {
                    request.getSession().setAttribute("errore", "Impossibile eliminare: prodotto presente in ordini esistenti");
                }
                response.sendRedirect("GestioneProdotti");
                return;
            }
            int page = 1;
            int limit = 10;
            String pageParam = request.getParameter("page");
            if (pageParam != null) {
                page = Integer.parseInt(pageParam);
                if (page < 1) page = 1;
            }
            int offset = (page - 1) * limit;
            List<Prodotto> prodotti = dao.doRetrieveAll(offset, limit);
            int totalProdotti = dao.countProdotti();
            int totalPages = (int) Math.ceil((double) totalProdotti / limit);
            request.setAttribute("prodotti", prodotti);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.getRequestDispatcher("/admin/gestioneprodotti.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Utente admin = (Utente) request.getSession().getAttribute("utente");
        if (admin == null || !"admin".equals(admin.getRuolo())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String action = request.getParameter("action");
        ProdottoDAO dao = new ProdottoDAO();
        try {
            Prodotto p = new Prodotto();
            p.setNome(request.getParameter("nome"));
            p.setPiattaforma(request.getParameter("piattaforma"));
            p.setPrezzo(Double.parseDouble(request.getParameter("prezzo")));
            p.setImmagineUrl(request.getParameter("immagineUrl"));
            p.setPopolare(request.getParameter("popolare") != null);

            if ("add".equals(action)) {
                dao.doSave(p);
                request.getSession().setAttribute("messaggio", "Prodotto aggiunto con successo");
            } else if ("update".equals(action)) {
                p.setId(Integer.parseInt(request.getParameter("id")));
                dao.doUpdate(p);
                request.getSession().setAttribute("messaggio", "Prodotto aggiornato con successo");
            }
            response.sendRedirect("GestioneProdotti");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errore", "Errore durante il salvataggio");
            response.sendRedirect("GestioneProdotti");
        }
    }
}