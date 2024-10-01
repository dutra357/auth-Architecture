package com.microservice.stateless_any_api.core.client;

import com.microservice.stateless_any_api.core.DTO.AuthUserResponse;
import com.microservice.stateless_any_api.core.DTO.TokenDTO;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/api/auth")
public interface TokenClient {

    @PostExchange("token/validate")
    TokenDTO validateToken(@RequestHeader String accessToken);

    @GetExchange("user")
    AuthUserResponse getAuthenticatedUser(@RequestHeader String accessToken);
}
