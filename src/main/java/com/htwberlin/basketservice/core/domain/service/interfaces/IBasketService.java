package com.htwberlin.basketservice.core.domain.service.interfaces;

import com.htwberlin.basketservice.core.domain.model.BasketItem;
import com.htwberlin.basketservice.core.domain.service.dto.BasketDTO;

import java.util.List;
import java.util.UUID;

public interface IBasketService {

    BasketDTO getBasketById(String basketId);

    List<BasketItem> getAllBasketItems(String basketId);

    BasketItem addBasketItem(BasketItem basketItem);

    void deleteBasketItem(String basketId, UUID productId);

    BasketItem updateBasketItem(String basketId, BasketItem basketItem);
}
