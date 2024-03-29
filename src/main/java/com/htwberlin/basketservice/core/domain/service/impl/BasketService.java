package com.htwberlin.basketservice.core.domain.service.impl;

import com.htwberlin.basketservice.core.domain.model.BasketItem;
import com.htwberlin.basketservice.core.domain.service.dto.BasketDTO;
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
    public void addBasketItem(BasketItem basketItem) {
        BasketItemKey key = new BasketItemKey(basketItem.getBasketId(), basketItem.getProductId());

        basketItemRepository.findById(key)
                .ifPresentOrElse(
                        (basketItemFromRepo -> {
                            int quantity = basketItemFromRepo.getQuantity();
                            basketItemFromRepo.setQuantity(quantity + basketItem.getQuantity());
                            basketItemRepository.save(basketItemFromRepo);
                        }),
                        (() -> basketItemRepository.save(basketItem))
                );
    }

    @Override
    public void deleteBasketItem(String basketId, UUID productId) {
        BasketItemKey key = new BasketItemKey(basketId, productId);

        basketItemRepository.findById(key)
                .ifPresent(basketItem -> basketItemRepository.delete(basketItem));
    }

    @Override
    public void updateBasketItem(String basketId, BasketItem basketItem) {
        BasketItemKey key = new BasketItemKey(basketId, basketItem.getProductId());

        Optional<BasketItem> itemOptional = basketItemRepository.findById(key);

        if (itemOptional.isPresent()) {
            BasketItem item = itemOptional.get();
            item.setQuantity(basketItem.getQuantity());
            basketItemRepository.save(item);
        }
    }

    private BigDecimal calculateTotalCost(List<BasketItem> basketItems) {
        return basketItems.stream().
                map(basketItem ->
                        basketItem.getPrice().multiply(new BigDecimal(basketItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}