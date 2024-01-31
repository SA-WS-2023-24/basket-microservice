package com.htwberlin.basketservice.port.consumer.authentication;

import com.htwberlin.basketservice.config.RabbitMQConfig;
import com.htwberlin.basketservice.core.domain.service.interfaces.IBasketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class BasketCreationMessageConsumer {

    private final IBasketService basketService;

    public BasketCreationMessageConsumer(IBasketService basketService) {
        this.basketService = basketService;
    }

    @RabbitListener(queues = RabbitMQConfig.CREATE_BASKET_QUEUE)
    public void receiveAddProductMessage(BasketCreationMessage message) {
        log.info(String.format("Received message: ADD -> %s", message));
        basketService.createBasket(message.getSessionId());
    }
}
