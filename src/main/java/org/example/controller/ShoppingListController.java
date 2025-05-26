package org.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/items")
public class ShoppingListController {

    public List<Item> items = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();


    @GetMapping
    public List<Item> getAllItems () {
        return items;
    }

    @PostMapping
    public Item addItemToCart(@RequestBody Item item) {
        Item newItem = new Item();
        newItem.setId(counter.incrementAndGet());
        newItem.setName(item.getName());
        newItem.setPurchased(false);
        items.add(newItem);
        return newItem;
    }

    @DeleteMapping("/{id}")
    public void deleteFromCart(@PathVariable Long id) {
        items.removeIf(x -> x.getId().equals(id));
    }

    @PutMapping("/{id}/purchased")
    public ResponseEntity<Item> markAsPurchased(
            @PathVariable Long id,
            @RequestBody Map<String, Boolean> request) {

        boolean purchased = request.getOrDefault("purchased", false);

        Item item = items.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));

        item.setPurchased(purchased);
        return ResponseEntity.ok(item);
    }
}
