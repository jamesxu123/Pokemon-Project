package com.jamesxu.ics4u;

public class Response {
    public final String message;
    public final boolean status;

    Response(String message, boolean status) {
        this.message = message;
        this.status = status;
    }
}
