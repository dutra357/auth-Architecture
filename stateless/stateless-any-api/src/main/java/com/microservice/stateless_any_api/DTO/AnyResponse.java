package com.microservice.stateless_any_api.DTO;

public record AnyResponse(String status, Integer code, AuthUserResponse authUser) {
}
