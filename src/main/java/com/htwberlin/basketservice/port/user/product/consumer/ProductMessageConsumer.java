package com.htwberlin.basketservice.port.user.product.consumer;

import com.htwberlin.basketservice.config.RabbitMQConfig;
import com.htwberlin.basketservice.core.domain.model.BasketItem;
import com.htwberlin.basketservice.core.domain.service.interfaces.IBasketService;
import com.htwberlin.basketservice.port.user.mapper.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ProductMessageConsumer {

    private final IBasketService basketService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductMessageConsumer.class);


    public ProductMessageConsumer(IBasketService basketService) {
        this.basketService = basketService;
    }

    @RabbitListener(queues = RabbitMQConfig.ADD_PRODUCT_QUEUE)
    public void receiveAddProductMessage(ProductMessage message) {
        LOGGER.info(String.format("Received message: ADD -> %s", message));
        BasketItem basketItem = Mapper.productMessageToBasketItem(message);
        basketService.addBasketItem(basketItem);
    }

    @RabbitListener(queues = RabbitMQConfig.UPDATE_PRODUCT_QUEUE)
    public void receiveUpdateProductMessage(ProductMessage message) {
        LOGGER.info(String.format("Received message: UPDATE -> %s", message));
        BasketItem basketItem = Mapper.productMessageToBasketItem(message);
        basketService.updateBasketItem(basketItem.getBasketId(), basketItem);
    }

    @RabbitListener(queues = RabbitMQConfig.REMOVE_PRODUCT_QUEUE)
    public void receiveRemoveProductMessage(ProductMessage message) {
        LOGGER.info(String.format("Received message: REMOVE -> %s", message));
        BasketItem basketItem = Mapper.productMessageToBasketItem(message);
        basketService.deleteBasketItem(basketItem.getBasketId(), basketItem.getProductId());
    }
}
