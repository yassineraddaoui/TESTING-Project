package com.ala.book.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum BusinessError {
    NO_CODE(0,HttpStatus.NOT_IMPLEMENTED,"no code"),
    INCORRECT_CURRENT_PASSWORD(300,HttpStatus.BAD_REQUEST,"current password is incorrect"),
    NEW_PASSWORD_DOES_NOT_MATCH(301,HttpStatus.BAD_REQUEST,"the new password does not match"),
    ACCOUNT_LOKCED(302,HttpStatus.FORBIDDEN,"user account is locked"),
    ACCOUNT_DISABLED(303,HttpStatus.FORBIDDEN,"user account is disabled"),
    BAD_CREDENTIALS(304,HttpStatus.FORBIDDEN,"login or password is incorrect"),


    ;
    @Getter
    private final int code;
    @Getter
    private final String description;
    @Getter
    private final HttpStatus httpStatus;

    BusinessError(int code, HttpStatus httpStatus, String description) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
