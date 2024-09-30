package com.microservice.statefull_auth_api.core.service;

import com.microservice.statefull_auth_api.core.DTO.AuthRequest;
import com.microservice.statefull_auth_api.core.DTO.AuthUserResponse;
import com.microservice.statefull_auth_api.core.DTO.TokenDTO;
import com.microservice.statefull_auth_api.core.Repository.UserRepository;
import com.microservice.statefull_auth_api.core.model.User;
import com.microservice.statefull_auth_api.exception.ValidationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public TokenDTO login(AuthRequest request) {
        var user = findByUsername(request.username());
        var accessToken = tokenService.createToken(user.getUserName());
        validatePassword(request.password(), user.getPassword());
        return new TokenDTO(accessToken);
    }

    public TokenDTO validateToken(String accessToken) {
        validateExistingToken(accessToken);
        var valid = tokenService.validateAccessToken(accessToken);
        if (valid) {
            return new TokenDTO(accessToken);
        }

        throw new ValidationException("Invalid token.");
    }

    public AuthUserResponse getAuthenticatedUser(String accessToken) {
        var tokenData = tokenService.getTokenData(accessToken);
        var user = findByUsername(tokenData.username());
    }

    private User findByUsername(String username) {
        userRepository.findByUsername(username)
                .orElseThrow(() -> new ValidationException("User not found."));
    }

    private void validatePassword(String rawPassword, String encodedPassword) {
        if (isEmpty(rawPassword)) {
            throw new ValidationException("The password must be informed.");
        }
        if(!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new ValidationException("Incorrect password!");
        }
    }

    private void validateExistingToken(String accessToken) {
        if (isEmpty(accessToken)) {
            throw new ValidationException("The access token must be informed.");
        }
    }
}
