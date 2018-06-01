package cn.com.spring.bean;

import org.springframework.beans.factory.annotation.Value;

public class Person {
	private String userName;
	private Integer age;
	public String getUserName() {
		return userName;
	}
	//获取外部文件中的值
	@Value("${person.name}")
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Integer getAge() {
		return age;
	}
	@Value("${person.age}")
	public void setAge(Integer age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "Person [userName=" + userName + ", age=" + age + "]";
	}
	
	

	
}
