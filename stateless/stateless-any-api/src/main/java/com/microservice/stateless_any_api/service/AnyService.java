package com.microservice.stateless_any_api.service;

import com.microservice.stateless_any_api.DTO.AnyResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AnyService {

    private final JwtService jwtService;
    public AnyService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public AnyResponse getData(String accessToken) {
        jwtService.validateAccessToken(accessToken);
        var authUser = jwtService.getAuthenticatedUser(accessToken);

        //HttpStatus is a ENUM
        var ok = HttpStatus.OK;
        return new AnyResponse(ok.name(), ok.value(), authUser);
    }
}
