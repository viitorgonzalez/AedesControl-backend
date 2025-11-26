package com.aedescontrol.backend.dto;

public record LoginResponse(String accessToken, Long expiresIn) {
}
