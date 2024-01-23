package com.htwberlin.basketservice.core.domain.service.exception;

import java.util.UUID;

public class BasketNotFoundException extends RuntimeException {
    public BasketNotFoundException(UUID id) {
        super("Could not find basket " + id.toString());
    }
}