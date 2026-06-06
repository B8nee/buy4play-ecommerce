package control;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import model.*;

@WebServlet("/FatturaPDFControl")
public class FatturaPDFControl extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null)
            return;
        int ordineId = Integer.parseInt(idParam);
        OrdineDAO dao = new OrdineDAO();
        try {
            Ordine ordine = dao.getOrdineById(ordineId);
            if (ordine == null) {
                response.sendRedirect("ordini");
                return;
            }
            List<DettaglioOrdine> dettagli = dao.getDettagliByOrdine(ordineId);
            if (dettagli.isEmpty()) {
                response.sendRedirect("ordini");
                return;
            }

            Utente utente = (Utente) request.getSession().getAttribute("utente");
            if (utente == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=fattura_ordine_" + ordineId + ".pdf");

            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Fattura d'acquisto - Buy4Play", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            document.add(new Paragraph("Cliente: " + utente.getNome() + " " + utente.getCognome()));
            document.add(new Paragraph("Email: " + utente.getEmail()));
            document.add(
                    new Paragraph("Indirizzo: " + (utente.getIndirizzo() != null ? utente.getIndirizzo() : "N/D")));
            document.add(new Paragraph("Città: " + (utente.getCitta() != null ? utente.getCitta() : "N/D") + " ("
                    + (utente.getProvincia() != null ? utente.getProvincia() : "N/D") + ")"));
            document.add(new Paragraph("CAP: " + (utente.getCap() != null ? utente.getCap() : "N/D")));
            document.add(new Paragraph("Data ordine: " + sdf.format(ordine.getDataOrdine())));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            String[] headers = { "Prodotto", "Quantità", "Prezzo unitario", "IVA", "Totale" };
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            double totaleComplessivo = 0.0;
            for (DettaglioOrdine d : dettagli) {
                double prezzoConIva = d.getPrezzoUnitario() * (1 + d.getIva() / 100);
                double totaleRiga = prezzoConIva * d.getQuantita();
                totaleComplessivo += totaleRiga;

                table.addCell(d.getProdotto().getNome());
                table.addCell(String.valueOf(d.getQuantita()));
                table.addCell(String.format("%.2f €", d.getPrezzoUnitario()));
                table.addCell(String.format("%.0f%%", d.getIva()));
                table.addCell(String.format("%.2f €", totaleRiga));
            }

            document.add(table);
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Totale complessivo: " + String.format("%.2f €", totaleComplessivo),
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD)));

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}