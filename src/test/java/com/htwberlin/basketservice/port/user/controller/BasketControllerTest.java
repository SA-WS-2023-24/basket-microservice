package com.htwberlin.basketservice.port.user.controller;


import com.htwberlin.basketservice.core.domain.model.Basket;
import com.htwberlin.basketservice.core.domain.model.BasketItem;
import com.htwberlin.basketservice.core.domain.service.exception.BasketNotFoundException;
import com.htwberlin.basketservice.core.domain.service.interfaces.IBasketService;
import com.htwberlin.basketservice.port.user.controller.advice.BasketItemNotFoundAdvice;
import com.htwberlin.basketservice.port.user.controller.advice.BasketNotFoundAdvice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BasketControllerTest {

    @Mock
    IBasketService basketService;

    @InjectMocks
    BasketController basketController;

    private MockMvc mockMvc;

    final List<BasketItem> basketItems =  new ArrayList<>();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(basketController)
                .setControllerAdvice(new BasketNotFoundAdvice(), new BasketItemNotFoundAdvice())
                .build();

        for (int i = 0; i < 20; i++) {
            BasketItem basketItem = BasketItem.builder()
                    .basketId(UUID.randomUUID())
                    .name("Item " + i)
                    .quantity(i)
                    .productId(UUID.randomUUID())
                    .build();

            basketItems.add(basketItem);
        }
    }

    @Test
    void getBasketItemsTest() throws Exception {
        UUID randomUuid = UUID.randomUUID();

        when(basketService.getAllBasketItems(any(UUID.class))).thenReturn(basketItems);

        MvcResult result = mockMvc.perform(get("/v1/basket/{basketId}/items", randomUuid)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        for (BasketItem item : basketItems) {
            assertTrue(responseBody.contains(item.getBasketId().toString()));
        }
    }

    @Test
    void getBasketItemsEmptyTest() throws Exception {
        UUID randomUuid = UUID.randomUUID();

        when(basketService.getAllBasketItems(any(UUID.class))).thenReturn(new ArrayList<>());

        MvcResult result = mockMvc.perform(get("/v1/basket/{basketId}/items", randomUuid)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertTrue(content.equals("[]"));

    }

    @Test
    void getBasketTest() throws Exception {
        UUID randomUuid = UUID.randomUUID();
        Basket basket = Basket.builder()
                .basketId(randomUuid)
                .items(basketItems)
                .build();

        when(basketService.getBasketById(any(UUID.class))).thenReturn(basket);

        MvcResult result = mockMvc.perform(get("/v1/basket/{basketId}", randomUuid)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        assertTrue(responseBody.contains(basket.getBasketId().toString()));
    }

    @Test
    void getBasketNotFound() throws Exception {
        UUID randomUuid = UUID.randomUUID();
        when(basketService.getBasketById(any(UUID.class))).thenThrow(new BasketNotFoundException(randomUuid));

        mockMvc.perform(get("/v1/basket/{basketId}", randomUuid)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Could not find basket " + randomUuid))
                .andReturn();

    }

}
