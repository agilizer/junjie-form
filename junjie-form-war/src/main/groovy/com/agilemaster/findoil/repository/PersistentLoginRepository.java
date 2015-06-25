package com.agilemaster.findoil.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.agilemaster.findoil.domain.PersistentLogin;

public interface PersistentLoginRepository extends JpaRepository<PersistentLogin, String>{
	
	@Modifying()
	@Query("update PersistentLogin set token =?1, lastUsed = ?2 where series = ?3")
	void updateToken(String token,Date lastUsed,String series);
	
	void deleteByName(String name);

}