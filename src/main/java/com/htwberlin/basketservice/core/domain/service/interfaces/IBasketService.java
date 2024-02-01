package com.htwberlin.basketservice.core.domain.service.interfaces;

import com.htwberlin.basketservice.core.domain.model.BasketItem;
import com.htwberlin.basketservice.core.domain.service.dto.BasketDTO;
import com.htwberlin.basketservice.core.domain.service.exception.BasketItemNotFoundException;
import com.htwberlin.basketservice.core.domain.service.exception.BasketNotFoundException;

import java.util.List;
import java.util.UUID;

public interface IBasketService {

    BasketDTO getBasketById(String basketId);

    List<BasketItem> getAllBasketItems(String basketId);

    BasketItem addBasketItem(BasketItem basketItem);

    void deleteBasketItem(String basketId, UUID productId) throws BasketNotFoundException ;

    BasketItem updateBasketItem(String basketId, BasketItem basketItem) throws BasketItemNotFoundException;
}
