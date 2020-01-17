package com.jt.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jt.service.FileService;
import com.jt.vo.ImageVO;

@Controller
public class FileController {
	
	@Autowired
	private FileService fileService;

	@RequestMapping("/file")
	public String file(MultipartFile fileImage) throws IllegalStateException, IOException {
		String originalFilename = fileImage.getOriginalFilename();
		File fileDir = new File("D:/java/image"); 
		if(!fileDir.exists()) {
			fileDir.mkdirs();
		}
		fileImage.transferTo(new File(fileDir+ "/" +originalFilename));
		return "redirect:/file.jsp";
	}
	
	//实现文件上传
	@RequestMapping("/pic/upload")
	@ResponseBody
	public ImageVO uploadFile(MultipartFile uploadFile) {
		
		return fileService.updateFile(uploadFile);
	}
}
