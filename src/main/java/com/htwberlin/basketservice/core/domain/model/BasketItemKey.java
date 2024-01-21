package com.htwberlin.basketservice.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
public class BasketItemKey implements Serializable {
    private UUID basketId;
    private UUID productId;
}
