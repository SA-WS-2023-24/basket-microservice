package com.htwberlin.basketservice.core.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@IdClass(BasketItemKey.class)
public class BasketItem {

    @Id
    private UUID productId;
    @Id
    @Column(name = "basket_id")
    private String basketId;
    private String name;
    private String imgLink;
    private String description;
    private BigDecimal price;
    private int quantity;
}
