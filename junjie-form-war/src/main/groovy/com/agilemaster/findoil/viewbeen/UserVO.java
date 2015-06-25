package com.agilemaster.findoil.viewbeen;

public class UserVO {
	private String username;
	private String password;
	private String passwordRepeat;
	private String remember;
	private String code;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordRepeat() {
		return passwordRepeat;
	}
	public void setPasswordRepeat(String passwordRepeat) {
		this.passwordRepeat = passwordRepeat;
	}
	public String getRemember() {
		return remember;
	}
	public void setRemember(String remember) {
		this.remember = remember;
	}
	@Override
	public String toString() {
		return "UserVO [username=" + username 
				+ ", password=" + password + ", passwordRepeat="
				+ passwordRepeat + ", remember=" + remember + ", code=" + code
				+ "]";
	}
	
	

}
