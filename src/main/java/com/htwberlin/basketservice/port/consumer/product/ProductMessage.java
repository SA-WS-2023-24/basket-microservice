package com.htwberlin.basketservice.port.consumer.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductMessage {
    private String productId;
    private String name;
    private BigDecimal price;
    private String imageLink;
    private String description;
    private int quantity;
    private String basketId;
}
