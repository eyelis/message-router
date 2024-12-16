package com.eyelis.messagerouter.infrastructure.adapters.persistence;

import com.eyelis.messagerouter.application.ports.out.PartnerRepository;
import com.eyelis.messagerouter.domain.model.Partner;
import com.eyelis.messagerouter.infrastructure.adapters.persistence.mapper.PartnerMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaPartnerRepository implements PartnerRepository {

    private final SpringJpaPartnerRepository jpaRepository;

    public JpaPartnerRepository(final SpringJpaPartnerRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Partner> save(final Partner partner) {
        return Optional.of(jpaRepository.save(PartnerMapper.INSTANCE.toEntity(partner))).map(PartnerMapper.INSTANCE::toDto);
    }

    @Override
    public Optional<Partner> findById(final Long id) {
        return jpaRepository.findById(id).map(PartnerMapper.INSTANCE::toDto);
    }

    @Override
    public List<Partner> findAll() {
        return jpaRepository.findAll().stream().map(PartnerMapper.INSTANCE::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteById(final Long id) {
        jpaRepository.deleteById(id);
    }
}
