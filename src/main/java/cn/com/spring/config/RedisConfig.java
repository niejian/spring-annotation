package cn.com.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import cn.com.spring.bean.RedisPropertiesBean;

@PropertySource("classpath:/redis.properties")
@Configuration
public class RedisConfig {
	// 将redis的配置全部以类属性的方式装配进来
	@Value("${redis.port}")
	private Integer port;
	@Value("${redis.host}")
	private String host;
	@Value("${redis.pass}")
	private String password;
	@Value("${redis.default.db}")
	private Integer db;
	@Value("${redis.timeout}")
	private Integer timeout;
	@Value("${redis.maxIdle}")
	private Integer maxIdle;
	@Value("${redis.maxActive}")
	private Integer maxActive;
	@Value("${redis.maxWait}")
	private Integer maxWait;
	@Value("${redis.testOnBorrow}")
	private boolean testOnBorrow;
	
	//注册redisFactory`	
//	public JedisConnectionFactory jedisConnectionFactory() {
//		
//	}

//	@Bean
//	public RedisPropertiesBean redisPropertiesBean() {
//
//		return new RedisPropertiesBean();
//	}

	// public RedisTemplate redisTemplate() {
	// JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
	// RedisTemplate redisTemplate = jedisConnectionFactory.getConnection().
	// return new RedisTemplate<>();
	// }

}
