package com.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.itcast.pojo.Country;
import cn.itcast.pojo.Province;

// @Configuration
public class CommonConfig {
	//注入Country对象
    //如果配置文件中配置了指定信息才注入，否则不注入
    @ConditionalOnProperty(prefix = "country",name = {"name","system"})
	@Bean
	public Country country(@Value("${country.name}") String name, @Value("${country.system}") String system){
        Country country = new Country();
        country.setName(name);
        country.setSystem(system);
		return country;
	}

    //注入Province对象
    //@Bean("aa")
    //如果方法内部需要使用到ioc容器中已存在的bean对象，那么只需要在方法上参数中声明即可，spring会自动注入
    // @Bean
    // public Province province(Country country){
    //     System.out.println("province: " + country);
    //     return new Province();
    // }
    //如果容器中不存在Country，则注入Provice，否则不注入
    @Bean
    // @ConditionalOnMissingBean(Country.class)
    // 如果当前环境中存在DispatcherServlet类，则注入Province，否则不注入
    // 如果当前环境引入了web起步依赖，则环境中有DispatcherServlet，否则没有
    @ConditionalOnClass(name = "org.springframework.web.servlet.DispatcherServlet")
    public Province province(){
        return new Province();
    }
}
