package com.htwberlin.basketservice.port.user.controller;

import com.htwberlin.basketservice.core.domain.model.Basket;
import com.htwberlin.basketservice.core.domain.model.BasketItem;
import com.htwberlin.basketservice.core.domain.service.interfaces.IBasketService;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{basketId}/items")
    public @ResponseBody List<BasketItem> getBasketItems(@PathVariable UUID basketId) {
        return basketService.getAllBasketItems(basketId);
    }

    @PostMapping("/{basketId}")
    public ResponseEntity<?> createBasket(@PathVariable UUID basketId) {
        basketService.createBasket(basketId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{basketId}")
    public @ResponseBody Basket getBasket(@PathVariable UUID basketId) {
        return basketService.getBasketById(basketId);
    }
}
