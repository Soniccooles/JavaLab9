package org.example;

import org.example.controller.ShoppingListController;
import org.example.model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class UnitTests {

    private ShoppingListController controller;

    @BeforeEach
    public void setup() {
        controller = new ShoppingListController();
    }

    @Test
    public void testAddItemToCart() {
        Item request = new Item();
        request.setName("Eggs");

        Item result = controller.addItemToCart(request);

        assertNotNull(result.getId());
        assertEquals("Eggs", result.getName());
        assertFalse(result.isPurchased());
        assertEquals(1, controller.getAllItems().size());
    }

    @Test
    public void testMarkAsPurchased() {
        Item item = controller.addItemToCart(new Item(null, "Butter", false));

        controller.markAsPurchased(item.getId(), Map.of("purchased", true));

        assertTrue(controller.getAllItems().get(0).isPurchased());
    }
}
