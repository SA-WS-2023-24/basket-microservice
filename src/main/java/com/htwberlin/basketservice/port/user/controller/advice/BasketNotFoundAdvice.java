package com.htwberlin.basketservice.port.user.controller.advice;

import com.htwberlin.basketservice.core.domain.service.exception.BasketNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BasketNotFoundAdvice {
    @ExceptionHandler(value = BasketNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    String basketNotFoundHandler(BasketNotFoundException ex) {
        return ex.getMessage();
    }
}
