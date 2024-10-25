package com.kyalo.mpesaintegration.http;

import com.kyalo.mpesaintegration.dto.RegisterUrlRequest;
import com.kyalo.mpesaintegration.dto.RegisterUrlResponse;
import com.kyalo.mpesaintegration.dto.StkPushPayload;
import com.kyalo.mpesaintegration.dto.StkPushResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface MpesaClient {
    @PostExchange("/mpesa/stkpush/v1/processrequest")
    StkPushResponse mpesaExpress(@RequestBody StkPushPayload stkPushPayload);

    @PostExchange("/mpesa/c2b/v1/registerurl")
    RegisterUrlResponse registerUrl(@RequestBody RegisterUrlRequest registerUrlRequest);
}
