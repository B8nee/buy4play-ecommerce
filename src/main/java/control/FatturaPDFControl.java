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

/**
 * Servlet per la generazione della fattura in formato PDF per un ordine.
 * Utilizza la libreria iText per creare un documento PDF ben formattato con:
 * - Intestazione del venditore (Buy4Play)
 * - Dati del cliente
 * - Tabella dei prodotti acquistati (con prezzi, IVA, imponibile, totale)
 * - Riepilogo totale imponibile, IVA e totale fattura
 * - Ringraziamenti e nota legale
 * Mappata sull'URL /FatturaPDFControl.
 */
@WebServlet("/FatturaPDFControl")
public class FatturaPDFControl extends HttpServlet {

    /**
     * Gestisce le richieste GET per generare il PDF della fattura.
     * Parametro atteso: "id" (l'identificativo dell'ordine).
     * Se l'ID è valido e l'ordine esiste, viene generato un PDF scaricabile.
     * Altrimenti, reindirizza a ordini o login in base alla situazione.
     */
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
            // Recupera l'ordine e i relativi dettagli dal database
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

            // Recupera il cliente dalla sessione (deve essere loggato)
            Utente cliente = (Utente) request.getSession().getAttribute("utente");
            if (cliente == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            // Imposta il tipo di contenuto per il PDF e il nome del file scaricabile
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=fattura_ordine_" + ordineId + ".pdf");

            // Crea il documento PDF con formato A4
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            // Definizione dei font per il documento
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);

            // ---- Intestazione della fattura (due colonne) ----
            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);
            headerTable.setWidths(new float[] { 1, 1 });

            // Colonna sinistra: dati del venditore (Buy4Play)
            PdfPCell cellLeft = new PdfPCell();
            cellLeft.setBorder(0);
            cellLeft.addElement(new Paragraph("Buy4Play", titleFont));
            cellLeft.addElement(new Paragraph("Via Giovanni Paolo II, 132", normalFont));
            cellLeft.addElement(new Paragraph("84084 Fisciano (SA)", normalFont));
            cellLeft.addElement(new Paragraph("P.IVA: 12345678901", normalFont));
            cellLeft.addElement(new Paragraph("info@buy4play.it", normalFont));
            headerTable.addCell(cellLeft);

            // Colonna destra: numero fattura, data ordine
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

            // ---- Dati del cliente ----
            Paragraph clienteTitle = new Paragraph("Dati Cliente", headerFont);
            clienteTitle.setSpacingBefore(10f);
            document.add(clienteTitle);
            document.add(new Paragraph("Nome: " + cliente.getNome() + " " + cliente.getCognome(), normalFont));
            document.add(new Paragraph("Email: " + cliente.getEmail(), normalFont));
            document.add(new Paragraph(
                    "Indirizzo: " + (cliente.getIndirizzo() != null ? cliente.getIndirizzo() : "N/D"), normalFont));
            document.add(new Paragraph("Città: " + (cliente.getCitta() != null ? cliente.getCitta() : "N/D") + " ("
                    + (cliente.getProvincia() != null ? cliente.getProvincia() : "N/D") + ")", normalFont));
            document.add(new Paragraph("CAP: " + (cliente.getCap() != null ? cliente.getCap() : "N/D"), normalFont));
            document.add(new Paragraph(" "));

            // ---- Tabella dei prodotti acquistati (6 colonne) ----
            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setWidths(new float[] { 3, 1, 1, 1, 1, 1 });
            table.setHeaderRows(1);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Intestazioni della tabella
            String[] headers = { "Prodotto", "Quantità", "Prezzo unit.", "IVA %", "Imponibile", "Totale" };
            for (String h : headers) {
                PdfPCell headerCell = new PdfPCell(new Phrase(h, headerFont));
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerCell.setBackgroundColor(new com.itextpdf.text.BaseColor(46, 213, 115, 50)); // Verde neon
                                                                                                  // trasparente
                table.addCell(headerCell);
            }

            // Popola la tabella con i dettagli dell'ordine
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

            // ---- Riepilogo totale (imponibile, IVA, totale fattura) ----
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

            // ---- Ringraziamenti e note legali ----
            document.add(new Paragraph(" "));
            Paragraph thanks = new Paragraph("Grazie per aver acquistato su Buy4Play!", boldFont);
            thanks.setAlignment(Element.ALIGN_CENTER);
            document.add(thanks);
            document.add(new Paragraph("Documento emesso elettronicamente ai sensi del D.Lgs. 127/2015",
                    FontFactory.getFont(FontFactory.HELVETICA, 8)));
            document.add(new Paragraph(" ", normalFont));

            // Chiude il documento (il PDF viene inviato al client)
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}