package com.eyelis.messagerouter.domain.model;

public record Partner(
        Long id,
        String type,
        String alias,
        Direction direction,
        String application,
        Flow flow,
        String description
) {
}