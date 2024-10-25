package com.kyalo.mpesaintegration.service;

import com.kyalo.mpesaintegration.dto.*;
import com.kyalo.mpesaintegration.entity.MpesaPayment;
import com.kyalo.mpesaintegration.http.MpesaClient;
import com.kyalo.mpesaintegration.repository.MpesaPaymentRepository;
import com.kyalo.mpesaintegration.utils.MpesaPaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MpesaPaymentService {
    @Autowired
    private final MpesaPaymentRepository mpesaPaymentRepository;
    @Autowired
    private final MpesaClient mpesaClient;
    //This is a list of account numbers that we have registered on out application
    private List<String> allowedAccounts = List.of("ACCOUNT_ONE","ACCOUNT_TWO","ACCOUNT_THREE");
    /**
     * on my validation logic, I check whether the account is present
     * I check whether this payment has already been recorded if yes, we return response code 0
     * otherwise we record the apyment and return response code 0
     * @param  payment ConfirmationValidationDto
     * @return ConfirmationValidationResponse
     */
    public ConfirmValidationResponse validatePayment(ConfirmValidationDto payment){
        if(allowedAccounts.contains(payment.BillRefNumber())){
            return mpesaPaymentRepository.findById(payment.TransID())
                    .map(mpesaPayment -> {
                        //We had already registered this transaction
                        // we return a result code of 0
                        return ConfirmValidationResponse.builder()
                                .ResultCode("0")
                                .ResultDesc("Accepted")
                                .build();
                    })
                    .orElseGet(()->{
                        //We record the payment and return a result code of 0
                        return savePayment(payment);
                    });
        }else{
            return ConfirmValidationResponse.builder()
                    .ResultCode("C2B00012")
                    .ResultDesc("Rejected")
                    .build();
        }
    }

    /**
     * for confirmation, we check if payment had been validated
     * we go ahead and mark the transaction as confirmed
     * @param payment
     * @return ConfirmationValidationResponse
     */
    public ConfirmValidationResponse confirmPayment(ConfirmValidationDto payment){
        if(allowedAccounts.contains(payment.BillRefNumber())){
            return mpesaPaymentRepository.findById(payment.TransID())
                    .map(mpesaPayment -> {
                        //Payment had already been validated
                        mpesaPayment.setStatus(MpesaPaymentStatus.CONFIRMED.value);
                        mpesaPaymentRepository.save(mpesaPayment);
                        return ConfirmValidationResponse.builder()
                                .ResultCode("0")
                                .ResultDesc("Accepted")
                                .build();
                    })
                    .orElseGet(()->{
                        // if you require validation you can throw an exception here
                        //this is because validation has to happen before confirmation
                        return savePayment(payment);
                    });
        }else{
            return ConfirmValidationResponse.builder()
                    .ResultCode("C2B00012")
                    .ResultDesc("Rejected")
                    .build();
        }
    }
    private ConfirmValidationResponse savePayment(ConfirmValidationDto payment){
        MpesaPayment model = new MpesaPayment();
        BeanUtils.copyProperties(payment,model);
        model.setStatus(MpesaPaymentStatus.VALIDATED.value);
        mpesaPaymentRepository.save(model);
        return ConfirmValidationResponse.builder()
                .ResultCode("0")
                .ResultDesc("Accepted")
                .build();
    }

    public RegisterUrlResponse registerUrl(RegisterUrlRequest registerUrlRequest){

        RegisterUrlResponse registerUrlResponse = mpesaClient.registerUrl(registerUrlRequest);
        return registerUrlResponse;
    }

    /**
     * Process a C2B Payment using the provided payment details.
     * This will handle the initiation of the C2B payment.
     *
     * @param C2BRequest c2bRequest
     * @return C2BResponse
     */
    public C2BResponse processC2BPayment(C2BRequest c2bRequest) {

        // Call the Mpesa client to initiate the C2B payment
        C2BResponse c2bPaymentResponse = mpesaClient.initiateC2BPayment(c2bRequest);

        String responseCode = c2bPaymentResponse.ResponseCode();
        String originatorConversationID = c2bPaymentResponse.OriginatorCoversationID();

// Use the extracted values instead of directly accessing them on the proxy object
        if ("0".equals(responseCode)) {
            // Save the payment to the database as validated
            MpesaPayment payment = new MpesaPayment();
            BeanUtils.copyProperties(c2bRequest, payment);

            // Manually assign a unique ID using UUID
            payment.setTransID(UUID.randomUUID().toString());
            payment.setStatus(MpesaPaymentStatus.VALIDATED.value);
            mpesaPaymentRepository.save(payment);


//            // Update the response to show acceptance
//            c2bPaymentResponse = C2BResponse.builder()
//                    .ResponseCode(c2bPaymentResponse.ResponseCode())
//                    .ResponseDescription(c2bPaymentResponse.ResponseDescription())
//                    .OriginatorConversationID(c2bPaymentResponse.OriginatorConversationID())
//                    .build();
        }

        return c2bPaymentResponse;
    }
    
}
