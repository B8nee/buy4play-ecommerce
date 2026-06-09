package model;

/**
 * JavaBean che rappresenta un prodotto (gioco) nel catalogo.
 * Contiene le informazioni principali: ID, nome, piattaforma, prezzo,
 * URL dell'immagine e un flag "popolare" per i prodotti in evidenza.
 */
public class Prodotto {
    private int id; // Identificativo univoco
    private String nome; // Nome del gioco
    private String piattaforma; // Piattaforma (PC, PS5, Xbox, ecc.)
    private double prezzo; // Prezzo in Euro (IVA esclusa)
    private String immagineUrl; // URL dell'immagine di copertina
    private boolean popolare; // Indica se il prodotto è tra i più venduti/in evidenza

    /**
     * Costruttore vuoto (richiesto per i JavaBean).
     */
    public Prodotto() {
    }

    /**
     * Costruttore con parametri principali (esclude il flag popolare).
     * 
     * @param id          ID del prodotto
     * @param nome        Nome del gioco
     * @param piattaforma Piattaforma
     * @param prezzo      Prezzo
     * @param immagineUrl URL dell'immagine
     */
    public Prodotto(int id, String nome, String piattaforma, double prezzo, String immagineUrl) {
        this.id = id;
        this.nome = nome;
        this.piattaforma = piattaforma;
        this.prezzo = prezzo;
        this.immagineUrl = immagineUrl;
    }

    // ---------- Getter e Setter ----------
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