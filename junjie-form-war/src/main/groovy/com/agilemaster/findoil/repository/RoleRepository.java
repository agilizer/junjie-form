package com.agilemaster.findoil.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agilemaster.findoil.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByAuthority(String role);
}
