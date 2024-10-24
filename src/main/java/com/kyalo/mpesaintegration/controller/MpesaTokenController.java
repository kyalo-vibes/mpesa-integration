package com.kyalo.mpesaintegration.controller;

import com.kyalo.mpesaintegration.service.MpesaAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class MpesaTokenController {
    private final MpesaAuthenticationService mpesaAuthenticationService;
    @GetMapping("token")
    @ResponseStatus(HttpStatus.OK)
    String getToken(){
        return mpesaAuthenticationService.accessToken();
    }
}