package com.agilemaster.findoil.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.agilemaster.findoil.consants.FindOilConstants;
import com.agilemaster.findoil.domain.FileInfo;
import com.agilemaster.findoil.service.FileInfoService;
import com.agilemaster.findoil.service.ProductUpdateService;
import com.agilemaster.findoil.util.StaticMethod;
import com.agilemaster.share.service.FileService;
import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/fileInfo")
public class FileInfoController {

	@Autowired
	FileInfoService fileInfoService;

	@Autowired
	FileService fileService;
	
	@Autowired
	ProductUpdateService productUpdateService;

	@RequestMapping("/download/{id}")
	public void downloadById(@PathVariable("id") Long id,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/html;charset=utf-8");
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		java.io.BufferedInputStream bis = null;
		java.io.BufferedOutputStream bos = null;
		FileInfo fileInfo = fileInfoService.getFileInfo(id);
		String downLoadPath = fileInfo.getStorePath();
		try {
			long fileLength = new File(downLoadPath).length();
			response.setContentType("application/x-msdownload;");
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(fileInfo.getOriginalName().getBytes("utf-8"),
							"ISO8859-1"));
			response.setHeader("Content-Length", String.valueOf(fileLength));
			bis = new BufferedInputStream(new FileInputStream(downLoadPath));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
	}

	@ResponseBody
	@RequestMapping("/upload")
	public Map<String, Object> upload(MultipartFile file,
			MultipartHttpServletRequest request,Long productId) {
		FileInfo fileInfo = fileService.saveFile(file,
				FindOilConstants.PRODUCT_FILE_STORE_PATH);
		Map<String, Object> result = StaticMethod.genResult();
		if (fileInfo != null) {
			result.put(FindOilConstants.SUCCESS, true);
			result.put("fileId", fileInfo.getId());
			result.put("path", fileInfo.getStorePath());
			if(productId!=null){
				productUpdateService.addToImages(fileInfo, productId);
			}
		} else {

		}
		return result;
	}
	
	@ResponseBody
	

	@RequestMapping("/kindeditorUpload")
	public String kindeditorUpload(MultipartFile imgFile,String dir,
			HttpServletResponse response, Model model) {
		FileInfo fileInfo = fileService.saveFile(imgFile,
				FindOilConstants.PRODUCT_FILE_STORE_PATH);
		response.setContentType("text/html; charset=UTF-8");
		Map<String, Object> result = new HashMap<>();
		response.setHeader("X-Frame-Options", "GOFORIT");
		if (fileInfo != null) {
			result.put("error", 0);
			result.put("url",
					"/upload" + fileInfo.getStorePath());

		} else {
			result.put("error", 1);
			result.put("url", "http://localhost:9099/upload");
		}
		model.addAttribute("json", JSON.toJSONString(result));
		System.out.println(JSON.toJSONString(result));
		return "/product/result";

	}

}
