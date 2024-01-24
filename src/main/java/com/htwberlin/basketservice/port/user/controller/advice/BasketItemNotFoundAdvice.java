package com.htwberlin.basketservice.port.user.controller.advice;

import com.htwberlin.basketservice.core.domain.service.exception.BasketItemNotFoundException;
import com.htwberlin.basketservice.core.domain.service.exception.BasketNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BasketItemNotFoundAdvice {
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BasketItemNotFoundException.class)
    String basketItemNotFoundHandler(BasketItemNotFoundException ex) {
        return ex.getMessage();
    }
}
