package com.htwberlin.basketservice.core.domain.service.impl;

import com.htwberlin.basketservice.core.domain.model.Basket;
import com.htwberlin.basketservice.core.domain.model.BasketItem;
import com.htwberlin.basketservice.core.domain.service.BasketNotFoundException;
import com.htwberlin.basketservice.core.domain.service.interfaces.IBasketItemRepository;
import com.htwberlin.basketservice.core.domain.service.interfaces.IBasketRepository;
import com.htwberlin.basketservice.core.domain.service.interfaces.IBasketService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BasketService implements IBasketService {

    private final IBasketRepository basketRepository;
    private final IBasketItemRepository basketItemRepository;

    public BasketService(IBasketRepository basketRepository, IBasketItemRepository basketItemRepository) {
        this.basketRepository = basketRepository;
        this.basketItemRepository = basketItemRepository;
    }

    @Override
    public Basket getBasketById(UUID basketId) {
        return basketRepository.findById(basketId).orElseThrow(() -> new BasketNotFoundException(basketId));
    }

    @Override
    public List<BasketItem> getAllBasketItems(UUID basketId) {
        return basketItemRepository.findBasketItemByBasketId(basketId).orElseThrow(() -> new BasketNotFoundException(basketId));
    }

    @Override
    public BasketItem addBasketItem(UUID basketId, BasketItem basketItem) {
    }

    @Override
    public void deleteBasketItem(UUID basketId, UUID productId) {

    }
}
