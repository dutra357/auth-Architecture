package com.microservice.stateless_any_api.core.service;

import com.microservice.stateless_any_api.core.DTO.AuthUserResponse;
import com.microservice.stateless_any_api.core.client.TokenClient;
import com.microservice.stateless_any_api.exception.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class TokenService {

    private final TokenClient tokenClient;
    private static final Logger log = LoggerFactory.getLogger(TokenService.class);

    public TokenService(TokenClient tokenClient) {
        this.tokenClient = tokenClient;
    }

    public void validateToken(String accessToken) {
        try {
            log.info("Sending request for token validation {}.", accessToken);
            var response = tokenClient.validateToken(accessToken);
            log.info("Token is valid, {}.", response.accessToken());

        } catch (Exception ex) {
            throw new AuthenticationException("Auth error: " + ex.getMessage());
        }
    }

    public AuthUserResponse getAuthenticatedUser(String accessToken) {
        try {
            log.info("Sending request for auth user: {}", accessToken);
            var response = tokenClient.getAuthenticatedUser(accessToken);
            log.info("Auth user found: {}. Token reference: {}", response.toString(), accessToken);
            return response;

        } catch (Exception ex) {
            throw new AuthenticationException("Error to get authenticated user:  " + ex.getMessage());
        }
    }
}
