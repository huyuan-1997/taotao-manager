package com.taotao.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.utils.FtpUtil;
import com.taotao.common.utils.IDUtils;
import com.taotao.service.PictureService;

/**
 * 功能：图片上传
 * 
 * @author 胡园
 *
 */
@Service
public class PictureServiceImpl implements PictureService {
	@Value("${FTP_ADDRESS}")
	private String FTP_ADDRESS;// ftp服务器地址
	@Value("${FTP_PORT}")
	private Integer FTP_PORT;// ftp服务器端口
	@Value("${FTP_USERNAME}")
	private String FTP_USERNAME;// ftp用户名
	@Value("${FTP_PASSWORD}")
	private String FTP_PASSWORD;// ftp密码
	@Value("${FTP_BASE_PATH}")
	private String FTP_BASE_PATH;// 图片存放的基本地址
	@Value("${IMAGE_BASE_URL}")
	private String IMAGE_BASE_URL;// 图片服务器提供的基本HTTP地址

	@Override
	public Map uploadPicture(MultipartFile uploadFile) {
		// TODO Auto-generated method stub
		Map resultMap = new HashMap<>();
		try {
			// 取原始文件名就例如90.jpg
			String oldName = uploadFile.getOriginalFilename();
			// 生成新的文件名
			String newName = IDUtils.genImageName();
			// String.substring(index)从index开始截取，获得一个字符串
			newName = newName + oldName.substring(oldName.lastIndexOf("."));
			// 图片上传
			String imagePath = new DateTime().toString("/yyyy/MM/dd");
			boolean result = FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD, FTP_BASE_PATH,
					imagePath, newName, uploadFile.getInputStream());
			// 返回结果
			if (!result) {
				resultMap.put("error", 1);
				resultMap.put("message", "文件上传失败");
				return resultMap;
			}
			//图片上传成功
			resultMap.put("error", 0);
			//http中指定了/home/ftpuser
			resultMap.put("url", IMAGE_BASE_URL + imagePath + "/" + newName);
			return resultMap;
		} catch (Exception e) {
			resultMap.put("error", 1);
			resultMap.put("message", "文件上传发生异常");
			return resultMap;
		}
	}

}
