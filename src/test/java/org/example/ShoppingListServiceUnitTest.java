package org.example;

import org.example.model.Item;
import org.example.service.ShoppingListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ShoppingListServiceUnitTest {

    private ShoppingListService service;

    @BeforeEach
    void setup() {
        service = new ShoppingListService();
    }

    @Test
    void testAddItem() {
        Item item = new Item(null, "Milk", false);
        Item added = service.addItem(item);

        assertNotNull(added.getId());
        assertEquals("Milk", added.getName());
        assertFalse(added.isPurchased());

        List<Item> allItems = service.getAllItems();
        assertTrue(allItems.contains(added));
    }

    @Test
    void testMarkAsPurchased() {
        Item item = service.addItem(new Item(null, "Eggs", false));
        Item updated = service.markAsPurchased(item.getId(), true);

        assertTrue(updated.isPurchased());

        Item fromList = service.getAllItems().stream()
                .filter(i -> i.getId().equals(item.getId()))
                .findFirst()
                .orElseThrow();
        assertTrue(fromList.isPurchased());
    }
}
