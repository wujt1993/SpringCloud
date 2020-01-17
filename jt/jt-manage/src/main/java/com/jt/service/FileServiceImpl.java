package com.jt.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jt.vo.ImageVO;

@Service
@PropertySource("classpath:/properties/image.properties")
//@ConfigurationProperties(prefix = "image")  依赖get/set方法
public class FileServiceImpl implements FileService {
	//定义本地磁盘路径
	@Value("${image.localDirPath}")
	private String localDirPath;
	//定义虚拟路径名称
	@Value("${image.urlPath}")
	private String urlPath;
	@Override
	public ImageVO updateFile(MultipartFile uploadFile) {
		ImageVO imageVO = new ImageVO();
		String fileName = (uploadFile.getOriginalFilename()).toLowerCase();
		if(!fileName.matches("^.+\\.(jpg|png|gif)$")) {
			imageVO.setError(1); //表示上传有无
			return imageVO;
		}
		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(uploadFile.getInputStream());
			int width = bufferedImage.getWidth();
			int height = bufferedImage.getHeight();
			if(width==0 || height ==0) {
				imageVO.setError(1);
				return imageVO;
			}
			String dateDir = 
					new SimpleDateFormat("yyyy/MM/dd")
					.format(new Date());
			
			//5.准备文件夹  D:/1-jt/image/yyyy/MM/dd
			String localDir = localDirPath + dateDir;
			File dirFile = new File(localDir);
			if(!dirFile.exists()) {
				//如果文件不存在,则创建文件夹
				dirFile.mkdirs();
			}
			//b8a7ff05-8356-11e9-9997-e0d55e0fcfd8
			//6.使用UUID定义文件名称 uuid.jpg
			String uuid = 
			UUID.randomUUID().toString().replace("-","");
			//图片类型 a.jpg 动态获取 ".jpg"
			String fileType = 
			fileName.substring(fileName.lastIndexOf("."));
			
			//拼接新的文件名称
			//D:/1-jt/image/yyyy/MM/dd/文件名称.类型
			String realLocalPath = localDir+"/"+uuid+fileType;
			String realUrlPath = urlPath+dateDir+"/"+uuid+fileType;
			//7.2完成文件上传
			uploadFile.transferTo(new File(realLocalPath));
			
			//将文件文件信息回传给页面
			imageVO.setError(0)
				   .setHeight(height)
				   .setWidth(width)
				   .setUrl(realUrlPath);
		} catch (IOException e) {
			e.printStackTrace();
			imageVO.setError(1);
			return imageVO;
		}
			
		return imageVO;
	}

}
