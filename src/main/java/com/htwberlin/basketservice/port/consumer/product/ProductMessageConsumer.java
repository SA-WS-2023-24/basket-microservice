package com.htwberlin.basketservice.port.consumer.product;

import com.htwberlin.basketservice.config.RabbitMQConfig;
import com.htwberlin.basketservice.core.domain.model.BasketItem;
import com.htwberlin.basketservice.core.domain.service.exception.BasketItemNotFoundException;
import com.htwberlin.basketservice.core.domain.service.exception.BasketNotFoundException;
import com.htwberlin.basketservice.core.domain.service.interfaces.IBasketService;
import com.htwberlin.basketservice.port.user.mapper.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class ProductMessageConsumer {

    private final IBasketService basketService;

    public ProductMessageConsumer(IBasketService basketService) {
        this.basketService = basketService;
    }

    @RabbitListener(queues = RabbitMQConfig.ADD_PRODUCT_QUEUE)
    public void receiveAddProductMessage(ProductMessage message) {
        log.info(String.format("Received message: ADD -> %s", message));
        BasketItem basketItem = Mapper.productMessageToBasketItem(message);
        basketItem.setBasketId(message.getBasketId());
        basketService.addBasketItem(basketItem);
    }

    @RabbitListener(queues = RabbitMQConfig.UPDATE_PRODUCT_QUEUE)
    public void receiveUpdateProductMessage(ProductMessage message) {
        log.info(String.format("Received message: UPDATE -> %s", message));
        BasketItem basketItem = Mapper.productMessageToBasketItem(message);
        try {
            basketService.updateBasketItem(basketItem.getBasketId(), basketItem);
        } catch (BasketItemNotFoundException basketItemNotFoundException) {
            log.info(basketItemNotFoundException.getMessage());
        }
    }

    @RabbitListener(queues = RabbitMQConfig.REMOVE_PRODUCT_QUEUE)
    public void receiveRemoveProductMessage(ProductMessage message) {
        log.info(String.format("Received message: REMOVE -> %s", message));
        BasketItem basketItem = BasketItem.builder()
                .basketId(message.getBasketId())
                .productId(UUID.fromString(message.getProductId()))
                .build();
        try {
            basketService.deleteBasketItem(basketItem.getBasketId(), basketItem.getProductId());
        } catch (BasketNotFoundException basketNotFoundException) {
            log.info(basketNotFoundException.getMessage());
        }
    }
}
