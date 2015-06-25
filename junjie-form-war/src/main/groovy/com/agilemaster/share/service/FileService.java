package com.agilemaster.share.service;

import org.springframework.web.multipart.MultipartFile;

import com.agilemaster.findoil.domain.FileInfo;

public interface FileService {
	/**
	 * save file to path
	 * @param file
	 * @return if  file is null return null,else return FileInfo
	 */
	FileInfo saveFile(MultipartFile file,String path);
	
	void delFile(FileInfo fileInfo);
	void delFile(Long fileInfoId);
}
