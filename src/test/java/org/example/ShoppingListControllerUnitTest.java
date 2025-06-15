package org.example;

import org.example.controller.ShoppingListController;
import org.example.model.Item;
import org.example.service.ShoppingListService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShoppingListController.class)
public class ShoppingListControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoppingListService shoppingListService; // мок-сервис

    @Test
    void testGetAllItems() throws Exception {
        List<Item> items = List.of(new Item(1L, "Milk", false));
        when(shoppingListService.getAllItems()).thenReturn(items);

        mockMvc.perform(get("/api/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Milk"));

        verify(shoppingListService).getAllItems();
    }
}

