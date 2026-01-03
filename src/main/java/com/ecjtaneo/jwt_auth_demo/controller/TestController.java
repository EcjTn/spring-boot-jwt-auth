package com.ecjtaneo.jwt_auth_demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/wco")
    public String test() {
        return "working";
    }

    @GetMapping("/v1")
    public String test1(@AuthenticationPrincipal String userId) {
        return userId;
    }
}
