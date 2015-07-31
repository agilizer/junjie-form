package com.agilemaster.form.war.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agilemaster.form.constants.FormWarConstants;
import com.agilemaster.form.domain.FileInfo;
import com.agilemaster.form.option.FileInfoOptions;
import com.agilemaster.form.war.util.StaticMethod;

@RestController
@RequestMapping("/api/v1/fileInfo")
public class FileInfoController {

	@Autowired
	FileInfoOptions fileInfoOptions;
	@ResponseBody
	@RequestMapping("/create")
	public Map<String, Object> create(String saasId,FileInfo fileInfo,
			HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> result = StaticMethod.genResult();
		if (saasId != null && fileInfo != null) {
			fileInfo = fileInfoOptions.save(fileInfo);
			result.put(FormWarConstants.SUCCESS, true);
			result.put(FormWarConstants.DATA, fileInfo);
		}
		return result;
	}
}
