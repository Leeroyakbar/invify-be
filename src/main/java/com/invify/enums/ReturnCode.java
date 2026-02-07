package com.invify.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ReturnCode {
    DATA_SUCCESSFULLY_CREATED(20001, "Data successfully created"),
    DATA_SUCCESSFULLY_UPDATED(20002, "Data successfully updated"),
    DATA_SUCCESSFULLY_DELETED(20003, "Data successfully deleted"),
    LOGIN_SUCCESSFULLY(20004, "Login successfully"),
    DATA_SUCCESSFULLY_FETCHED(20005, "Data successfully fetched"),
    DATA_NOT_FOUND(40001, "Data not found");
    private final int code;
    private final String message;
    ReturnCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
