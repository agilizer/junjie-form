package com.agilemaster.findoil.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agilemaster.findoil.domain.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long>  {

}
