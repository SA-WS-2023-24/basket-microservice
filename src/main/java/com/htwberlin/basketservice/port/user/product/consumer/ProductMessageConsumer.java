package com.htwberlin.basketservice.port.user.product.consumer;

import com.htwberlin.basketservice.config.RabbitMQConfig;
import com.htwberlin.basketservice.core.domain.service.interfaces.IBasketService;
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
    }

    @RabbitListener(queues = RabbitMQConfig.UPDATE_PRODUCT_QUEUE)
    public void receiveUpdateProductMessage(ProductMessage message) {
        LOGGER.info(String.format("Received message: UPDATE -> %s", message));
    }

    @RabbitListener(queues = RabbitMQConfig.REMOVE_PRODUCT_QUEUE)
    public void receiveRemoveProductMessage(ProductMessage message) {
        LOGGER.info(String.format("Received message: REMOVE -> %s", message));
    }
}
