package com.agilemaster.findoil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.agilemaster.findoil.domain.Role;
import com.agilemaster.findoil.domain.User;
import com.agilemaster.findoil.domain.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

	@Query("select role from UserRole ur join ur.user user join ur.role role where user.id = ?")
	List<Role> findRolesByUser(Long userId);
	UserRole findByUserAndRole(User user,Role role);
}
