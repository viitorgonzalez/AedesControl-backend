package com.aedescontrol.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidStatusException extends RuntimeException {
    public InvalidStatusException(String status) {
        super("Status inválido: '" + status + "'. Valores aceitos: SUSPEITA, CONFIRMADO, LIVRE");
    }
}