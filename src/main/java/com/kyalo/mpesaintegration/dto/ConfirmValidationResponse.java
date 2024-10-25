package com.kyalo.mpesaintegration.dto;

import lombok.Builder;

@Builder
public record ConfirmValidationResponse(String ResultCode, String ResultDesc) {
}
