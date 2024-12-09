package com.eyelis.messagerouter.infrastructure.adapter.dto;

public record MessageRequest(
        String key,
        String message
) {
}
