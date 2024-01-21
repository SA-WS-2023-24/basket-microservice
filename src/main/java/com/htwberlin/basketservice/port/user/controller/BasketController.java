package com.htwberlin.basketservice.port.user.controller;

import com.htwberlin.basketservice.core.domain.model.BasketItem;
import com.htwberlin.basketservice.core.domain.service.interfaces.IBasketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("v1/basket")
@RestController
public class BasketController {

    private final IBasketService basketService;

    public BasketController(IBasketService basketService) {
        this.basketService = basketService;
    }

    @GetMapping("{basketId}/")
    public @ResponseBody List<BasketItem> getBasketItems(@PathVariable String basketId) {
        return basketService.getAllBasketItems(UUID.fromString(basketId));
    }
}
