package com.agilemaster.findoil.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agilemaster.findoil.domain.ConfigDomain;

public interface ConfigDomainRepository extends JpaRepository<ConfigDomain, Long>{
	ConfigDomain findByMapName(String mapName);
}

