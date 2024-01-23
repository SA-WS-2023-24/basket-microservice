package com.htwberlin.basketservice.core.domain.service.impl;

import com.htwberlin.basketservice.core.domain.model.Basket;
import com.htwberlin.basketservice.core.domain.model.BasketItem;
import com.htwberlin.basketservice.core.domain.model.BasketItemKey;
import com.htwberlin.basketservice.core.domain.service.exception.BasketItemNotFoundException;
import com.htwberlin.basketservice.core.domain.service.interfaces.IBasketItemRepository;
import com.htwberlin.basketservice.core.domain.service.interfaces.IBasketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.htwberlin.basketservice.core.domain.service.exception.BasketNotFoundException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class BasketServiceTest {

    @Mock
    private IBasketRepository basketRepository;

    @Mock
    private IBasketItemRepository basketItemRepository;

    @InjectMocks
    private BasketService basketService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateBasket() {
        UUID basketId = UUID.randomUUID();
        Basket basket = new Basket();
        basket.setBasketId(basketId);

        Basket savedBasket = new Basket();
        savedBasket.setBasketId(basketId);

        when(basketRepository.save(any(Basket.class))).thenReturn(savedBasket);

        UUID result = basketService.createBasket(basketId);

        assertEquals(basket.getBasketId(), result);
    }

    @Test
    public void testGetBasket() {
        UUID basketId = UUID.randomUUID();
        Basket basket = new Basket();
        basket.setBasketId(basketId);

        when(basketRepository.findById(basketId)).thenReturn(Optional.of(basket));

        Basket result = basketService.getBasket(basketId);

        assertEquals(basket, result);
    }

    @Test
    public void addBasketItemTest() {
        UUID basketId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        int quantity = 3;

        BasketItem basketItem = new BasketItem();
        basketItem.setBasketId(basketId);
        basketItem.setProductId(productId);
        basketItem.setQuantity(quantity);

        Basket basket = new Basket();
        basket.setBasketId(basketId);
        basket.setItems(new ArrayList<>());

        BasketItem savedBasketItem = new BasketItem();
        savedBasketItem.setBasketId(basketId);
        savedBasketItem.setProductId(productId);
        savedBasketItem.setQuantity(quantity);

        when(basketRepository.findById(basketId)).thenReturn(Optional.of(basket));
        when(basketItemRepository.findById(any())).thenReturn(Optional.empty());
        when(basketItemRepository.save(any(BasketItem.class))).thenReturn(savedBasketItem);

        BasketItem result = basketService.addBasketItem(basketItem);

        assertEquals(basketItem.getBasketId(), result.getBasketId());
        assertEquals(basketItem.getProductId(), result.getProductId());
        assertEquals(basketItem.getQuantity(), result.getQuantity());
    }

    @Test
    public void updateBasketItemTest() {
        UUID basketId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        int quantity = 3;
        BasketItem basketItem = new BasketItem();
        basketItem.setBasketId(basketId);
        basketItem.setProductId(productId);
        basketItem.setQuantity(quantity);

        BasketItemKey key = new BasketItemKey(basketId, productId);

        Basket basket = new Basket();
        basket.setBasketId(basketId);
        basket.setItems(new ArrayList<>());

        BasketItem savedBasketItem = new BasketItem();
        savedBasketItem.setBasketId(basketId);
        savedBasketItem.setProductId(productId);
        savedBasketItem.setQuantity(quantity);

        when(basketRepository.findById(basketId)).thenReturn(Optional.of(basket));
        when(basketItemRepository.findById(key)).thenReturn(Optional.of(savedBasketItem));
        when(basketItemRepository.save(any(BasketItem.class))).thenReturn(savedBasketItem);

        BasketItem result = basketService.updateBasketItem(basketId, basketItem);

        assertEquals(basketItem.getBasketId(), result.getBasketId());
        assertEquals(basketItem.getProductId(), result.getProductId());
        assertEquals(basketItem.getQuantity(), result.getQuantity());
    }

    @Test
    public void deleteBasketItemTest() {
        UUID basketId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        int quantity = 3;
        BasketItem basketItem = new BasketItem();
        basketItem.setBasketId(basketId);
        basketItem.setProductId(productId);
        basketItem.setQuantity(quantity);

        BasketItemKey key = new BasketItemKey(basketId, productId);

        Basket basket = new Basket();
        basket.setBasketId(basketId);
        basket.setItems(new ArrayList<>());

        BasketItem savedBasketItem = new BasketItem();
        savedBasketItem.setBasketId(basketId);
        savedBasketItem.setProductId(productId);
        savedBasketItem.setQuantity(quantity);

        when(basketRepository.findById(basketId)).thenReturn(Optional.of(basket));
        when(basketItemRepository.findById(key)).thenReturn(Optional.of(savedBasketItem));

        basketService.deleteBasketItem(basketId, productId);
    }

    @Test
    public void testGetAllBasketItems() {
        UUID basketId = UUID.randomUUID();
        Basket basket = Basket.builder().basketId(basketId).build();
        List<BasketItem> basketItems = new ArrayList<>();
        BasketItem item = new BasketItem();
        item.setBasketId(basketId);
        basketItems.add(item);

        when(basketRepository.findById(basketId)).thenReturn(Optional.of(basket));
        when(basketItemRepository.findAllByBasketId(basketId)).thenReturn(basketItems);

        List<BasketItem> result = basketService.getAllBasketItems(basketId);

        assertEquals(basketItems, result);
    }

    @Test
    public void testGetBasketById_NotFound() {
        UUID basketId = UUID.randomUUID();
        when(basketRepository.findById(basketId)).thenReturn(Optional.empty());
        assertThrows(BasketNotFoundException.class, () -> basketService.getBasketById(basketId));
    }

    @Test
    public void testAddBasketItem_BasketNotFound() {
        BasketItem basketItem = new BasketItem();
        basketItem.setBasketId(UUID.randomUUID());
        when(basketRepository.findById(basketItem.getBasketId())).thenReturn(Optional.empty());
        assertThrows(BasketNotFoundException.class, () -> basketService.addBasketItem(basketItem));
    }

    @Test
    public void testDeleteBasketItem_BasketNotFound() {
        UUID basketId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        when(basketRepository.findById(basketId)).thenReturn(Optional.empty());
        assertThrows(BasketNotFoundException.class, () -> basketService.deleteBasketItem(basketId, productId));
    }

    @Test
    public void testUpdateBasketItem_BasketNotFound() {
        UUID basketId = UUID.randomUUID();
        BasketItem basketItem = new BasketItem();
        basketItem.setBasketId(basketId);
        basketItem.setProductId(UUID.randomUUID());
        when(basketRepository.findById(basketId)).thenReturn(Optional.empty());
        assertThrows(BasketNotFoundException.class, () -> basketService.updateBasketItem(basketId, basketItem));
    }

    @Test
    public void testGetAllBasketItems_BasketNotFound() {
        UUID basketId = UUID.randomUUID();
        when(basketRepository.findById(basketId)).thenReturn(Optional.empty());
        assertThrows(BasketNotFoundException.class, () -> basketService.getAllBasketItems(basketId));
    }

    @Test
    public void testGetAllBasketItems_BasketEmpty() {
        UUID basketId = UUID.randomUUID();
        when(basketRepository.findById(basketId)).thenReturn(Optional.of(new Basket()));
        when(basketItemRepository.findAllByBasketId(basketId)).thenReturn(Collections.emptyList());
        assertTrue(basketService.getAllBasketItems(basketId).isEmpty());
    }

    @Test
    public void testDeleteBasketItem_ItemNotFound() {
        UUID basketId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        when(basketRepository.findById(basketId)).thenReturn(Optional.of(new Basket()));
        when(basketItemRepository.findById(new BasketItemKey(basketId, productId))).thenReturn(Optional.empty());
        assertThrows(BasketItemNotFoundException.class, () -> basketService.deleteBasketItem(basketId, productId));
    }

    @Test
    public void testUpdateBasketItem_ItemNotFound() {
        UUID basketId = UUID.randomUUID();
        BasketItem basketItem = new BasketItem();
        basketItem.setBasketId(basketId);
        basketItem.setProductId(UUID.randomUUID());
        when(basketRepository.findById(basketId)).thenReturn(Optional.of(new Basket()));
        when(basketItemRepository.findById(new BasketItemKey(basketId, basketItem.getProductId()))).thenReturn(Optional.empty());
        assertThrows(BasketItemNotFoundException.class, () -> basketService.updateBasketItem(basketId, basketItem));
    }
}