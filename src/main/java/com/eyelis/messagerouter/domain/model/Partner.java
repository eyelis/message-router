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
    public static Partner of(
            final Long id,
            final String type,
            final String alias,
            final Direction direction,
            final String application,
            final Flow flow,
            final String description
    ) {
        return new Partner(id, type, alias, direction, application, flow, description);
    }
}