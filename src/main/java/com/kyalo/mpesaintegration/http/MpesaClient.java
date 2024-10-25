package com.kyalo.mpesaintegration.http;

import com.kyalo.mpesaintegration.dto.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface MpesaClient {
    @PostExchange("/mpesa/stkpush/v1/processrequest")
    StkPushResponse mpesaExpress(@RequestBody StkPushPayload stkPushPayload);

    @PostExchange("/mpesa/c2b/v1/registerurl")
    RegisterUrlResponse registerUrl(@RequestBody RegisterUrlRequest registerUrlRequest);

    @PostExchange("/mpesa/c2b/v1/simulate")
    C2BResponse initiateC2BPayment(@RequestBody C2BRequest c2BRequest);

    // TODO 01: Implement C2B request and response DTOs
    // TODO 02: Implement C2B PostExchange
    // TODO 03: Implement C2B
}
