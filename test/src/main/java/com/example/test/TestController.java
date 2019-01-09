package com.example.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ws
 * @date 2019/1/7 17:34
 */
@RestController
public class TestController {


	@RequestMapping("hello")
	public String hello(){
		return "hello world!";
	}


}
