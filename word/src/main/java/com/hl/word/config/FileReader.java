package com.hl.word.config;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author ws
 * @date 2018/12/29 11:23
 */
@Component
public class FileReader implements ApplicationContextAware {

	/**************************************************方式一**********************************************/
	private String classpath = "classpath:config/file/";

	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}

	public String getFileContent(String file) throws IOException {
		Resource resource = context.getResource(classpath+file);
		return IOUtils.toString(resource.getInputStream(),"UTF-8");
	}

	public String getFileContent(String base,String file) throws IOException {
		Resource resource = context.getResource(base+file);
		return IOUtils.toString(resource.getInputStream(),"UTF-8");
	}

	public static ApplicationContext getApplicationContext() {
		return context;
	}


	/**************************************************方式2**********************************************/
	@Bean
	public ResourceLoader createResourceLoader() {
		return new DefaultResourceLoader();
	}

	/**************************************************方式3**********************************************/
	public void getFile() throws FileNotFoundException {
		File file = ResourceUtils.getFile("classpath:config/file/postman.json");
	}
}
