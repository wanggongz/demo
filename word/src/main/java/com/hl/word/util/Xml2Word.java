package com.hl.word.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.util.Map;

/**
 * @author ws
 * @date 2018/12/29 10:21
 */
public class Xml2Word {

	/**
	 * 将xml模板转换为后缀为doc文件，本质仍是属于xml
	 * @param dataMap	需要填充到模板的数据
	 * @param templetFilePath	模板文件路径
	 * @param targetFilePath	目标文件保存路径
	 * @throws IOException
	 * @throws TemplateException
	 */
	public static void xml2XmlDoc(Object dataMap, String templetFilePath, String targetFilePath) throws IOException, TemplateException {
		// 将模板文件路径拆分为文件夹路径和文件名称
		String tempLetDir = templetFilePath.substring(0,templetFilePath.lastIndexOf("/"));
		// 注意：templetFilePath.lastIndexOf("/")中，有的文件分隔符为：\ 要注意文件路径的分隔符
		String templetName = templetFilePath.substring(templetFilePath.lastIndexOf("/")+1);
		// 将目标文件保存路径拆分为文件夹路径和文件名称
		String targetDir = targetFilePath.substring(0,targetFilePath.lastIndexOf("/"));
		String targetName = targetFilePath.substring(targetFilePath.lastIndexOf("/")+1);
		Configuration configuration = new Configuration();
		configuration.setDefaultEncoding("UTF-8");
		// 如果目标文件目录不存在，则需要创建
		File file = new File(targetDir);
		if(!file.exists()){
			file.mkdirs();
		}
		// 加载模板数据（从文件路径中获取文件，其他方式，可百度查找）
		configuration.setDirectoryForTemplateLoading(new File(tempLetDir));
		// 获取模板实例
		Template template = configuration.getTemplate(templetName);
		File outFile = new File(targetDir + File.separator + targetName);
		//将模板和数据模型合并生成文件
		Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"UTF-8"));
		//生成文件
		template.process(dataMap, out);
		out.flush();
		out.close();
	}

	/**
	 * 将图片转换成Base64编码 (优化：在web工程下，可通过工程路径获取流的方式获取图片)
	 * @param imgFilePath	图片路径
	 * @return
	 * @throws IOException
	 */
	public static String getImgStr(String imgFilePath) throws IOException {
		//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		InputStream in = null;
		byte[] data = null;
		//读取图片字节数组
		in = new FileInputStream(imgFilePath);
		data = new byte[in.available()];
		in.read(data);
		in.close();
		return new String(Base64.encodeBase64(data));
	}

}
