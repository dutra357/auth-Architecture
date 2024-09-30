package com.microservice.stateless_auth_api.core.service;

import com.microservice.stateless_auth_api.core.DTO.AuthRequest;
import com.microservice.stateless_auth_api.core.DTO.TokenDTO;
import com.microservice.stateless_auth_api.core.repository.UserRepository;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository repository;
    public AuthService(PasswordEncoder passwordEncoder, JwtService jwtService, UserRepository repository) {
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.repository = repository;
    }

    public TokenDTO login(AuthRequest request) {
        var user = repository.findByUserName(request.userName())
                .orElseThrow(() -> new ValidationException("User not found."));

        var accessToken = jwtService.createToken(user);
        validatePassword(request.password(), user.getPassword());

        return new TokenDTO(accessToken);
    }

    private void validatePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new ValidationException("Incorrect password.");
        }
    }




    public TokenDTO validateToken(String accessToken) {
        validateExistingToken(accessToken);
        jwtService.validateAccessToken(accessToken);

        return new TokenDTO(accessToken);
    }

    private void validateExistingToken(String accessToken) {
        if (isEmpty(accessToken)) {
            throw new ValidationException("The access token must be informed.");
        }
    }
}
