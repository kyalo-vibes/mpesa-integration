package com.kyalo.mpesaintegration.dto;

import lombok.Builder;

@Builder
public record RegisterUrlRequest(String ShortCode,String ResponseType,String ConfirmationURL,String ValidationURL) {
}
