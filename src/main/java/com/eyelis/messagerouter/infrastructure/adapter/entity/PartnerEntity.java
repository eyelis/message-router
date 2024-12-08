package com.eyelis.messagerouter.infrastructure.adapter.entity;


import com.eyelis.messagerouter.domain.model.Direction;
import com.eyelis.messagerouter.domain.model.Flow;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Entity
@Table(name = "partner")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(fluent = true)
public class PartnerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String alias;
    @Enumerated(EnumType.STRING)
    private Direction direction;
    private String application;
    @Enumerated(EnumType.STRING)
    private Flow flow;
    private String description;

}
