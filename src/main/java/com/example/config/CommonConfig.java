package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.itcast.pojo.Country;
import cn.itcast.pojo.Province;

// @Configuration
public class CommonConfig {
	//注入Country对象
	@Bean
	public Country country(){
		return new Country();
	}

    //注入Province对象
    //@Bean("aa")
    //如果方法内部需要使用到ioc容器中已存在的bean对象，那么只需要在方法上参数中声明即可，spring会自动注入
    @Bean
    public Province province(Country country){
        System.out.println("province: " + country);
        return new Province();
    }
}
