package com.htwberlin.basketservice.port.user.mapper;

import com.htwberlin.basketservice.core.domain.model.BasketItem;
import com.htwberlin.basketservice.port.consumer.product.ProductMessage;

import java.util.UUID;

public class Mapper {
    public static BasketItem productMessageToBasketItem(ProductMessage message) {
        return BasketItem.builder()
                .basketId(message.getBasketId())
                .productId(UUID.fromString(message.getProductId()))
                .price(message.getPrice())
                .name(message.getName())
                .description(message.getDescription())
                .imgLink(message.getImageLink())
                .quantity(message.getQuantity())
                .build();
    }
}
