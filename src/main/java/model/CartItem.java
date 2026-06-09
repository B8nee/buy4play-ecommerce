package model;

/**
 * Rappresenta un singolo articolo all'interno del carrello.
 * Contiene il riferimento al prodotto e la quantità desiderata.
 */
public class CartItem {
    private Prodotto prodotto; // Il prodotto associato a questo articolo
    private int quantita; // La quantità richiesta

    /**
     * Costruttore per creare un articolo del carrello.
     * 
     * @param prodotto il prodotto da aggiungere
     * @param quantita la quantità iniziale
     */
    public CartItem(Prodotto prodotto, int quantita) {
        this.prodotto = prodotto;
        this.quantita = quantita;
    }

    public Prodotto getProdotto() {
        return prodotto;
    }

    public void setProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    /**
     * Calcola il subtotale per questo articolo (prezzo del prodotto * quantità).
     * 
     * @return subtotale in Euro
     */
    public double getSubtotale() {
        return prodotto.getPrezzo() * quantita;
    }
}