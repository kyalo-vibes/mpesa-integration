package com.kyalo.mpesaintegration.utils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MpesaPaymentStatus {
    VALIDATED("VALIDATED"),
    CONFIRMED("CONFIRMED");
    public final String value;
}
