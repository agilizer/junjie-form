package com.agilemaster.findoil.bootstrap;

import java.util.Calendar;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.agilemaster.findoil.domain.Role;
import com.agilemaster.findoil.domain.User;
import com.agilemaster.findoil.domain.UserInfo;
import com.agilemaster.findoil.domain.UserRole;
import com.agilemaster.findoil.repository.RoleRepository;
import com.agilemaster.findoil.repository.UserInfoRepository;
import com.agilemaster.findoil.repository.UserRepository;
import com.agilemaster.findoil.repository.UserRoleRepository;
import com.agilemaster.findoil.service.UserService;

@Service
public class BootstrapServiceImpl implements BootstrapService,
		ApplicationContextAware {
	private static final Logger log = LoggerFactory
			.getLogger(BootstrapServiceImpl.class);
	private ApplicationContext applicationContext;
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserService userService;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	UserRoleRepository userRoleRepository;
	@Autowired
	ProducCatalogService producCatalogService;
	@Autowired
	AreaInitService areaInitService;
	@Autowired
	StorageLocationInitService storageLocationInitService;
	@Autowired
	ConfigDomainInitService configDomainInitService;
	@Autowired
	ArticleCatalogInitService articleCatalogInitService;
	@Autowired
	UserInfoRepository userInfoRepository;
	@Autowired
	WuluInitService wuluInitService;
	@Autowired
	CarBrandInitService carBrandInitService;
	String ENV = "";
	boolean isDev = false;
	User user  = null;

	@PostConstruct
	public void init() {
		String[] profiles = applicationContext.getEnvironment()
				.getActiveProfiles();
		ENV = org.apache.commons.lang3.StringUtils.join(profiles);
		if (ENV.indexOf("dev") > -1) {
			isDev = true;
		}
		log.info("env---------------->" + ENV);
		initUsers();
		areaInitService.init();
		producCatalogService.init();
		//storageLocationInitService.init();
		configDomainInitService.init();
		articleCatalogInitService.init(user);
		wuluInitService.init();
		carBrandInitService.init();
	}

	public void initUsers() {
		user  = createDefaultUser("11111111111", "123456", Role.ROLE_ADMIN);
		createDefaultUser("12222222222", "123456", Role.ROLE_USER);
	}

	private User createDefaultUser(String username, String password,
			String authority) {
		Role role = roleRepository.findByAuthority(authority);
		if (role == null) {
			role = new Role();
			role.setAvailable(true);
			role.setAuthority(authority);
			role.setDescription("");
			role = roleRepository.save(role);
		}
		User temp  = userRepository.findByUsername(username);
		if(temp==null){
			User user = new User();
			Calendar now = Calendar.getInstance();
			user.setPassword(passwordEncoder.encode(password));
			user.setUsername(username);
			user.setDateCreated(now);
			UserInfo userInfo = new UserInfo();
			userInfo.setDateCreated(now);
			userInfo.setLastUpdated(now);
			userInfo = userInfoRepository.save(userInfo);
			user.setUserInfo(userInfo);
			userRepository.save(user);
			UserRole userAndRole = new UserRole();
			userAndRole.setRole(role);
			userAndRole.setUser(user);
			userRoleRepository.save(userAndRole);
		}
		return temp;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
}
