package com.htwberlin.basketservice.core.domain.service.interfaces;

import com.htwberlin.basketservice.core.domain.model.BasketItem;
import com.htwberlin.basketservice.core.domain.model.BasketItemKey;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface IBasketItemRepository extends CrudRepository<BasketItem, BasketItemKey> {
    List<BasketItem> findAllByBasketId(UUID basketId);
}
