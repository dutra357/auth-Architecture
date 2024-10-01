package com.microservice.stateless_any_api.core.DTO;

public record AnyResponse(String status, Integer code, AuthUserResponse authUser) {
}
