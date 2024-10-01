package com.microservice.statefull_auth_api.core.controller;

import com.microservice.statefull_auth_api.core.DTO.AuthRequest;
import com.microservice.statefull_auth_api.core.DTO.AuthUserResponse;
import com.microservice.statefull_auth_api.core.DTO.TokenDTO;
import com.microservice.statefull_auth_api.core.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

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

    @PostMapping("token/validate")
    public TokenDTO validateToken(@RequestHeader String accessToken) {
        return authService.validateToken(accessToken);
    }

    @GetMapping("user")
    public AuthUserResponse getAuthenticatedUser(@RequestHeader String accessToken) {
        return authService.getAuthenticatedUser(accessToken);
    }

    @PostMapping("logout")
    public HashMap<String, Object> logout(@RequestHeader String accessToken) {
        authService.logout(accessToken);
        var response = new HashMap<String, Object>();

        var ok = HttpStatus.OK;
        response.put("status", ok.name());
        response.put("code", ok.value());

        return response;
    }
}
