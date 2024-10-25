package com.kyalo.mpesaintegration.dto;

import lombok.Builder;

@Builder
public record C2BRequest(String ShortCode, String CommandID, int Amount, String Msisdn, String BillRefNumber) {
}
