package com.microservice.stateless_any_api.controller;

import com.microservice.stateless_any_api.DTO.AnyResponse;
import com.microservice.stateless_any_api.service.AnyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/resource")
public class AnyController {

    private final AnyService anyService;
    public AnyController(AnyService anyService) {
        this.anyService = anyService;
    }

    @GetMapping
    public AnyResponse getResource(@RequestHeader String accessToken) {
        return anyService.getData(accessToken);
    }
}
