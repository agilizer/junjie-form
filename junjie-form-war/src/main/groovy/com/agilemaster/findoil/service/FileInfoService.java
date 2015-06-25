package com.agilemaster.findoil.service;

import com.agilemaster.findoil.domain.FileInfo;

public interface FileInfoService {
	
	public String getDownloadPathById(Long id);

	public FileInfo getFileInfo(Long id);
	
	
}
