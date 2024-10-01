package com.microservice.stateless_any_api.core.service;

import com.microservice.stateless_any_api.core.DTO.AnyResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AnyService {

    private final TokenService tokenService;

    public AnyService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public AnyResponse getData(String accessToken) {
        tokenService.validateToken(accessToken);
        var authUser = tokenService.getAuthenticatedUser(accessToken);
        var ok = HttpStatus.OK;

        return new AnyResponse(ok.name(), ok.value(), authUser);
    }
}
