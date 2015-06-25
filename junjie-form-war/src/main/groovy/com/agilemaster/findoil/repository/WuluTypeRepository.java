package com.agilemaster.findoil.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agilemaster.findoil.domain.WuluType;

public interface WuluTypeRepository extends JpaRepository<WuluType, Long> {
	WuluType findByName(String name);
}

