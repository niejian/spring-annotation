package cn.com.spring.test;

import cn.com.spring.bean.OaNoCode;
import cn.com.spring.config.RedisConfig;
import cn.com.spring.service.OaCodeService;
import cn.com.spring.units.BloomFilterUtils;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;

public class BloomTest {
	
	private AnnotationConfigApplicationContext annotationContext;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private OaCodeService oaCodeService;

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
		boBloomFilter.put("a");
//		System.out.println(boBloomFilter.mightContain(""));
//		annotationContext = new AnnotationConfigApplicationContext(RedisConfig.class);
//		RedisPropertiesBean bean = annotationContext.getBean(RedisPropertiesBean.class);
//		System.out.println(bean);
		
		
	}

	@Ignore
	@Test
	public void test02(){
		annotationContext = new AnnotationConfigApplicationContext(RedisConfig.class);
		RedisTemplate redisTemplate = annotationContext.getBean(RedisTemplate.class);
		RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
		System.out.println(valueSerializer);
		redisTemplate.opsForValue().set("test", "test");
		String o = (String) redisTemplate.opsForValue().get("test");
		System.out.println(o);
//		logger.info("================");
//		logger.info("直接通过dao调用");
//        OaNoCodeDao oaNoCodeDao = annotationContext.getBean(OaNoCodeDao.class);
//        OaNoCode oaNoCode = oaNoCodeDao.getByCodeid("2426");
//        System.out.println(oaNoCode);
//        logger.info(">>>>>>>>>>>>>>>>");
//        logger.info("通过service调用");
//        oaCodeService = annotationContext.getBean(OaCodeService.class);
//        OaNoCode oaNoCode1 = oaCodeService.getOaCodeById("2426");
//        System.out.println(oaNoCode1);
//
//        logger.info(">>>>>>>>>>>>>>>>");
//        logger.info(">>>>>>>>>>>>>>>>");
//        logger.info(">>>>>>>>>>>>>>>>");
//        List<OaNoCode> list = oaNoCodeDao.getAllByCodeidAfter("0");
//        System.out.println(list.toString());
        annotationContext.close();
	}

	@Ignore
	@Test
    public void test03(){
        RedisTemplate redisTemplate = annotationContext.getBean(RedisTemplate.class);

        OaCodeService oaCodeService = annotationContext.getBean(OaCodeService.class);
        OaNoCode oaNoCode1 = oaCodeService.getOaCodeById("2426");
        System.out.println(oaNoCode1);
		OaNoCode oaNoCode = oaCodeService.getOaCodeById("1");

		String string = new String();
	}

    @PostConstruct
    public void init(){
        oaCodeService = annotationContext.getBean(OaCodeService.class);

    }
}
