package com.htwberlin.basketservice.core.domain.service.interfaces;

import com.htwberlin.basketservice.core.domain.model.BasketItem;
import com.htwberlin.basketservice.core.domain.model.BasketItemKey;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IBasketItemRepository extends CrudRepository<BasketItem, BasketItemKey> {
    Optional<List<BasketItem>> findBasketItemByBasketId(UUID basketId);
}
