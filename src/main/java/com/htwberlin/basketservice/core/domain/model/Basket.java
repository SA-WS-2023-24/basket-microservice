package com.htwberlin.basketservice.core.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "basket")
@Entity
public class Basket {
    @Id
    @Column(name = "basket_id")
    private UUID basketId;
    private BigDecimal totalCost;
    private BigDecimal freeShippingLimit;
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<BasketItem> items;
}
