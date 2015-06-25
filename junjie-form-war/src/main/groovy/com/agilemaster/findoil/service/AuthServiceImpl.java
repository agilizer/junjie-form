package com.agilemaster.findoil.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilemaster.findoil.consants.FindOilConstants;
import com.agilemaster.findoil.util.StaticMethod;
import com.agilemaster.findoil.viewbeen.UserVO;

@Service
public class AuthServiceImpl implements AuthService {
	private static final Logger log = LoggerFactory
			.getLogger(AuthServiceImpl.class);

	@Autowired
	ShareService shareService;
	@Autowired
	UserService userService;
	@Autowired
	SmsService smsService;
	@Autowired
	ConfigDomainService configDomainService;

	@Override
	public boolean checkSmsCode(HttpSession session, String code) {
		boolean isRight = false;
		if (code.equals(session
				.getAttribute(FindOilConstants.SMS_CODE_SESSION_NAME))) {
			isRight = true;
		}
		return isRight;
	}

	private boolean sendSmsCode(HttpSession session, String phoneNumber,
			String content) {
		boolean isRight = false;
		
		String captchaSolution = RandomStringUtils.random(
				FindOilConstants.DEFAULT_LENGTH,
				FindOilConstants.DEFAULT_CAPTCHA_CHARS.toCharArray());
		String sendStr = content.replace(FindOilConstants.SMS_VAR_CHECK_CODE,
				captchaSolution);
		session.setAttribute(FindOilConstants.SMS_CODE_SESSION_NAME,
				captchaSolution);
		if (smsService.sendSMS(phoneNumber, sendStr) > 0) {
			isRight = true;
		}
		log.info(sendStr);		
		return isRight;

	}

	@Override
	public Map<String, Object> register(UserVO userVO,
			HttpServletRequest request) {
		Map<String, Object> result = StaticMethod.genResult();
		boolean isRight = true;
		if (!(userVO.getUsername().trim().matches("^1[0-9]{10}"))) {
			isRight = false;
		} else if (!userVO.getPassword().equals(userVO.getPasswordRepeat())) {
			isRight = false;
		} else if (userVO.getCode() == null) {
			isRight = false;
		} else if (!checkSmsCode(request.getSession(), userVO.getCode())) {
			isRight = false;
		} else if (userService.createUser(userVO) == null) {
			isRight = false;
		}
		if (isRight) {
			result.put(FindOilConstants.SUCCESS, true);
		}
		return result;
	}

	@Override
	public Map<String, Object> checkUsername(String username) {
		Map<String, Object> result = StaticMethod.genResult();
		if (userService.findByUsername(username) == null
				&& username.trim().matches("^1[0-9]{10}")) {
			result.put(FindOilConstants.SUCCESS, true);
		}
		return result;
	}

	@Override
	public Map<String, Object> sendRegisterSmsCode(String username,
			HttpServletRequest request) {
		Map<String, Object> result = StaticMethod.genResult();
		if (userService.findByUsername(username) == null) {
			String content = configDomainService.getConfigString(FindOilConstants.CON_SMS_REGISTER_TEMPLATE);
			boolean sendResult = sendSmsCode(request.getSession(), username,
					content);
			result.put(FindOilConstants.SUCCESS, sendResult);
		}
		return result;
	}

	@Override
	public Map<String, Object> sendFindPwSmsCode(String username,
			HttpServletRequest request) {
		Map<String, Object> result = StaticMethod.genResult();
		if (userService.findByUsername(username) != null) {
			String content = configDomainService.getConfigString(FindOilConstants.CON_SMS_FINDPW_TEMPLATE);
			boolean sendResult = sendSmsCode(request.getSession(), username,
					content);
			result.put(FindOilConstants.SUCCESS, sendResult);
		}
		return result;
	}

	@Override
	public Map<String, Object> resetPassword(UserVO userVO,
			HttpServletRequest request) {
		Map<String, Object> result = StaticMethod.genResult();
		boolean isRight = true;
		if (userService.findByUsername(userVO.getUsername()) == null) {
			isRight = false;
			result.put(FindOilConstants.ERROR_MSG, "用户没有找到！");
			result.put(FindOilConstants.ERROR_CODE, ERROR_CODE_USER_NOT_FOUND);
		} else if (userVO.getCode() == null) {
			isRight = false;
			result.put(FindOilConstants.ERROR_CODE, 2);
			result.put(FindOilConstants.ERROR_MSG, "验证码不能为空！");
		} else if (!userVO.getPassword().equals(userVO.getPasswordRepeat())) {
			isRight = false;
			result.put(FindOilConstants.ERROR_CODE, 3);
			result.put(FindOilConstants.ERROR_MSG, "两次密码不一致！");
		} else if (!checkSmsCode(request.getSession(), userVO.getCode())) {
			isRight = false;
			result.put(FindOilConstants.ERROR_MSG, "验证码不正确！");
			result.put(FindOilConstants.ERROR_CODE, 4);
		}
		if (isRight) {
			userService.changePassword(
					userService.findByUsername(userVO.getUsername()),
					userVO.getPassword());
			result.put(FindOilConstants.SUCCESS, true);
		}
		return result;
	}

}
