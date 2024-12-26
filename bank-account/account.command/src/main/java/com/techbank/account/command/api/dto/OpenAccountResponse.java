package com.techbank.account.command.api.dto;

import com.techbank.account.common.dto.BaseResponse;

public class OpenAccountResponse extends BaseResponse {
    private final String id;

    public OpenAccountResponse(String message, String id) {
        super(message);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
