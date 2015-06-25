package com.agilemaster.findoil.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agilemaster.findoil.domain.Area;

public interface AreaRepository extends JpaRepository<Area, Long>{
	Area findByName(String name);
}
