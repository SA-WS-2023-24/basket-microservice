package com.htwberlin.basketservice.core.domain.model;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class BasketItemKey implements Serializable {
    private UUID basketId;
    private UUID productId;
}
