package com.agilemaster.findoil.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agilemaster.findoil.domain.FileInfo;

public interface FileInfoRepository extends  JpaRepository<FileInfo, Long>{

}

