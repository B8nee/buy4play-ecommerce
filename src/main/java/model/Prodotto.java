package model;

public class Prodotto {
    private int id;
    private String nome;
    private String piattaforma;
    private double prezzo;
    private String immagineUrl;
    private boolean popolare;

    public Prodotto() {
    }

    public Prodotto(int id, String nome, String piattaforma, double prezzo, String immagineUrl) {
        this.id = id;
        this.nome = nome;
        this.piattaforma = piattaforma;
        this.prezzo = prezzo;
        this.immagineUrl = immagineUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPiattaforma() {
        return piattaforma;
    }

    public void setPiattaforma(String piattaforma) {
        this.piattaforma = piattaforma;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public String getImmagineUrl() {
        return immagineUrl;
    }

    public void setImmagineUrl(String immagineUrl) {
        this.immagineUrl = immagineUrl;
    }
    
    public boolean isPopolare() {
        return popolare;
    }

    public void setPopolare(boolean popolare) {
        this.popolare = popolare;
    }
}
