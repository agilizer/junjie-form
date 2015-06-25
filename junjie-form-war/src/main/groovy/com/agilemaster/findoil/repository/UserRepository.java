package com.agilemaster.findoil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.agilemaster.findoil.domain.Role;
import com.agilemaster.findoil.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	public User findByUsername(String username);
	@Query("select u_r.role  from UserRole u_r   where u_r.user.id = ?1")
	public List<Role> listRoleByUserId(Long userId);
}
