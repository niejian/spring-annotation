package cn.com.spring.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import redis.clients.jedis.JedisPoolConfig;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.HashSet;


@PropertySource(value = {
        "classpath:/redis.properties",
        "classpath:/jdbc.properties",
})
@Configuration
//配置JPA要把哪些路径下面的包加载到ioc容器中
@EnableJpaRepositories(basePackages = {
        "cn.com.spring.Dao"
})
@ComponentScan(basePackages = {
        "cn.com.spring.service"
})
public class RedisConfig {
	// 将redis的配置全部以类属性的方式装配进来
	@Value("${redis.port0}")
	private Integer port0;
    @Value("${redis.port1}")
    private Integer port1;
    @Value("${redis.port2}")
    private Integer port2;
    @Value("${redis.port3}")
    private Integer port3;
    @Value("${redis.port4}")
    private Integer port4;
    @Value("${redis.port5}")
    private Integer port5;

    @Value("${sentinel.port1}")
    private Integer sentinelPort1;
    @Value("${sentinel.port2}")
    private Integer sentinelPort2;




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

	@Value("${jdbc.driver}")
	private String driveClass;
    @Value("${jdbc.url}")
	private String url;
    @Value("${jdbc.username}")
	private String dnUserName;
    @Value("${jdbc.password}")
	private String dbPassword;

	
	//注册redisFactory`
	@Bean
	public JedisConnectionFactory jedisConnectionFactory(RedisSentinelConfiguration redisSentinelConfiguration) {
		//RedisStandaloneConfiguration
		//设置连接信息--单机
//		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
//		configuration.setDatabase(db);
//		configuration.setHostName(host);
//		configuration.setPort(port);
//		configuration.setPassword(RedisPassword.of(password));
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxTotal(maxActive);
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        jedisPoolConfig.setMaxWaitMillis(maxWait);


		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisSentinelConfiguration, jedisPoolConfig);
        //设置连接池
		//jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
		return jedisConnectionFactory;
	}



	@Bean
    public RedisTemplate<String, String> redisTemplate(JedisConnectionFactory jedisConnectionFactory){
        RedisTemplate<String, String> redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
//
//        redisTemplate.setConnectionFactory(jedisConnectionFactory());
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
//        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    //注册JDBC
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DruidDataSource dataSource){
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setShowSql(true);
        jpaVendorAdapter.setGenerateDdl(false);
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactoryBean.setDataSource(dataSource);
        //添加扫描包路径
        entityManagerFactoryBean.setPackagesToScan("cn.com.spring.Dao", "cn.com.spring.service", "cn.com.spring.bean");
        return entityManagerFactoryBean;
    }

    @Bean
    public DruidDataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(dnUserName);
        dataSource.setMaxActive(2000);
        dataSource.setPassword(dbPassword);
        dataSource.setKeepAlive(true);

        return dataSource;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory ){
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        return jpaTransactionManager;
    }

    //redis集群，redis的是使用集群模式做的集群
    @Bean
    public RedisClusterConfiguration redisClusterConfiguration(){
        ArrayList<String> clusterNodes = new ArrayList<>();
        clusterNodes.add(host + ":" + port0);
        clusterNodes.add(host + ":" + port1);
        clusterNodes.add(host + ":" + port2);
        clusterNodes.add(host + ":" + port3);
        clusterNodes.add(host + ":" + port4);
        clusterNodes.add(host + ":" + port5);
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(clusterNodes);
        return redisClusterConfiguration;


    }

    @Bean
    public RedisSentinelConfiguration redisSentinelConfiguration() {
        //ArrayList<String> clusterNodes = new ArrayList<>();
        //ArrayList<String> clusterNodes = new ArrayList<>();
        HashSet<String> clusterNodes = new HashSet<>();
        clusterNodes.add(host + ":" + sentinelPort1);
        clusterNodes.add(host + ":" + sentinelPort2);

        String master = host + ":" + port2;
        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration("mymaster", clusterNodes);
        return redisSentinelConfiguration;

    }




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
