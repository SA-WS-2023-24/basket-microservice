package com.htwberlin.basketservice.port.user.controller;

import com.htwberlin.basketservice.core.domain.model.Basket;
import com.htwberlin.basketservice.core.domain.model.BasketItem;
import com.htwberlin.basketservice.core.domain.service.interfaces.IBasketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("v1/basket")
@RestController
public class BasketController {

    private final IBasketService basketService;

    public BasketController(IBasketService basketService) {
        this.basketService = basketService;
    }

    @GetMapping("/{basketId}/items")
    public @ResponseBody List<BasketItem> getBasketItems(@PathVariable String basketId) {
        return basketService.getAllBasketItems(basketId);
    }

    @GetMapping("/{basketId}")
    public @ResponseBody Basket getBasket(@PathVariable String basketId) {
        return basketService.getBasketById(basketId);
    }
}
