package com.orhonit.ole.enforce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orhonit.ole.enforce.entity.SysUserEntity;

public interface SysUserRepository extends JpaRepository<SysUserEntity, String>{

	SysUserEntity findById(Integer id);
	
	SysUserEntity findByUsername(String username);

}
