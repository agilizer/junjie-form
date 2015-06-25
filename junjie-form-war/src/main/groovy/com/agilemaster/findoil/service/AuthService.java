package com.agilemaster.findoil.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.agilemaster.findoil.viewbeen.UserVO;

public interface AuthService {
	int ERROR_CODE_USER_NOT_FOUND=1;
	
	boolean checkSmsCode(HttpSession session, String code);

	Map<String,Object> register(UserVO userVO,
			HttpServletRequest request);
	Map<String,Object> checkUsername(String username);
	
	Map<String,Object> sendRegisterSmsCode(String username,HttpServletRequest request);
	Map<String,Object> sendFindPwSmsCode(String username,HttpServletRequest request);
	Map<String,Object> resetPassword(UserVO userVO,HttpServletRequest request);
    

}