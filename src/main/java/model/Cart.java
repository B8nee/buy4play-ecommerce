package model;

import java.util.ArrayList;
import java.util.List;

public class Carrello {
    private List<CarrelloItem> items = new ArrayList<>();

    public List<CarrelloItem> getItems() {
        return items;
    }

    public void addProdotto(Prodotto p) {
        for (CarrelloItem item : items) {
            if (item.getProdotto().getId() == p.getId()) {
                item.setQuantita(item.getQuantita() + 1);
                return;
            }
        }
        items.add(new CarrelloItem(p, 1));
    }

    public void removeProdotto(int idProdotto) {
        items.removeIf(item -> item.getProdotto().getId() == idProdotto);
    }

    public void updateQuantita(int idProdotto, int nuovaQuantita) {
        for (CarrelloItem item : items) {
            if (item.getProdotto().getId() == idProdotto) {
                if (nuovaQuantita <= 0) {
                    removeProdotto(idProdotto);
                } else {
                    item.setQuantita(nuovaQuantita);
                }
                return;
            }
        }
    }

    public double getTotale() {
        double totale = 0;
        for (CarrelloItem item : items) {
            totale += item.getProdotto().getPrezzo() * item.getQuantita();
        }
        return totale;
    }

    public void clear() {
        items.clear();
    }
}
