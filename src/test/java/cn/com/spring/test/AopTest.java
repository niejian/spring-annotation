package cn.com.spring.test;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import cn.com.spring.aop.MathClac;
import cn.com.spring.config.MainConfigAop;

public class AopTest {
	
	private AnnotationConfigApplicationContext annotationContext;

	@Test
	public void test1() {
		
		annotationContext = new AnnotationConfigApplicationContext(MainConfigAop.class);
		MathClac mathClac = annotationContext.getBean(MathClac.class);
		mathClac.div(1, 1);
	}

}
