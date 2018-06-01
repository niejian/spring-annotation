package cn.com.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import cn.com.spring.bean.Car;
import cn.com.spring.bean.Person;

/**
 * 
 * @author niejian
 * bean的初始化
 * scope = "singleton", 默认单实例。容器启动的时候就创建
 * scope = "prototype", 多实例
 *
 */
//@PropertySource读取外部环境的配置文件
@PropertySource("classpath:/person.properties")
@Configuration
public class MyConfig {

	
	@Bean(value="person")
	public Person PersonBean() {
		return new Person();
	}
	
	//
	@Scope("prototype")
	@Bean(value="car", initMethod="init", destroyMethod="destory")
	public Car getCar() {
		return new Car();
	}
}
