package com.htwberlin.basketservice.core.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class BasketItem {

    private String name;
    private String imgLink;
    private String description;
    private BigDecimal price;
    private int quantity;

    @Id
    private UUID productId;

}
