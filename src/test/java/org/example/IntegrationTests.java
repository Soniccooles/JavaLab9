package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testAddItem() throws Exception {
        Item item = new Item();
        item.setName("Milk");

        mockMvc.perform(post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(item)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Milk"))
                .andExpect(jsonPath("$.purchased").value(false));
    }

    @Test
    public void testMarkAsPurchased() throws Exception {
        // Сначала добавим
        Item item = new Item();
        item.setName("Bread");

        String response = mockMvc.perform(post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(item)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Item addedItem = objectMapper.readValue(response, Item.class);

        // Теперь обновим статус
        mockMvc.perform(put("/api/items/" + addedItem.getId() + "/purchased")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("purchased", true))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.purchased").value(true));
    }
}
