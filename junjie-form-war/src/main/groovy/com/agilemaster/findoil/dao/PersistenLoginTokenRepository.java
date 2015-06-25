package com.agilemaster.findoil.dao;

import java.util.Calendar;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Service;

import com.agilemaster.findoil.domain.PersistentLogin;
import com.agilemaster.findoil.repository.PersistentLoginRepository;

@Service(value="persistenLoginTokenRepository")
public class PersistenLoginTokenRepository implements PersistentTokenRepository {

	@Autowired()
	private PersistentLoginRepository persistentLoginRepository;
	@Override
	public void createNewToken(PersistentRememberMeToken token) {
		PersistentLogin persistentLogin = new PersistentLogin();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(token.getDate());
		persistentLogin.setLastUsed(calendar);
		persistentLogin.setName(token.getUsername());
		persistentLogin.setSeries(token.getSeries());
		persistentLogin.setToken(token.getTokenValue());
		persistentLoginRepository.save(persistentLogin);
	}

	@Transactional
	@Override
	public void updateToken(String series, String tokenValue, Date lastUsed) {
		persistentLoginRepository.updateToken(tokenValue, lastUsed, series);
	}

	@Override
	public PersistentRememberMeToken getTokenForSeries(String seriesId) {
		PersistentLogin persistentLogin = persistentLoginRepository.findOne(seriesId);
		PersistentRememberMeToken token =null;
		if(persistentLogin!=null){
		//	String username, String series, String tokenValue, Date date
			token = new PersistentRememberMeToken(persistentLogin.getName(),persistentLogin.getSeries(),persistentLogin.getToken(),persistentLogin.getLastUsed().getTime());
		} 
		return token;
	}

	@Transactional
	@Override
	public void removeUserTokens(String username) {
		persistentLoginRepository.deleteByName(username);
	}

}
