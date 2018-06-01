package cn.com.spring.test;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import cn.com.spring.bean.Car;
import cn.com.spring.bean.Person;
import cn.com.spring.config.MyConfig;
import net.oschina.htmlsucker.HtmlSucker;

public class SpringAnnotationTest {
	
	//需要將對應的加上@Configuration的註解作為參數添加到AnnotationConfigApplicationContext構造函數中
	AnnotationConfigApplicationContext annotationContext = null;
	
	@Ignore
	@Test
	public void test1() {
		annotationContext = new  AnnotationConfigApplicationContext(MyConfig.class);
		Person p = (Person) annotationContext.getBean("person");
		System.out.println(p);
	}
	
	@Ignore
	@Test
	public void test02() {
		annotationContext = new  AnnotationConfigApplicationContext(MyConfig.class);
		Car car = (Car) annotationContext.getBean("car");
		
	}
	
	@Test
	public void test03() throws IOException {
		
		String url = "https://gitee.com/ld/HtmlSucker";
		System.out.println(HtmlSucker.select(HtmlSucker.TEXT_DENSITY_EXTRACTOR).parse(url, 20000));
	}
}
