package com.htwberlin.basketservice.core.domain.service.impl;

import com.htwberlin.basketservice.core.domain.model.BasketItem;
import com.htwberlin.basketservice.core.domain.service.dto.BasketDTO;
import com.htwberlin.basketservice.core.domain.service.exception.BasketItemNotFoundException;
import com.htwberlin.basketservice.core.domain.service.interfaces.IBasketItemRepository;
import com.htwberlin.basketservice.core.domain.service.interfaces.IBasketService;
import com.htwberlin.basketservice.core.domain.model.BasketItemKey;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BasketService implements IBasketService {
    private final IBasketItemRepository basketItemRepository;

    public BasketService(IBasketItemRepository basketItemRepository) {
        this.basketItemRepository = basketItemRepository;
    }

    @Override
    public BasketDTO getBasketById(String basketId) {
        List<BasketItem> basketItems = basketItemRepository.findAllByBasketId(basketId);
        return BasketDTO.builder()
                .basketId(basketId)
                .items(basketItems)
                .totalCost(calculateTotalCost(basketItems))
                .build();
    }

    @Override
    public List<BasketItem> getAllBasketItems(String basketId) {
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

        return itemAddedToRepo;
    }

    @Override
    public void deleteBasketItem(String basketId, UUID productId) {
        BasketItemKey key = new BasketItemKey(basketId, productId);

        Optional<BasketItem> optionalBasketItem = basketItemRepository.findById(key);
        if (optionalBasketItem.isEmpty()) {
            throw new BasketItemNotFoundException(key);
        }
        basketItemRepository.delete(optionalBasketItem.get());
    }

    @Override
    public BasketItem updateBasketItem(String basketId, BasketItem basketItem) {
        BasketItemKey key = new BasketItemKey(basketId, basketItem.getProductId());

        Optional<BasketItem> itemOptional = basketItemRepository.findById(key);

        BasketItem itemFromRepo;
        if (itemOptional.isPresent()) {
            BasketItem item = itemOptional.get();
            item.setQuantity(basketItem.getQuantity());
            itemFromRepo = basketItemRepository.save(item);
        } else {
            throw new BasketItemNotFoundException(key);
        }

        return itemFromRepo;
    }

    private BigDecimal calculateTotalCost(List<BasketItem> basketItems) {
        return basketItems.stream().
                map(basketItem ->
                        basketItem.getPrice().multiply(new BigDecimal(basketItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}