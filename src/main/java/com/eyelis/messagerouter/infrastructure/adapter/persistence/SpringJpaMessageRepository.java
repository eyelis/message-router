package com.eyelis.messagerouter.infrastructure.adapter.persistence;

import com.eyelis.messagerouter.infrastructure.adapter.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringJpaMessageRepository extends JpaRepository<MessageEntity, Long> {
}