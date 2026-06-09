package model;

import java.sql.Timestamp;
import java.util.List;

/**
 * Rappresenta un ordine effettuato da un utente.
 * Contiene i dati di testata dell'ordine: identificativo, utente, data,
 * totale, indirizzo di spedizione, stato e lista dei dettagli (prodotti
 * acquistati).
 * Viene utilizzato sia dal cliente che dall'amministratore.
 */
public class Ordine {
    private int id; // Identificativo univoco dell'ordine
    private int utenteId; // Riferimento all'utente che ha effettuato l'ordine
    private Timestamp dataOrdine; // Data e ora di creazione dell'ordine
    private double totale; // Totale dell'ordine (IVA inclusa)
    private String indirizzoSpedizione; // Indirizzo di spedizione al momento dell'ordine
    private List<DettaglioOrdine> dettagli; // Lista dei prodotti acquistati (dettaglio ordine)
    private Utente utente; // Oggetto Utente associato (comodo per visualizzazione)
    private String stato; // Stato dell'ordine: in_lavorazione, spedito, consegnato

    // Getter e Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUtenteId() {
        return utenteId;
    }

    public void setUtenteId(int utenteId) {
        this.utenteId = utenteId;
    }

    public Timestamp getDataOrdine() {
        return dataOrdine;
    }

    public void setDataOrdine(Timestamp dataOrdine) {
        this.dataOrdine = dataOrdine;
    }

    public double getTotale() {
        return totale;
    }

    public void setTotale(double totale) {
        this.totale = totale;
    }

    public String getIndirizzoSpedizione() {
        return indirizzoSpedizione;
    }

    public void setIndirizzoSpedizione(String indirizzoSpedizione) {
        this.indirizzoSpedizione = indirizzoSpedizione;
    }

    public List<DettaglioOrdine> getDettagli() {
        return dettagli;
    }

    public void setDettagli(List<DettaglioOrdine> dettagli) {
        this.dettagli = dettagli;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }
}