package com.agilemaster.findoil.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agilemaster.findoil.service.AuthService;
import com.agilemaster.findoil.service.UserService;
import com.agilemaster.findoil.viewbeen.UserVO;

@Controller
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	AuthService authService;
	@Autowired
	UserService userService;
	
	@RequestMapping("/registerPage")
	public String registerPage() {
		return "auth/register";
	}
	@RequestMapping("/loginPage")
	public String loginPage() {
		return "login";
	}
	@RequestMapping("/findPwPage")
	public String findPwPage() {
		return "auth/findPw";
	}
	@ResponseBody
	@RequestMapping("/register")
	public Map<String, Object> register(UserVO userVO,HttpServletRequest request) {
		return authService.register(userVO, request);
	}

	@ResponseBody
	@RequestMapping("/checkUsername/{username}")
	public Map<String, Object> checkUsername(@PathVariable("username") String username) {
		return authService.checkUsername(username);
	}

	@ResponseBody
	@RequestMapping("/currentUser")
	public Object currentUser() {
		return userService.currentUser();
	}

	
	/**
	 * 注册验证码 
	 * @param username 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/sendRegisterSmsCode/{username}")
	public Map<String, Object> sendRegisterSmsCode(@PathVariable("username") String username,HttpServletRequest request) {	
		return authService.sendRegisterSmsCode(username, request);
	}
	/**
	 * 重置密码
	 * @param username
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/sendFindPwSmsCode/{username}")
	public Map<String, Object> sendFindPwSmsCode(@PathVariable("username") String username,
			HttpServletRequest request) {		
		return authService.sendFindPwSmsCode(username, request);
	}

	@ResponseBody
	@RequestMapping("/resetPassword")
	public Map<String, Object> resetPassword(UserVO userVO,
			HttpServletRequest request) {	
		return authService.resetPassword(userVO, request);
	}

}
