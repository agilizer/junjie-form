package com.agilemaster.findoil.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.agilemaster.findoil.domain.FileInfo;
import com.agilemaster.findoil.domain.Product;

public interface ProductUpdateService {
	public void addToImages(FileInfo fileInfo,Long productId);
	public Map<String,Object> update(MultipartFile analysisReportFile,Product product,HttpServletRequest request);
	public Map<String,Object> delFile(Long productId,Long fileId,Boolean mainPhoto);
	Map<String, Object> addToCarousel( Long productId, Long fileId) ;
	Map<String, Object> removeFromCarousel( Long productId, Long fileId);
	
}
