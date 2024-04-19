package com.flowerbowl.web.common;

import lombok.Getter;

@Getter
public enum JwtError {

    NOT_EXIST_TOKEN("NT", "Token is not exist"),
    INVALID_TOKEN("IT", "Invalid Token"),
    EXPIRED_TOKEN("ET", "Expired Token"),
    NOT_SUPPORT_TOKEN("NS", "JWT format not supported"),
    NOT_PERMISSION("NP", "No Permission");

    private String code;
    private String message;

    JwtError(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
