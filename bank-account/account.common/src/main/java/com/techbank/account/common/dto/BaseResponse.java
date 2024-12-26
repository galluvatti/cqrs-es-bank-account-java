package com.techbank.account.common.dto;

public class BaseResponse {
    private final String message;

    public BaseResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
