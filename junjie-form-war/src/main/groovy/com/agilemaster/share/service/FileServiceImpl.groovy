package com.agilemaster.share.service
import javax.annotation.PostConstruct
import javax.servlet.ServletContext

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

import com.agilemaster.findoil.domain.FileInfo
import com.agilemaster.findoil.repository.FileInfoRepository
import com.agilemaster.findoil.service.UserService

@Service
class FileServiceImpl implements FileService{
	private static final Logger log = LoggerFactory
	.getLogger(FileServiceImpl.class);
	@Autowired
	ServletContext context;
	@Autowired
	FileInfoRepository fileInfoRepository;
	@Autowired
	JpaShareService japShareService;
	@Autowired
	UserService userService;
	
	private String rootPath = "";
	@PostConstruct
	public void init(){
		Resource resource = new ClassPathResource("application.properties");
		rootPath=resource.getFile().getParentFile().getAbsolutePath()+"/files/upload";
		log.info("upload file store root path:--->>>>>>>>>>>>>>>\n"+rootPath)
	}
	@Override
	public FileInfo saveFile(MultipartFile file,String path) {
	//	WebApplicationContextUtils.
		FileInfo fileInfo = null;
		if(!file.isEmpty()){
			fileInfo =new FileInfo()
			log.info("  path->"+path)
			log.info(" rootPath->"+rootPath)
			def user = userService.currentUser()
			fileInfo.setAuthor(user)
			def filePath = rootPath+(path.startsWith("/")?path:("/"+path))
			log.info("file storePath"+filePath)
			def fileName = file.getOriginalFilename()
			fileInfo.setOriginalName(fileName)
			fileInfo.setFileSize(file.getSize())
			// unique the file name
			int dotPos = fileName.lastIndexOf(".")
			def extension = (dotPos) ? fileName[dotPos + 1..fileName.length() - 1] : ""
			fileName = System.currentTimeMillis()+"." + extension
			def newFile = new File(filePath, fileName)
			fileInfo.setStorePath(path+"/"+fileName)
			def dir = new File(filePath)
			if (!dir.exists()) {
				try {
					dir.mkdirs()
				} catch (SecurityException se) {
					log.error("Creating Folders in: ${path} was failed")
					log.error(se.message)
					return null
				}
			}
			def freeSpace = dir.getUsableSpace()
			if (file.size > freeSpace) {
				log.error("File size exceeds the usable size: ${freeSpace.toString()}")
				return null
			}
			if (!dir.canWrite()) {
				// no, try to make it writable
				if (!dir.setWritable(true)) {
					log.error("The Folder ${filePath} is not writable, and can not set to writable!")
					return null
				}
			}
			file.transferTo(newFile)
			fileInfo = fileInfoRepository.save(fileInfo)
		}
		return fileInfo
	}
	@Override
	public void delFile(FileInfo fileInfo) {
		if(fileInfo){
			String path = fileInfo.getStorePath()
			def filePath = rootPath+(path.startsWith("/")?path:("/"+path))
			File file = new File(filePath);
			log.info("del file-->"+file.getAbsolutePath())
			file.deleteOnExit()
			japShareService.delete(fileInfo)
		}
		
	}
	@Override
	public void delFile(Long fileInfoId) {
		delFile(fileInfoRepository.getOne(fileInfoId))
	}
	
}
