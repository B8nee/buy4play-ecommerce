package model;

/**
 * JavaBean che rappresenta un utente del sistema.
 * Contiene i dati anagrafici, di contatto, di autenticazione (hash password)
 * e il ruolo (cliente o admin).
 */
public class Utente {
    private int id; // Identificativo univoco
    private String email; // Email (usata come username per il login)
    private String nome; // Nome dell'utente
    private String cognome; // Cognome
    private String indirizzo; // Indirizzo di spedizione (opzionale)
    private String citta; // Città
    private String provincia; // Provincia (2 lettere)
    private String cap; // CAP (5 cifre)
    private String passwordHash; // Hash SHA-512 della password
    private String ruolo; // Ruolo: "cliente" o "admin"

    // Costruttore vuoto (richiesto per i JavaBean)
    public Utente() {
    }

    // ---------- Getter e Setter ----------
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }
}