package com.htwberlin.basketservice.core.domain.service.exception;


import com.htwberlin.basketservice.core.domain.model.BasketItemKey;

public class BasketItemNotFoundException extends RuntimeException {
    public BasketItemNotFoundException(BasketItemKey id) {
        super(String.format("Could not find basket item {basketId: %s  productId: %s}",
                        id.getBasketId().toString(), id.getProductId().toString()));
    }
}
