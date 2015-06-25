package com.agilemaster.findoil.service;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.agilemaster.findoil.domain.Role;
import com.agilemaster.findoil.domain.User;
import com.agilemaster.findoil.domain.UserInfo;
import com.agilemaster.findoil.domain.UserRole;
import com.agilemaster.findoil.repository.RoleRepository;
import com.agilemaster.findoil.repository.UserRepository;
import com.agilemaster.findoil.repository.UserRoleRepository;
import com.agilemaster.findoil.viewbeen.UserVO;

/**
 * 
 * @author abel.lee
 *2014年11月14日 上午10:11:14
 */
@Service
public class UserServiceImpl implements UserService {
	private static final Logger log = LoggerFactory
			.getLogger(UserServiceImpl.class);
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
    /**
     * 创建用户
     * @param user
     */
    public User createUser(UserVO userVO) {
    	User user = new User();
    	user.setUsername(userVO.getUsername());
    	user.setPassword(passwordEncoder.encode(userVO.getPassword()));////加密密码 
    	user.setDateCreated(Calendar.getInstance());
        user.setLastUpdated(Calendar.getInstance());
    	user=userRepository.save(user);
    	Role role = roleRepository.findByAuthority(Role.ROLE_USER);
    	UserRole userAndRole = new UserRole();
    	userAndRole.setRole(role);
		userAndRole.setUser(user);
		userRoleRepository.save(userAndRole);
		user.setUserInfo(new UserInfo());
        return user;
    }

    @Override
    public User updateUser(User user) {
    	//TODO
        return userRepository.save(user);
    }

	@Override
	public void deleteUser(User user) {
		userRepository.delete(user);		
	}

	@Override
	public void changePassword(User user, String newPassword) {
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
	}

	@Override
	public User findByUsername(String username) {
		if(username ==null){
			return null;
		}
		return userRepository.findByUsername(username);
	}

	@Override
	public void loginAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User currentUser() {
		User user = null;
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		log.info("principal-->" + principal.getClass().getName()
				+ "   --->value:" + principal.toString());
		if (principal instanceof UserDetails) {
			String username = ((UserDetails) principal).getUsername();
			user = findByUsername(username);
		}
		return user;
	}
	


}
