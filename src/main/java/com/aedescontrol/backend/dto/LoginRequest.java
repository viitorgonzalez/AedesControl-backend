package com.aedescontrol.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(@Email(message = "Email deve ser válido")
                           @NotBlank(message = "Email obrigatório")
                           String email,
                           @NotBlank(message = "Senha obrigatório")
                           String password) {
}
