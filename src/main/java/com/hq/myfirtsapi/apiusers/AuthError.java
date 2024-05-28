package com.hq.myfirtsapi.apiusers;

public enum AuthError {
    TOKEN_EXPIRED("The token is expired"),
    INVALID_USER("The user is not valid for token"),
    TOKEN_CREATED("New token made for %s from %s to %s"),
    DUPLICATED_USER("The user %s is register"),
    APPROVED_REQUEST("The request to %s from %s by %s was approved"),
    PUBLIC_REQUEST("A public request was made to %s from %s"),
    ARGUMENT_EXCEPTION("The argument %s can't be null");
    private final String messageFormat;

    AuthError(String messageFormat) {
        this.messageFormat = messageFormat;
    }

    public String getMessage(String... data) {
        return String.format(messageFormat, (Object[]) data);
    }
}
