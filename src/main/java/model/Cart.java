package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Cart {
    private List<CartItem> items = new ArrayList<>();

    public List<CartItem> getItems() {
        return items;
    }

    public void addItem(Prodotto prodotto) {
        Optional<CartItem> existing = items.stream()
                .filter(item -> item.getProdotto().getId() == prodotto.getId())
                .findFirst();
        if (existing.isPresent()) {
            existing.get().setQuantita(existing.get().getQuantita() + 1);
        } else {
            items.add(new CartItem(prodotto, 1));
        }
    }

    public void removeItem(int productId) {
        items.removeIf(item -> item.getProdotto().getId() == productId);
    }

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

    public double getTotal() {
        return items.stream().mapToDouble(CartItem::getSubtotale).sum();
    }

    public void clear() {
        items.clear();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}