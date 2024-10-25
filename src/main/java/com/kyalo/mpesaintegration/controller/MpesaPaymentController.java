package com.kyalo.mpesaintegration.controller;

import com.kyalo.mpesaintegration.dto.ConfirmValidationDto;
import com.kyalo.mpesaintegration.dto.ConfirmValidationResponse;
import com.kyalo.mpesaintegration.service.MpesaPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class MpesaPaymentController {
    private final MpesaPaymentService mpesaPaymentService;
    /**
     *  This is the URL that is only used when a Merchant
     *  (Partner) requires to validate the
     *  details of the payment before accepting.
     *  For example, a bank would want to verify if an account number exists
     *  in their platform before accepting a payment from the customer.
     * @param payment
     * @return
     */
    @PostMapping("validate")
    @ResponseStatus(HttpStatus.OK)
    ConfirmValidationResponse validatePayment(@RequestBody ConfirmValidationDto payment){
        return mpesaPaymentService.validatePayment(payment);
    }

    /**
     *  This is the URL that receives payment notification once payment has been
     *  completed successfully on M-PESA.
     * @param payment
     * @return
     */
    @PostMapping("confirm")
    @ResponseStatus(HttpStatus.OK)
    ConfirmValidationResponse confirmPayment(@RequestBody ConfirmValidationDto payment){
        return mpesaPaymentService.confirmPayment(payment);
    }
}
