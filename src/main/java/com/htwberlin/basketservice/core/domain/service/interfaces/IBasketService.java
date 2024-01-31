package com.htwberlin.basketservice.core.domain.service.interfaces;

import com.htwberlin.basketservice.core.domain.model.Basket;
import com.htwberlin.basketservice.core.domain.model.BasketItem;

import java.util.List;
import java.util.UUID;

public interface IBasketService {

    Basket getBasketById(String basketId);

    List<BasketItem> getAllBasketItems(String basketId);

    BasketItem addBasketItem(BasketItem basketItem);

    void deleteBasketItem(String basketId, UUID productId);

    BasketItem updateBasketItem(String basketId, BasketItem basketItem);

    void createBasket(String basketId);
}
