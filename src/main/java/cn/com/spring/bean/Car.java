package cn.com.spring.bean;

import javax.annotation.PostConstruct;

public class Car {
	
	public Car() {
		System.out.println("初始化。。。。");
	}
	
	public void init() {
		
		System.out.println("car init.........");
	}
	
	public void destory() {
		System.out.println("car destory........");
	}
	
	/**
	 * @PostConstruct bean实例化后调用
	 */
	@PostConstruct
	public void postConstor() {
		System.out.println("car @PostConstruct......");
	}

}
