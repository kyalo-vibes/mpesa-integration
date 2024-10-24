package com.kyalo.mpesaintegration.http;

import com.kyalo.mpesaintegration.dto.AccessToken;
import org.springframework.web.service.annotation.GetExchange;

public interface MpesaAuthClient {
    @GetExchange("oauth/v1/generate?grant_type=client_credentials")
    AccessToken getAccessToken();
}
