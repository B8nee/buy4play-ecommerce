package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Modella il carrello della spesa di un utente.
 * Contiene una lista di oggetti CartItem, ciascuno associato a un prodotto e
 * una quantità.
 * Fornisce metodi per aggiungere, rimuovere, aggiornare quantità, calcolare il
 * totale e svuotare il carrello.
 */
public class Cart {
    private List<CartItem> items = new ArrayList<>();

    /**
     * Restituisce la lista degli articoli nel carrello.
     * 
     * @return lista di CartItem
     */
    public List<CartItem> getItems() {
        return items;
    }

    /**
     * Aggiunge un prodotto al carrello.
     * Se il prodotto è già presente, incrementa la quantità di 1.
     * Altrimenti, aggiunge un nuovo CartItem con quantità 1.
     * 
     * @param prodotto il prodotto da aggiungere
     */
    public void addItem(Prodotto prodotto) {
        // Cerca se esiste già un CartItem per lo stesso prodotto
        Optional<CartItem> existing = items.stream()
                .filter(item -> item.getProdotto().getId() == prodotto.getId())
                .findFirst();
        if (existing.isPresent()) {
            // Prodotto già presente: aumenta la quantità di 1
            existing.get().setQuantita(existing.get().getQuantita() + 1);
        } else {
            // Prodotto non presente: crea nuovo CartItem con quantità 1
            items.add(new CartItem(prodotto, 1));
        }
    }

    /**
     * Rimuove completamente un prodotto dal carrello.
     * 
     * @param productId l'ID del prodotto da rimuovere
     */
    public void removeItem(int productId) {
        items.removeIf(item -> item.getProdotto().getId() == productId);
    }

    /**
     * Aggiorna la quantità di un prodotto nel carrello.
     * Se la nuova quantità è <= 0, rimuove l'articolo.
     * 
     * @param productId l'ID del prodotto
     * @param quantita  la nuova quantità (deve essere >= 1 per mantenere
     *                  l'articolo)
     */
    public void updateQuantity(int productId, int quantita) {
        if (quantita <= 0) {
            removeItem(productId);
        } else {
            for (CartItem item : items) {
                if (item.getProdotto().getId() == productId) {
                    item.setQuantita(quantita);
                    break;
                }
            }
        }
    }

    /**
     * Calcola il totale del carrello (somma dei subtotali di ogni articolo).
     * 
     * @return totale in Euro
     */
    public double getTotal() {
        return items.stream().mapToDouble(CartItem::getSubtotale).sum();
    }

    /**
     * Svuota completamente il carrello, rimuovendo tutti gli articoli.
     */
    public void clear() {
        items.clear();
    }

    /**
     * Verifica se il carrello è vuoto.
     * 
     * @return true se non ci sono articoli, false altrimenti
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }
}