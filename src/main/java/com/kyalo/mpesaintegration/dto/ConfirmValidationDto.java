package com.kyalo.mpesaintegration.dto;

import java.math.BigDecimal;

public record ConfirmValidationDto(String TransactionType, String TransID, String TransTime,
                                   BigDecimal TransAmount, int BusinessShortCode, String BillRefNumber,
                                   String InvoiceNumber, BigDecimal OrgAccountBalance, String ThirdPartyTransID,
                                   String FirstName,
                                   String MiddleName,
                                   String LastName) {
}
