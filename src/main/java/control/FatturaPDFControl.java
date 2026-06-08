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
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import model.DettaglioOrdine;
import model.Ordine;
import model.OrdineDAO;
import model.Utente;

@WebServlet("/FatturaPDFControl")
public class FatturaPDFControl extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect("ordini");
            return;
        }
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

            Utente cliente = (Utente) request.getSession().getAttribute("utente");
            if (cliente == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=fattura_ordine_" + ordineId + ".pdf");

            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);

            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);
            headerTable.setWidths(new float[]{1, 1});

            PdfPCell cellLeft = new PdfPCell();
            cellLeft.setBorder(0);
            cellLeft.addElement(new Paragraph("Buy4Play", titleFont));
            cellLeft.addElement(new Paragraph("Via Giovanni Paolo II, 132", normalFont));
            cellLeft.addElement(new Paragraph("84084 Fisciano (SA)", normalFont));
            cellLeft.addElement(new Paragraph("P.IVA: 12345678901", normalFont));
            cellLeft.addElement(new Paragraph("info@buy4play.it", normalFont));
            headerTable.addCell(cellLeft);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            PdfPCell cellRight = new PdfPCell();
            cellRight.setBorder(0);
            cellRight.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellRight.addElement(new Paragraph("FATTURA N. " + ordineId, headerFont));
            cellRight.addElement(new Paragraph("Data: " + sdf.format(ordine.getDataOrdine()), normalFont));
            cellRight.addElement(new Paragraph("Ordine N. " + ordineId, normalFont));
            headerTable.addCell(cellRight);
            document.add(headerTable);

            document.add(new Paragraph(" "));

            Paragraph clienteTitle = new Paragraph("Dati Cliente", headerFont);
            clienteTitle.setSpacingBefore(10f);
            document.add(clienteTitle);
            document.add(new Paragraph("Nome: " + cliente.getNome() + " " + cliente.getCognome(), normalFont));
            document.add(new Paragraph("Email: " + cliente.getEmail(), normalFont));
            document.add(new Paragraph("Indirizzo: " + (cliente.getIndirizzo() != null ? cliente.getIndirizzo() : "N/D"), normalFont));
            document.add(new Paragraph("Città: " + (cliente.getCitta() != null ? cliente.getCitta() : "N/D") + " (" + (cliente.getProvincia() != null ? cliente.getProvincia() : "N/D") + ")", normalFont));
            document.add(new Paragraph("CAP: " + (cliente.getCap() != null ? cliente.getCap() : "N/D"), normalFont));

            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{3, 1, 1, 1, 1, 1});
            table.setHeaderRows(1);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            String[] headers = {"Prodotto", "Quantità", "Prezzo unit.", "IVA %", "Imponibile", "Totale"};
            for (String h : headers) {
                PdfPCell headerCell = new PdfPCell(new Phrase(h, headerFont));
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerCell.setBackgroundColor(new com.itextpdf.text.BaseColor(46, 213, 115, 50));
                table.addCell(headerCell);
            }

            double totaleImponibile = 0.0;
            double totaleIva = 0.0;
            for (DettaglioOrdine det : dettagli) {
                double imponibile = det.getPrezzoUnitario() * det.getQuantita();
                double iva = imponibile * det.getIva() / 100;
                double totaleRiga = imponibile + iva;
                totaleImponibile += imponibile;
                totaleIva += iva;

                table.addCell(det.getProdotto().getNome());
                table.addCell(String.valueOf(det.getQuantita()));
                table.addCell(String.format("%.2f €", det.getPrezzoUnitario()));
                table.addCell(String.format("%.0f%%", det.getIva()));
                table.addCell(String.format("%.2f €", imponibile));
                table.addCell(String.format("%.2f €", totaleRiga));
            }
            document.add(table);

            PdfPTable totalTable = new PdfPTable(2);
            totalTable.setWidthPercentage(50);
            totalTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalTable.getDefaultCell().setBorder(0);
            totalTable.addCell(new Phrase("Totale imponibile: ", normalFont));
            totalTable.addCell(new Phrase(String.format("%.2f €", totaleImponibile), normalFont));
            totalTable.addCell(new Phrase("Totale IVA: ", normalFont));
            totalTable.addCell(new Phrase(String.format("%.2f €", totaleIva), normalFont));
            totalTable.addCell(new Phrase("TOTALE FATTURA: ", boldFont));
            totalTable.addCell(new Phrase(String.format("%.2f €", totaleImponibile + totaleIva), boldFont));

            document.add(totalTable);

            document.add(new Paragraph(" "));
            Paragraph thanks = new Paragraph("Grazie per aver acquistato su Buy4Play!", boldFont);
            thanks.setAlignment(Element.ALIGN_CENTER);
            document.add(thanks);
            document.add(new Paragraph("Documento emesso elettronicamente ai sensi del D.Lgs. 127/2015", FontFactory.getFont(FontFactory.HELVETICA, 8)));
            document.add(new Paragraph(" ", normalFont));

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}