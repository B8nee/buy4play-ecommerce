package model;

public class CarrelloItem {
    private Prodotto prodotto;
    private int quantita;

    public CarrelloItem(Prodotto prodotto, int quantita) {
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
}
