package com.eyelis.messagerouter.domain.model;

import java.time.LocalDateTime;

public record Message(
        Long id,
        String content,
        LocalDateTime timestamp
) {
}