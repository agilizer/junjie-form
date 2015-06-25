package com.agilemaster.findoil.service;

import com.agilemaster.findoil.domain.Role;
import com.agilemaster.findoil.domain.User;

public interface UserRoleService {
	
	void insert(Role role,User user);
	void insert(Long roleId,Long userId);
	void delete(Role role,User user);
	void delete(Long roleId,Long userId);
	void enabled(Long userId,Boolean enabled);
	
}
