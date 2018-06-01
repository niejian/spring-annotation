package cn.com.spring.test;

import java.nio.charset.Charset;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import cn.com.spring.bean.RedisPropertiesBean;
import cn.com.spring.config.RedisConfig;
import cn.com.spring.units.BloomFilterUtils;
import jdk.nashorn.internal.ir.annotations.Ignore;

public class BloomTest {
	
	private AnnotationConfigApplicationContext annotationContext;

	@Ignore
	@Test
	public void  test01() {
		//创建一个布隆过滤器, 1万个hash函数，容错率是千分之一
		
		BloomFilter<String> boBloomFilter = BloomFilterUtils.getInstance();
		BloomFilter<String> boBloomFilter2 = BloomFilterUtils.getInstance();
		BloomFilter<String> boBloomFilter3 = BloomFilter.create(Funnels.stringFunnel(Charset.forName("utf-8")), (long) 10000, 0.0001);
		System.out.println(boBloomFilter);
		System.out.println(boBloomFilter2);
		System.out.println(boBloomFilter3);
		boBloomFilter.put("a");
		boBloomFilter.put("a1");
		boBloomFilter.put("2a");
		boBloomFilter.put("a2");
		boBloomFilter.put("a4");
//		System.out.println(boBloomFilter.mightContain(""));
//		annotationContext = new AnnotationConfigApplicationContext(RedisConfig.class);
//		RedisPropertiesBean bean = annotationContext.getBean(RedisPropertiesBean.class);
//		System.out.println(bean);
		
		
	}
}
