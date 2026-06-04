package model;

public class DettaglioOrdine {
    private int id;
    private int ordineId;
    private int prodottoId;
    private int quantita;
    private double prezzoUnitario;
    private double iva;
    private Prodotto prodotto;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getOrdineId() { return ordineId; }
    public void setOrdineId(int ordineId) { this.ordineId = ordineId; }
    public int getProdottoId() { return prodottoId; }
    public void setProdottoId(int prodottoId) { this.prodottoId = prodottoId; }
    public int getQuantita() { return quantita; }
    public void setQuantita(int quantita) { this.quantita = quantita; }
    public double getPrezzoUnitario() { return prezzoUnitario; }
    public void setPrezzoUnitario(double prezzoUnitario) { this.prezzoUnitario = prezzoUnitario; }
    public double getIva() { return iva; }
    public void setIva(double iva) { this.iva = iva; }
    public Prodotto getProdotto() { return prodotto; }
    public void setProdotto(Prodotto prodotto) { this.prodotto = prodotto; }
}
