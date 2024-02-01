package com.htwberlin.basketservice.core.domain.service.impl;

import com.htwberlin.basketservice.core.domain.service.interfaces.IBasketItemRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class BasketServiceTest {

    @Mock
    private IBasketItemRepository basketItemRepository;

    @InjectMocks
    private BasketService basketService;

}