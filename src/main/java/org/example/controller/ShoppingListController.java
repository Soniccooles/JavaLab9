package org.example.controller;

import org.example.model.Item;
import org.example.service.ShoppingListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/items")
public class ShoppingListController {

    private final ShoppingListService service;

    public ShoppingListController(ShoppingListService service) {
        this.service = service;
    }

    @GetMapping
    public List<Item> getAllItems() {
        return service.getAllItems();
    }

    @PostMapping
    public Item addItemToCart(@RequestBody Item item) {
        return service.addItem(item);
    }

    @DeleteMapping("/{id}")
    public void deleteFromCart(@PathVariable Long id) {
        service.deleteItem(id);
    }

    @PutMapping("/{id}/purchased")
    public ResponseEntity<Item> markAsPurchased(
            @PathVariable Long id,
            @RequestBody Map<String, Boolean> request) {
        boolean purchased = request.getOrDefault("purchased", false);
        Item updated = service.markAsPurchased(id, purchased);
        return ResponseEntity.ok(updated);
    }
}
