package com.cpt.payments.constant;

import lombok.Getter;

public enum ErrorCodeEnum {
    PROVIDER_ID_VALIDATION_FAILED("10017","Bad request, given proiderId parameter is not valid or empty"),
    PAYMENT_TYPE_VALIDATION_FAILED("10012","Bad request, given paymentType parameter is not valid or empty"),
    SIGNATURE_EMPTY("10018","Bad request, signature is empty"),
    SIGNATURE_INVALID("10019","Bad request,  signature is invalid"),

    GENERIC_EXCEPTION("10001","Something went wrong, please try later");
    @Getter
    private String errorCode;
    @Getter
    private String errorMessage;

    private ErrorCodeEnum(String errorCode, String errorMessage) {
        this.errorCode=errorCode;
        this.errorMessage=errorMessage;
    }
}
