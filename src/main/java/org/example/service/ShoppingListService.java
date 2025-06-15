package org.example.service;

import org.example.model.Item;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ShoppingListService {

    private final List<Item> items = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    public List<Item> getAllItems() {
        return items;
    }

    public Item addItem(Item item) {
        Item newItem = new Item();
        newItem.setId(counter.incrementAndGet());
        newItem.setName(item.getName());
        newItem.setPurchased(false);
        items.add(newItem);
        return newItem;
    }

    public void deleteItem(Long id) {
        items.removeIf(x -> x.getId().equals(id));
    }

    public Item markAsPurchased(Long id, boolean purchased) {
        Item item = items.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));
        item.setPurchased(purchased);
        return item;
    }
}
