package com.orhonit.ole.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orhonit.ole.server.model.SeqSerializerNum;

public interface SeqNumRepository extends JpaRepository<SeqSerializerNum,String> {

}
