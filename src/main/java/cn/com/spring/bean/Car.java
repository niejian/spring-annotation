package cn.com.spring.bean;

import javax.annotation.PostConstruct;

public class Car {
	
	public Car() {
		System.out.println("��ʼ����������");
	}
	
	public void init() {
		
		System.out.println("car init.........");
	}
	
	public void destory() {
		System.out.println("car destory........");
	}
	
	/**
	 * @PostConstruct beanʵ���������
	 */
	@PostConstruct
	public void postConstor() {
		System.out.println("car @PostConstruct......");
	}

}
