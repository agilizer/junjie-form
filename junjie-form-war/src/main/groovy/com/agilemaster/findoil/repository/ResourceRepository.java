package com.agilemaster.findoil.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agilemaster.findoil.domain.Resource;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
}


