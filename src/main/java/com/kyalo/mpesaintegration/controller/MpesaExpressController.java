package com.kyalo.mpesaintegration.controller;

import com.kyalo.mpesaintegration.dto.PaymentRequest;
import com.kyalo.mpesaintegration.dto.StkPushResponse;
import com.kyalo.mpesaintegration.service.MpesaExpressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/express-payment")
@RequiredArgsConstructor
public class MpesaExpressController {
    private final MpesaExpressService mpesaExpressService;
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    StkPushResponse paymentRequest(@RequestBody PaymentRequest paymentRequest){
        return mpesaExpressService.paymentRequest(paymentRequest);
    }
}
