package com.agilemaster.findoil.controller

import javax.servlet.http.HttpServletRequest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.session.SessionRegistry
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.multipart.MultipartFile

import com.agilemaster.findoil.domain.StorageLocation
import com.agilemaster.findoil.repository.StorageLocationRepository
import com.agilemaster.share.service.FileService
import com.agilemaster.share.service.JdbcPage
import com.agilemaster.share.service.JpaShareService

@Controller
@RequestMapping("/test")
class TestController {
	
	@Autowired
	FileService fileService;
	@Autowired
	StorageLocationRepository  storageLocationRepository;
	@Autowired
	JpaShareService japShareService
	
	
	@RequestMapping("/filePage")
	def filePage(HttpServletRequest request){
		//SecurityContextHolder.getContextHolderStrategy().getContext().
		//SessionRegistry.getAllPrincipals()
		return "test/filePage"
	}
	@ResponseBody
	@RequestMapping("/uploadFile")
	def uploadFile(MultipartFile file,HttpServletRequest request){
		def result = fileService.saveFile(file, "/test");
	}
	
	/**
	 * 简单分页
	 * @param page
	 * @param size
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryPage/{page}/{size}")
	def queryPage(@PathVariable("page")  int page,@PathVariable("size")  int size){
		Page<StorageLocation> result = storageLocationRepository.findAll(new PageRequest(page, size));
		return result
	}
	
	/**
	 * 复杂分页查询示例
	 * @param max
	 * @param offset
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/hqlPage/{max}/{offset}")
	def hqlPage(@PathVariable("max")  int max,@PathVariable("offset")  int offset){
		def hql="from StorageLocation order by id ";
		def countHql="select count(*) from StorageLocation  ";
		JdbcPage result = japShareService.queryForHql(hql, countHql, max, offset, null);
		return result
	}
	/**
	 * 带参数的分页
	 * @param max
	 * @param offset
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/hqlQueryPage/{max}/{offset}")
	def hqlQueryPage(@PathVariable("max")  int max,@PathVariable("offset")  int offset){
		def hql="from StorageLocation where id>:id and address like:likeParam  order by id ";
		def countHql="select count(*) from StorageLocation where id>:id and address like:likeParam   ";
		def map= [id:2l,likeParam:"%%"]//HashMap
		JdbcPage result = japShareService.queryForHql(hql, countHql, max, offset, map);
		return result
	}
	

}
