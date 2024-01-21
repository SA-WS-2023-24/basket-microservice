package com.htwberlin.basketservice.core.domain.service.interfaces;

import com.htwberlin.basketservice.core.domain.model.Basket;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface IBasketRepository extends CrudRepository<Basket, UUID> {
}
