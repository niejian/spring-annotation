package cn.com.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import cn.com.spring.aop.LogAspects;
import cn.com.spring.aop.MathClac;

/**
 * 
 * @author niejian
 * aop测试
 * 定义一个日志切面类（logAspects）
 * joinPoint.getSignature().getName() 获取方法名
 * joinPoint只能在参数列表的第一位
 * 	通知方法：
 * 		前置通知：（@Before）
 * 		后置通知：方法结束就会调用，不管它是正常结束还是一样结束
 * 		环绕通知：
 * 		异常通知：
 * 		返回通知：
 *
 */
//开启切面注解
@EnableAspectJAutoProxy
@Configuration
public class MainConfigAop {

	@Bean
	public MathClac mathClac() {
		return new MathClac();
	}
	
	//将切面加载到容器中
	@Bean
	public LogAspects logAspects() {
		return new LogAspects();
	}
}
