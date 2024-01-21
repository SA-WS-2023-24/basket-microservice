package com.htwberlin.basketservice.core.domain.service.interfaces;

import com.htwberlin.basketservice.core.domain.model.Basket;
import com.htwberlin.basketservice.core.domain.model.BasketItem;

import java.util.List;
import java.util.UUID;

public interface IBasketService {

    Basket getBasketById(UUID basketId);

    List<BasketItem> getAllBasketItems(UUID basketId);

    BasketItem addBasketItem(BasketItem basketItem);

    void deleteBasketItem(UUID basketId, UUID productId);

    BasketItem updateBasketItem(UUID basketId, BasketItem basketItem);
}
