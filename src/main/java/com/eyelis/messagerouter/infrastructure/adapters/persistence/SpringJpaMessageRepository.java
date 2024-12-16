package com.eyelis.messagerouter.infrastructure.adapters.persistence;

import com.eyelis.messagerouter.infrastructure.adapters.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringJpaMessageRepository extends JpaRepository<MessageEntity, Long> {
}