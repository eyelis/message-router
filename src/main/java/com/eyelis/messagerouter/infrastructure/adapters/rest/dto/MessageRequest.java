package com.eyelis.messagerouter.infrastructure.adapters.rest.dto;

public record MessageRequest(
        String key,
        String message
) {
}
