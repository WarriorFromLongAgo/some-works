package com.orhonit.ole.enforce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.orhonit.ole.enforce.entity.MessagePushEntity;

public interface MessagePushRepository extends JpaRepository<MessagePushEntity, String> {

}
