package com.agilemaster.findoil.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilemaster.findoil.domain.Role;
import com.agilemaster.findoil.domain.User;
import com.agilemaster.findoil.domain.UserRole;
import com.agilemaster.findoil.repository.RoleRepository;
import com.agilemaster.findoil.repository.UserRepository;
import com.agilemaster.findoil.repository.UserRoleRepository;
import com.agilemaster.share.service.JpaShareService;

@Service
public class UserRoleServiceImpl implements UserRoleService{

	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	UserRoleRepository userRoleRepository;
	@Autowired
	JpaShareService jpaShareService;
	
	private static final Logger log = LoggerFactory
			.getLogger(UserRoleServiceImpl.class);
	@Override
	public void insert(Role role, User user) {
		UserRole userRole = new UserRole();
		userRole.setRole(role);
		userRole.setUser(user);
		userRoleRepository.save(userRole);
	}

	@Override
	public void insert(Long roleId, Long userId) {
		Role role = roleRepository.getOne(roleId);
		User user = userRepository.getOne(userId);
		if(null!=role&&null!=user){
			insert(role,user);
		}else{
			log.warn("insert role or user not found,roleId {} userId{}",roleId,userId);
		}
	}

	@Override
	public void delete(Role role, User user) {
		UserRole userRole = userRoleRepository.findByUserAndRole(user, role);
		if(null!=userRole){
			userRoleRepository.delete(userRole);
		}
	}

	@Override
	public void delete(Long roleId, Long userId) {
		Role role = roleRepository.getOne(roleId);
		User user = userRepository.getOne(userId);
		if(null!=role&&null!=user){
			delete(role,user);
		}else{
			log.warn("delete role or user not found,roleId {} userId{}",roleId,userId);
		}
	}

	@Override
	public void enabled(Long userId, Boolean enabled) {
		jpaShareService.executeForHql("update User set enabled=? where id=?", enabled,userId);
	}

}
