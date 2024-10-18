package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import com.example.anno.EnableCommonConfig;
import com.example.config.CommonConfig;
import com.example.config.CommonImportSelector;

import cn.itcast.pojo.Country;

//启动类
@SpringBootApplication
// @Import({CommonConfig.class})
// @Import(CommonImportSelector.class)
@EnableCommonConfig
public class DemoApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(DemoApplication.class, args);

		Country country = context.getBean(Country.class);
		System.out.println(country);

		
		System.out.println(context.getBean("province"));
	}



}
