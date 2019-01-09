package com.hl.word.util;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @author ws
 * @date 2019/1/7 16:53
 */
public class UpAndDownUtil {

	public static void down(HttpServletResponse response, File file) throws Exception {
		// 配置文件下载
		response.setHeader("content-type", "application/octet-stream");
		response.setContentType("application/octet-stream");
		// 下载文件能正常显示中文
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
		// 实现文件下载
		byte[] buffer = new byte[1024];
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		try {
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			OutputStream os = response.getOutputStream();
			int i = bis.read(buffer);
			while (i != -1) {
				os.write(buffer, 0, i);
				i = bis.read(buffer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
