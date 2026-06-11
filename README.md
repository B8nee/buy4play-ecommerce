# 🎮 Buy4Play – E-commerce di chiavi videogiochi

Progetto realizzato per il corso di **Tecnologie Software per il Web** (TSW)  
Università degli Studi di Salerno – Corso di Laurea in Informatica  
**Anno Accademico 2024/2025** – Docente: Prof.ssa Rita Francese

---

## 📌 Descrizione del progetto

Buy4Play è un e-commerce completo per l’acquisto di **chiavi digitali di videogiochi** per PC, PlayStation e Xbox.  
Il sito permette agli utenti di:

- Sfogliare il catalogo con filtri e paginazione
- Aggiungere/rimuovere prodotti dal carrello (gestito in sessione)
- Completare l’acquisto (checkout) con salvataggio dell’ordine nel database
- Visualizzare la lista degli ordini, i dettagli e scaricare la fattura in PDF
- Gestire il proprio profilo e cambiare password
- Registrarsi e accedere con login persistente (“Ricordami”)

L’**amministratore** ha un’area riservata per:
- Gestire il catalogo (CRUD prodotti)
- Visualizzare e filtrare tutti gli ordini (per data, cliente, stato)
- Modificare lo stato degli ordini (in lavorazione, spedito, consegnato)
- Eliminare ordini e gestire gli utenti (cambio ruolo, eliminazione)

---

## 🚀 Tecnologie utilizzate

| Area                | Tecnologie / Pattern                                                                 |
|---------------------|--------------------------------------------------------------------------------------|
| **Architettura**    | MVC (Model-View-Controller)                                                          |
| **Back‑end**        | Java 11, Servlet, JSP, JDBC                                                          |
| **Database**        | MySQL 8, DataSource (JNDI, Connection Pool)                                          |
| **Server**          | Apache Tomcat 9                                                                      |
| **Front‑end**       | HTML5, CSS3 (responsive), JavaScript, AJAX (fetch API)                               |
| **Librerie**        | Gson (JSON), iText (PDF), SHA‑512 (hashing password)                                 |
| **Sicurezza**       | Filtri di autenticazione, hash password, login persistente (cookie + DB)             |
| **Versionamento**   | Git / GitHub                                                                         |

---

## 📁 Struttura del progetto

Buy4Play/
├── src/main/java/
│ ├── control/ # Servlet (CatalogoControl, CarrelloControl, LoginControl, ...)
│ ├── model/ # JavaBean (Prodotto, Utente, Ordine, Cart, ...) e DAO
│ ├── filtri/ # AuthFilter, AdminFilter, RememberMeFilter
│ └── utility/ # PasswordHasher, TokenGenerator
├── WebContent/
│ ├── admin/ # JSP dell’area amministrativa
│ ├── error/ # Pagine di errore personalizzate (404, 500, generica)
│ ├── META-INF/ # context.xml (configurazione DataSource)
│ ├── WEB-INF/ # web.xml e librerie (.jar)
│ ├── header.jsp # Header comune (include navbar, ricerca, badge carrello)
│ ├── footer.jsp # Footer con social, newsletter simulata
│ ├── index.jsp # Home page
│ ├── catalogo.jsp # Catalogo prodotti con filtri e paginazione
│ ├── carrello.jsp # Riepilogo carrello (AJAX per modifiche)
│ ├── dettaglio.jsp # Dettaglio prodotto
│ ├── ordini.jsp # Lista ordini utente (con filtro mese/anno)
│ ├── dettaglioOrdine.jsp # Dettaglio ordine e link per scaricare PDF
│ ├── profilo.jsp # Modifica profilo utente
│ ├── login.jsp # Form di accesso
│ ├── registrazione.jsp # Registrazione con controllo email AJAX
│ ├── cambioPassword.jsp
│ ├── contattaci.jsp # Modulo di contatto (simulazione)
│ ├── faq.jsp # Domande frequenti
│ ├── privacy.jsp # Informativa privacy
│ └── termini.jsp # Termini e condizioni
└── README.md

---

## 🗄️ Schema del database

Il database si chiama `buy4play` e contiene le seguenti tabelle:

| Tabella            | Descrizione                                           |
|--------------------|-------------------------------------------------------|
| `utente`           | Dati utenti (email, nome, cognome, indirizzo, ruolo)  |
| `prodotto`         | Catalogo giochi (nome, piattaforma, prezzo, immagine) |
| `ordine`           | Testata ordine (utente, data, totale, stato)          |
| `dettaglio_ordine` | Righe ordine (prodotto, quantità, prezzo storico, IVA)|
| `remember_me`      | Token per login persistente (serie, token, scadenza)  |

> Il campo `stato` di `ordine` può assumere i valori `in_lavorazione`, `spedito`, `consegnato`.

**Vincoli di integrità referenziale:**
- `ordine.utente_id` → `utente.id` (ON DELETE CASCADE)
- `dettaglio_ordine.ordine_id` → `ordine.id` (ON DELETE CASCADE)
- `dettaglio_ordine.prodotto_id` → `prodotto.id` (ON DELETE RESTRICT – per preservare gli ordini storici)
- `remember_me.utente_id` → `utente.id` (ON DELETE CASCADE)

---

## ⚙️ Configurazione e installazione

### Prerequisiti
- Java 11 o superiore
- Apache Tomcat 9
- MySQL 8
- Eclipse IDE for Enterprise Java (o altro IDE compatibile)

### Passi per l’installazione

1. **Clona il repository**
   ```bash
   git clone https://github.com/tuo-username/buy4play-ecommerce.git
   ```

2. **Crea il database MySQL**
   Esegui lo script SQL fornito (se presente) per creare il database e le tabelle.
   ```sql
   CREATE DATABASE buy4play;
   USE buy4play;
   -- esegui le istruzioni CREATE TABLE, INSERT, ecc.
   ```

3. **Configura il DataSource**
   Modifica il file `WebContent/META-INF/context.xml` impostando le tue credenziali MySQL:
   ```xml
   <Resource name="jdbc/buy4play"
             auth="Container"
             type="javax.sql.DataSource"
             driverClassName="com.mysql.cj.jdbc.Driver"
             url="jdbc:mysql://localhost:3306/buy4play?useSSL=false&amp;serverTimezone=Europe/Rome"
             username="root"
             password="tua_password"
             maxTotal="20"
             maxIdle="10" />
   ```

4. **Aggiungi le librerie**
   Copia i seguenti .jar nella cartella `WebContent/WEB-INF/lib`:
   - `mysql-connector-java-8.0.33.jar`
   - `gson-2.10.1.jar`
   - `itextpdf-5.5.13.3.jar`

5. **Importa il progetto in Eclipse**
   - File → Import → Existing Projects into Workspace → seleziona la cartella del progetto.
   - Assicurati che il target runtime sia Apache Tomcat 9.

6. **Avvia il server**
   - Aggiungi il progetto a Tomcat (Run on Server).
   - L’applicazione sarà disponibile all’indirizzo: `http://localhost:8080/Buy4Play`

### 🔐 Credenziali di accesso

| Ruolo   | Email               | Password  |
|---------|---------------------|-----------|
| Admin   | admin@buy4play.it   | admin123  |
| Cliente | (tramite registrazione) | - |

> La password `admin123` viene automaticamente hashata con SHA‑512. Se si ripristina il database, assicurarsi di inserire l’hash corrispondente (generato dalla classe `PasswordHasher`).

---

## ✨ Funzionalità principali

### 👥 Per tutti i visitatori (guest)
- Catalogo con filtro per piattaforma, ordinamento per prezzo e paginazione
- Barra di ricerca con suggerimenti AJAX (Google Suggest style)
- Dettaglio prodotto
- Aggiunta al carrello (anche senza login, ma il checkout richiede l’autenticazione)

### 👤 Utente registrato
- Login persistente con checkbox “Ricordami”
- Visualizzazione carrello, modifica quantità e rimozione via AJAX
- Checkout → salvataggio ordine nel DB (prezzi storici)
- Lista ordini con filtro per mese/anno
- Dettaglio ordine e download fattura PDF
- Modifica profilo e cambio password

### 👑 Amministratore (area /admin)
- Gestione catalogo: aggiunta, modifica, eliminazione prodotti (modale AJAX)
- Gestione ordini: visualizzazione con filtri (data, email cliente, stato), cambio stato, eliminazione ordine
- Gestione utenti: modifica ruolo, eliminazione

---

## ✅ Checklist dei requisiti
- Carrello gestito con sessioni
- Storicità di prezzo e IVA negli ordini
- Paginazione catalogo
- Registrazione e login con hash password (SHA‑512)
- Login persistente (cookie + tabella remember_me)
- Filtri di autenticazione (AuthFilter, AdminFilter)
- Area admin: CRUD prodotti, gestione ordini (con filtri), gestione utenti
- Profilo utente e cambio password
- Fattura in PDF (iText)
- AJAX: barra di ricerca suggest, controllo email duplicata, operazioni carrello, modifica prodotto admin, cambio stato ordine
- Validazione lato client con regex
- Responsive design (media query)
- Pagine di errore personalizzate (404, 500)
- Pattern MVC, package control e model
- Uso di DataSource (connection pool)
- Commenti in italiano in tutto il codice

---

## 🧪 Test
L’applicazione è stata testata con:
- **Browser:** Google Chrome, Mozilla Firefox, Microsoft Edge
- **Dispositivi simulati:** desktop, tablet, smartphone (tramite dev tools)
- **Tomcat 9** su Windows e macOS

Tutte le operazioni CRUD e i flussi principali funzionano correttamente.

---

## 👨‍💻 Autori
- **Dan Adrian Radut** – matricola 0512119516
- **Antonio Romano** – matricola 0512120191

Progetto realizzato per il corso di Tecnologie Software per il Web  
Università degli Studi di Salerno – Dipartimento di Informatica

---

## 📄 Licenza
Questo progetto è stato sviluppato a scopo didattico.  
Non è concessa alcuna licenza per uso commerciale senza autorizzazione degli autori.

---

## 🙏 Ringraziamenti
Si ringrazia la Prof.ssa Rita Francese per la chiarezza delle lezioni e il supporto durante il corso, e il Dott. Sabato Nocera per il tutorato e i suggerimenti tecnici.

**Buon acquisto su Buy4Play! 🎮**