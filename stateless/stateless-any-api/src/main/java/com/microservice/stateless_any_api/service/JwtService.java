package com.microservice.stateless_any_api.service;

import com.microservice.stateless_any_api.DTO.AuthUserResponse;
import com.microservice.stateless_any_api.exception.AuthenticationException;
import com.microservice.stateless_any_api.exception.ValidationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@RequiredArgsConstructor
public class JwtService {

    private static final String EMPTY_SPACE = " ";
    private static final Integer TOKEN_INDEX = 1;

    @Value("${app.token.secretKey}")
    private String secretKey;

    public AuthUserResponse getAuthenticatedUser(String token) {
        var tokenClaims = getClains(token);
        var userId = Integer.valueOf((String) tokenClaims.get("id"));

        return new AuthUserResponse(userId, ((String) tokenClaims.get("username")));
    }


    private Claims getClains(String token) {
        var accessToken = extractToken(token);
        try {
            return Jwts
                    .parser()
                    .verifyWith(generateSign())
                    .build()
                    .parseSignedClaims(accessToken)
                    .getPayload();
        } catch (Exception ex) {
            throw new AuthenticationException("Invalid token: " + ex.getMessage());
        }
    }


    public void validateAccessToken(String token) {
        getClains(token);
    }

    private String extractToken(String token) {
        if (isEmpty(token)) {
            throw new ValidationException("The access token was not informed.");
        }

        if (token.contains(EMPTY_SPACE)) {
            return token.split(EMPTY_SPACE)[TOKEN_INDEX];
        }

        return token;
    }

    private SecretKey generateSign() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}
