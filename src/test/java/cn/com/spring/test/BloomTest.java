package cn.com.spring.test;

import cn.com.spring.bean.OaNoCode;
import cn.com.spring.config.RedisConfig;
import cn.com.spring.service.OaCodeService;
import cn.com.spring.units.BloomFilterUtils;
import com.alibaba.fastjson.JSONObject;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BloomTest {
	
	private AnnotationConfigApplicationContext annotationContext = new AnnotationConfigApplicationContext(RedisConfig.class);
    //RedisTemplate redisTemplate = annotationContext.getBean(RedisTemplate.class);
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //private OaCodeService oaCodeService;
    private static final int THREAD_NUM = 20;
    //初始化一个计数器
    private final CountDownLatch countDownLatch = new CountDownLatch(THREAD_NUM);
    private static final String KEY_PREFIX = "OA_CODE_";

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
		HashMap<Object, Object> hashMap = new HashMap<>();
		hashMap.put("a", "a");
		hashMap.put("b", "a");
		hashMap.put("c", "a");
		hashMap.put("d", "a");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("a", "a");
		jsonObject.put("a1", "a1");
		jsonObject.put("a2", "a2");
		jsonObject.put("a3", "a");
		jsonObject.put("a4", "a");
		jsonObject.put("a5", "a");
		redisTemplate.opsForValue().set("map", jsonObject.toString());
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
//		annotationContext = new AnnotationConfigApplicationContext(RedisConfig.class);
//        RedisTemplate redisTemplate = annotationContext.getBean(RedisTemplate.class);
//
//        OaCodeService oaCodeService = annotationContext.getBean(OaCodeService.class);
//        OaNoCode oaNoCode1 = oaCodeService.getOaCodeById("14");
//        System.out.println(oaNoCode1);
//		OaNoCode oaNoCode = oaCodeService.getOaCodeById("1");
//
//		String string = new String();

//        for (int i = 0; i < THREAD_NUM; i++) {
//            new Thread(new MyThread()).start();
//            countDownLatch.countDown();
//
//            try {
//                Thread.currentThread().join();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        RedisTemplate redisTemplate = annotationContext.getBean(RedisTemplate.class);

//
        OaCodeService oaCodeService = annotationContext.getBean(OaCodeService.class);
        OaNoCode oaNoCode = oaCodeService.getOaCodeById("1");
        System.out.println(oaNoCode.toString());
        System.out.println(redisTemplate.opsForValue().get("map"));
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_NUM * 2);
        for (int i = 0; i < THREAD_NUM; i++) {
            executorService.execute(new MyThread(redisTemplate, oaCodeService));
            countDownLatch.countDown();
        }
	}

//    @PostConstruct
//    public void init(){
//        annotationContext = new AnnotationConfigApplicationContext(RedisConfig.class);
//        oaCodeService = annotationContext.getBean(OaCodeService.class);
//
//    }

    class MyThread implements Runnable {
	    private RedisTemplate redisTemplate;
	    private OaCodeService oaCodeService;

        //AnnotationConfigApplicationContext annotationContext = new AnnotationConfigApplicationContext(RedisConfig.class);
        public MyThread(RedisTemplate redisTemplate, OaCodeService oaCodeService) {
            this.redisTemplate = redisTemplate;
            this.oaCodeService = oaCodeService;
        }
        @Override
        public void run() {

            try {
                ////所有子线程等待，当子线程全部创建完成再一起并发执行后面的代码
                //System.out.println("count计数：" + countDownLatch.getCount());
                countDownLatch.await();


                //1.先不用布隆过滤器，直接查询缓存，如果缓存没有再查询数据库并将该信息存放到数据库中
                String id = "2";
                String redisKey = KEY_PREFIX + id;


                //RedisSerializer valueSerializer = redisTemplate.getValueSerializer();

                //logger.info("1------------从redis缓存中获取该数据信息------------1");

                Object value = null;
                String o = null;
                //synchronized (countDownLatch){
//                String o = null;
//               // System.out.println("《《《《《《《《《《《《《《《《缓存查询结果：" + redisTemplate);
//                o = (String) redisTemplate.opsForValue().get("test");
//                System.out.println("《《《《《《《《《《《《《《《《缓存查询结果：" + o);
                value = redisTemplate.opsForValue().get(redisKey);
//                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>线程全部结束等待，开始执行各自逻辑：" + redisKey);


                if (null != value) {
                    //System.out.println(">>>>>>>>>>>>>" + 0);
                    logger.info("------------从redis缓存中查到对应信息{}------------", (String) value);
                }
               // System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>线程全部结束等待，开始执行各自逻辑：" + redisKey);
                if (null == value) {
                    //logger.info("++++++++++++redis中没有该数据，从数据库中查询++++++++++++");

                    //oaCodeService = annotationContext.getBean(OaCodeService.class);
                    OaNoCode oaNoCode = this.oaCodeService.getOaCodeById(id);
                    //System.out.println(oaNoCode.toString());

                    if (null != oaNoCode) {
                        logger.info("执行sql结果：" + oaNoCode);
                        //o = JSONObject.toJSONString(oaNoCode);
                        //logger.info("++++++++++++redis中没有该数据，从数据库中查询结果{}++++++++++++", o);
                        redisTemplate.opsForValue().set(redisKey, oaNoCode.toString());
                    }


                }
                //}
                //}
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
