package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Prodotto;

@WebServlet("/catalogo")
public class CatalogoControl extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        List<Prodotto> prodotti = new ArrayList<>();
        prodotti.add(new Prodotto(1, "FIFA 24", "PC / PS5 / Xbox", 49.99, "https://gaming-cdn.com/images/products/13588/orig/ea-sports-fc-24-pc-gioco-ea-app-cover.jpg?v=1696842619"));
        prodotti.add(new Prodotto(2, "Call of Duty: Modern Warfare III", "PC / PS5 / Xbox", 59.99, "https://gaming-cdn.com/images/products/15070/orig/call-of-duty-modern-warfare-iii-bundle-cross-gen-cross-gen-bundle-xbox-one-xbox-series-x-s-gioco-microsoft-store-cover.jpg?v=1739354531"));
        prodotti.add(new Prodotto(3, "Grand Theft Auto V", "PC / PS5 / Xbox", 29.99, "https://gaming-cdn.com/images/products/186/orig/grand-theft-auto-v-pc-gioco-rockstar-cover.jpg?v=1744367958"));
        prodotti.add(new Prodotto(4, "Elden Ring", "PC / PS5 / Xbox", 39.99, "https://gaming-cdn.com/images/products/4824/orig/elden-ring-pc-steam-cover.jpg?v=1750231653"));

        request.setAttribute("listaProdotti", prodotti);

        request.getRequestDispatcher("/catalogo.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
