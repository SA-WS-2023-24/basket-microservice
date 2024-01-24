package com.htwberlin.basketservice.core.domain.service.impl;

import com.htwberlin.basketservice.core.domain.model.Basket;
import com.htwberlin.basketservice.core.domain.model.BasketItem;
import com.htwberlin.basketservice.core.domain.model.BasketItemKey;
import com.htwberlin.basketservice.core.domain.service.exception.BasketItemNotFoundException;
import com.htwberlin.basketservice.core.domain.service.exception.BasketNotFoundException;
import com.htwberlin.basketservice.core.domain.service.interfaces.IBasketItemRepository;
import com.htwberlin.basketservice.core.domain.service.interfaces.IBasketRepository;
import com.htwberlin.basketservice.core.domain.service.interfaces.IBasketService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
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
        return basketRepository.findById(basketId)
                .orElseThrow(() -> new BasketNotFoundException(basketId));
    }

    @Override
    public List<BasketItem> getAllBasketItems(UUID basketId) {
        basketRepository.findById(basketId).orElseThrow(
                () -> new BasketNotFoundException(basketId));

        return basketItemRepository.findAllByBasketId(basketId);
    }

    @Override
    public BasketItem addBasketItem(BasketItem basketItem) {
        BasketItemKey key = new BasketItemKey(basketItem.getBasketId(), basketItem.getProductId());

        Optional<BasketItem> itemOptional = basketItemRepository.findById(key);


        BasketItem itemAddedToRepo;

        if (itemOptional.isPresent()) {
            BasketItem item = itemOptional.get();
            item.setQuantity(item.getQuantity() + basketItem.getQuantity());
            itemAddedToRepo = basketItemRepository.save(item);
        } else {
            itemAddedToRepo = basketItemRepository.save(basketItem);
        }

        Basket basket = basketRepository.findById(basketItem.getBasketId())
                .orElseThrow(() -> new BasketNotFoundException(basketItem.getBasketId()));

        updateBasketPrice(basket);
        return itemAddedToRepo;
    }

    @Override
    public void deleteBasketItem(UUID basketId, UUID productId) {
        BasketItemKey key = new BasketItemKey(basketId, productId);

        Basket basket = basketRepository.findById(basketId).orElseThrow(() -> new BasketNotFoundException(basketId));
        Optional<BasketItem> optionalBasketItem = basketItemRepository.findById(key);
        if (!optionalBasketItem.isPresent()) {
            throw new BasketItemNotFoundException(key);
        }
        basketItemRepository.delete(optionalBasketItem.get());
        updateBasketPrice(basket);
    }

    @Override
    public BasketItem updateBasketItem(UUID basketId, BasketItem basketItem) {
        BasketItemKey key = new BasketItemKey(basketId, basketItem.getProductId());

        Optional<BasketItem> itemOptional = basketItemRepository.findById(key);

        Basket basket = basketRepository.findById(basketId).orElseThrow(() -> new BasketNotFoundException(basketId));

        BasketItem itemFromRepo;
        if (itemOptional.isPresent()) {
            BasketItem item = itemOptional.get();
            item.setQuantity(basketItem.getQuantity());
            itemFromRepo = basketItemRepository.save(item);
        } else {
            throw new BasketItemNotFoundException(key);
        }

        updateBasketPrice(basket);
        return itemFromRepo;
    }

    @Override
    public UUID createBasket(UUID basketId) {
        Basket basket = Basket.builder()
                .basketId(basketId)
                .freeShippingLimit(new BigDecimal("60.00"))
                .totalCost(new BigDecimal("0"))
                .items(new ArrayList<>())
                .build();
        return basketRepository.save(basket).getBasketId();
    }

    private void updateBasketPrice(Basket basket) {
        List<BasketItem> basketItems = basket.getItems();

        BigDecimal totalCost = basketItems.stream().
                map(basketItem ->
                basketItem.getPrice().multiply(new BigDecimal(basketItem.getQuantity())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
        basket.setTotalCost(totalCost);
        basketRepository.save(basket);
    }
}