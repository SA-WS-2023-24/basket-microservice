package com.htwberlin.basketservice.port.consumer.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasketCreationMessage {
    private String sessionId;
}
