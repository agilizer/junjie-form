package com.agilemaster.findoil.controller

import javax.servlet.http.HttpServletRequest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

import com.agilemaster.findoil.consants.FindOilConstants
import com.agilemaster.findoil.domain.User
import com.agilemaster.findoil.domain.UserInfo
import com.agilemaster.findoil.repository.UserInfoRepository
import com.agilemaster.findoil.service.UserService
import com.agilemaster.findoil.util.StaticMethod

@Controller
@RequestMapping("/userCenter")
class UserCenterController {
	@Autowired
	UserService userService;
	@Autowired
	UserInfoRepository userInfoRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

	def index(HttpServletRequest request){
	}

	@RequestMapping("/index")
	public String index(Model model,HttpServletRequest request) {
		User user = userService.currentUser();
		if(user!=null){
			model.addAttribute("username", user.getUsername());
			request.getSession().setAttribute("username", user.getUsername());
		}
		return "userCenter/index"
	}

	@RequestMapping("/userInfo")
	public String userInfo(Model model,HttpServletRequest request) {
		User user = userService.currentUser();
		if(user!=null){
			model.addAttribute("userInfo", user.getUserInfo());
		}
		return "userCenter/userInfo"
	}
	@ResponseBody
	@RequestMapping("/userInfoSave")
	public Map<String, Object> userInfoSave(UserInfo userInfo,Model model,HttpServletRequest request) {
		Map<String, Object> result = StaticMethod.genResult()
		User user = userService.currentUser();
		if(user!=null&&user.getUserInfo().getId()==userInfo.getId()){
			userInfoRepository.save(userInfo)
			result.put(FindOilConstants.SUCCESS, true)
		}
		return result
	}

	@RequestMapping("/changePassword")
	public String changePassword(Model model,HttpServletRequest request) {
		return "userCenter/changePassword"
	}
	@ResponseBody
	@RequestMapping("/updatePassword")
	public Map<String, Object> updatePassword(String oldPassword,String password,String passwordRepeat){
		Map<String, Object> result = StaticMethod.genResult();
		User user = userService.currentUser();
		boolean isRight = true;
		if (user == null) {
			isRight = false;
			result.put(FindOilConstants.ERROR_MSG, "用户没有找到！");
			result.put(FindOilConstants.ERROR_CODE, 1);
		}else if(!passwordEncoder.matches(oldPassword, user.getPassword())){
			isRight = false;
			result.put(FindOilConstants.ERROR_CODE, 2);
			result.put(FindOilConstants.ERROR_MSG, "原密码不正确！");
		}
		else if (!password.equals(passwordRepeat)) {
			isRight = false;
			result.put(FindOilConstants.ERROR_CODE, 3);
			result.put(FindOilConstants.ERROR_MSG, "两次密码不一致！");
		}
		if (isRight) {
			userService.changePassword(
					user,
					password);
			result.put(FindOilConstants.SUCCESS, true);
		}
		return result;
	}
}
