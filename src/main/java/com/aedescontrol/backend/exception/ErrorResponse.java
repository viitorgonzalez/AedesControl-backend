package com.aedescontrol.backend.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ErrorResponse {
    private LocalDateTime timestamp;
    private HttpStatus status;
    private String error;

    public ErrorResponse(HttpStatus status, String error) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }
}
