package org.example;

import org.example.controller.ShoppingListController;
import org.example.model.Item;
import org.example.service.ShoppingListService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShoppingListControllerUnitTest {

    private final ShoppingListService service = Mockito.mock(ShoppingListService.class);
    private final ShoppingListController controller = new ShoppingListController(service);

    @Test
    void testAddItem() {
        Item input = new Item(null, "Bread", false);
        Item returned = new Item(1L, "Bread", false);

        when(service.addItem(input)).thenReturn(returned);

        Item result = controller.addItemToCart(input);

        assertEquals(returned, result);
        verify(service, times(1)).addItem(input);
    }

    @Test
    void testMarkAsPurchased() {
        Long id = 1L;
        boolean purchased = true;
        Item updatedItem = new Item(id, "Milk", purchased);

        when(service.markAsPurchased(id, purchased)).thenReturn(updatedItem);

        ResponseEntity<Item> response = controller.markAsPurchased(id, Map.of("purchased", purchased));

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedItem, response.getBody());
        verify(service, times(1)).markAsPurchased(id, purchased);
    }
}
