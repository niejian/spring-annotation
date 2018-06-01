package cn.com.spring.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LogAspects {
	
	@Pointcut("execution(public * cn.com.spring.aop.MathClac.*(..))")
	public void pointCut() {
		
	}

	@Before(value = "pointCut()")
	public void logStatrt(JoinPoint joinPoint) {
		System.out.println(joinPoint.getSignature().getName() + "除法运行开始。。。。参数{"+Arrays.asList(joinPoint.getArgs())+"}");
	}
	
	@After("pointCut()")
	public void logEnd(JoinPoint joinPoint) {
		System.out.println(joinPoint.getSignature().getName() + "除法运行结束。。。。参数{}");
	}
	
	//获取返回结果
	@AfterReturning(value = "pointCut()", returning="result")
	public void logReturn(JoinPoint joinPoint, Object result) {
		System.out.println(joinPoint.getSignature().getName() + "除法正常返回。。。返回结果：" + result);
	}
	
	//获取跑出异常
	@AfterThrowing(value="pointCut()", throwing="exception")
	public void logExcetion(JoinPoint joinPoint, Exception exception
			) {
		System.out.println(joinPoint.getSignature().getName() + "除法异常 返回。。。。异常信息：" + exception.getMessage());
	}
	
	//@Around(value="pointCut()")
	public void aroundLog(JoinPoint joinPoint) {
		System.out.println(joinPoint.getSignature().getName()+ ":切面环绕通知");
		
	}
}
