package com.microservice.stateless_auth_api.core.controller;

import com.microservice.stateless_auth_api.core.DTO.AuthRequest;
import com.microservice.stateless_auth_api.core.DTO.TokenDTO;
import com.microservice.stateless_auth_api.core.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("login")
    public TokenDTO login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }

    @PostMapping("validate")
    public TokenDTO validateToken(@RequestHeader String accessToken) {
        return authService.validateToken(accessToken);
    }
}
