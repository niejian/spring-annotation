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
 * bean�ĳ�ʼ��
 * scope = "singleton", Ĭ�ϵ�ʵ��������������ʱ��ʹ���
 * scope = "prototype", ��ʵ��
 *
 */
//@PropertySource��ȡ�ⲿ�����������ļ�
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
