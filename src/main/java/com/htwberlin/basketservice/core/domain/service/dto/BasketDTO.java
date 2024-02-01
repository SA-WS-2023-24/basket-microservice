package com.htwberlin.basketservice.core.domain.service.dto;

import com.htwberlin.basketservice.core.domain.model.BasketItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BasketDTO {
    private String basketId;
    private BigDecimal totalCost;
    private List<BasketItem> items;
}
