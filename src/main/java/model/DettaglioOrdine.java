package model;

/**
 * Modella la riga di dettaglio di un ordine.
 * Per ogni prodotto acquistato, vengono memorizzati:
 * - l'ID del prodotto (prodottoId),
 * - la quantità,
 * - il prezzo unitario al momento dell'acquisto (per storicità),
 * - l'aliquota IVA applicata (per storicità).
 * Inoltre, può contenere un riferimento all'oggetto Prodotto associato
 * (opzionale).
 */
public class DettaglioOrdine {
    private int id; // Identificativo univoco della riga
    private int ordineId; // ID dell'ordine a cui appartiene
    private int prodottoId; // ID del prodotto acquistato
    private int quantita; // Quantità acquistata
    private double prezzoUnitario; // Prezzo del prodotto al momento dell'acquisto (IVA esclusa)
    private double iva; // Aliquota IVA applicata (es. 22.0)
    private Prodotto prodotto; // Riferimento all'oggetto Prodotto (caricato opzionalmente dal DAO)

    // ----- Getter e Setter -----
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrdineId() {
        return ordineId;
    }

    public void setOrdineId(int ordineId) {
        this.ordineId = ordineId;
    }

    public int getProdottoId() {
        return prodottoId;
    }

    public void setProdottoId(int prodottoId) {
        this.prodottoId = prodottoId;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public double getPrezzoUnitario() {
        return prezzoUnitario;
    }

    public void setPrezzoUnitario(double prezzoUnitario) {
        this.prezzoUnitario = prezzoUnitario;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public Prodotto getProdotto() {
        return prodotto;
    }

    public void setProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
    }
}