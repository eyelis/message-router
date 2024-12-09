package com.eyelis.messagerouter.infrastructure.adapter.persistence;

import com.eyelis.messagerouter.domain.model.Partner;
import com.eyelis.messagerouter.domain.repository.PartnerRepository;
import com.eyelis.messagerouter.infrastructure.adapter.entity.PartnerEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaPartnerRepository implements PartnerRepository {

    private final SpringJpaPartnerRepository jpaRepository;

    public JpaPartnerRepository(SpringJpaPartnerRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Partner> findById(Long id) {
        return jpaRepository.findById(id)
                .map(entity -> new Partner(
                        entity.id(),
                        entity.type(),
                        entity.alias(),
                        entity.direction(),
                        entity.application(),
                        entity.flow(),
                        entity.description())
                );
    }

    @Override
    public List<Partner> findAll() {
        return jpaRepository.findAll().stream()
                .map(entity -> new Partner(
                        entity.id(),
                        entity.type(),
                        entity.alias(),
                        entity.direction(),
                        entity.application(),
                        entity.flow(),
                        entity.description())
                )
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(final Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public Partner save(Partner partner) {
        PartnerEntity entity = new PartnerEntity(
                partner.id(),
                partner.type(),
                partner.alias(),
                partner.direction(),
                partner.application(),
                partner.flow(),
                partner.description()
        );
        PartnerEntity savedEntity = jpaRepository.save(entity);
        return new Partner(
                savedEntity.id(),
                savedEntity.type(),
                savedEntity.alias(),
                savedEntity.direction(),
                savedEntity.application(),
                savedEntity.flow(),
                savedEntity.description());
    }
}
