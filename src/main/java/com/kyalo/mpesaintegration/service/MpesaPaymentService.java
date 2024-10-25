package com.kyalo.mpesaintegration.service;

import com.kyalo.mpesaintegration.dto.ConfirmValidationDto;
import com.kyalo.mpesaintegration.dto.ConfirmValidationResponse;
import com.kyalo.mpesaintegration.dto.RegisterUrlRequest;
import com.kyalo.mpesaintegration.dto.RegisterUrlResponse;
import com.kyalo.mpesaintegration.entity.MpesaPayment;
import com.kyalo.mpesaintegration.http.MpesaClient;
import com.kyalo.mpesaintegration.repository.MpesaPaymentRepository;
import com.kyalo.mpesaintegration.utils.MpesaPaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MpesaPaymentService {
    private final MpesaPaymentRepository mpesaPaymentRepository;
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

        return mpesaClient.registerUrl(registerUrlRequest);
    }
}
