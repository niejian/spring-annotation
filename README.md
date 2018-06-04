# spring-annotation
## Spring注解方式工作原理

### 背景

在使用了Springboot后，被其全注解的工作方式所吸引。所以就想使用全注解的方式来集成Spring。

### 准备工作

Spring的工作方式都知道，通过配置各种xml文件，然后在web.xml来加载这些xml使Spring容器工作。就比如配置数据库链接的xml文件。首先我们得指定相应的数据库连接池（DataSource）。在配置连接池的时候还得配置数据库的连接参数等（用户名，密码….）。连接池还得配置Spring帮你封装的数据连接工厂，配完连接工厂还得声明单个的数据库连接之后是声明事务切面….详细的配置文件如下，以mybatis为例

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:tx="http://www.springframework.org/schema/tx" xmlns:aop="http://www.springframework.org/schema/aop" xsi:schemaLocation="
	http://www.springframework.org/schema/beans 
	http://www.springframework.org/schema/beans/spring-beans-3.0.xsd 
	http://www.springframework.org/schema/tx 
	http://www.springframework.org/schema/tx/spring-tx-3.0.xsd
	http://www.springframework.org/schema/aop 
	http://www.springframework.org/schema/aop/spring-aop-3.0.xsd">

    <!-- JNDI方式配置数据源 -->
    <!-- <bean id="dataSource" class="org.springframework.jndi.JndiObjectFactoryBean"> <property name="jndiName" value="${jndiName}"></property> </bean> -->
    <!-- 配置数据源 -->
    <!-- 配置数据源，使用的是alibaba的Druid(德鲁伊)数据源 -->
    <bean name="dataSource" class="com.alibaba.druid.pool.DruidDataSource" init-method="init" destroy-method="close">
        <property name="url" value="${jdbc.url}" />
        <property name="username" value="${jdbc.username}" />
        <property name="password" value="${jdbc.password}" />
        <!-- 初始化连接大小 -->
        <property name="initialSize" value="0" />
        <!-- 连接池最大使用连接数量 -->
        <property name="maxActive" value="2000" />
        <!-- 连接池最大空闲 -->
        <property name="maxIdle" value="20" />
        <!-- 连接池最小空闲 -->
        <property name="minIdle" value="0" />
        <!-- 获取连接最大等待时间 -->
        <property name="maxWait" value="60000" />
        <!-- 
        <property name="poolPreparedStatements" value="true" /> 
        <property name="maxPoolPreparedStatementPerConnectionSize" value="33" /> 
        -->
        <property name="validationQuery" value="${validationQuery}" />
        <property name="testOnBorrow" value="false" />
        <property name="testOnReturn" value="false" />
        <property name="testWhileIdle" value="true" />
        <!-- 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 -->
        <property name="timeBetweenEvictionRunsMillis" value="60000" />
        <!-- 配置一个连接在池中最小生存的时间，单位是毫秒 -->
        <property name="minEvictableIdleTimeMillis" value="25200000" />
        <!-- 打开removeAbandoned功能 -->
        <property name="removeAbandoned" value="true" />
        <!-- 1800秒，也就是30分钟 -->
        <property name="removeAbandonedTimeout" value="1800" />
        <!-- 关闭abanded连接时输出错误日志 -->
        <property name="logAbandoned" value="true" />
        <!-- 监控数据库 -->
        <!-- <property name="filters" value="stat" /> -->
        <property name="filters" value="mergeStat" />
    </bean>
	<!-- 针对myBatis的配置项============================= -->
    <!-- 配置sqlSessionFactory 
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">-->
    <bean id="sqlSessionFactory" class="cn.com.bluemoon.timer.util.SqlSessionFactoryBeanUtil">
        <!-- 实例化sqlSessionFactory时需要使用上述配置好的数据源以及SQL映射文件 -->
        <property name="dataSource" ref="dataSource" />
        <!-- 自动扫描 目录下的所有SQL映射的xml文件, 省掉Configuration.xml里的手工配置
  <property name="configLocation" value="classpath:/mybatis-config.xml"></property>
        <property name="mapperLocations" value="classpath:/mappings/**/*.xml"/>
  <property name="mapperLocations" value="classpath:cn/com/bluemoon/common/dispatch/**/*Mapper.xml"/>
         -->

        <property name="mapperLocations">
            <list>
                <value>classpath:cn/com/bluemoon/common/**/**/*Mapper.xml</value>
            </list>
        </property>
    </bean>
    <!-- 配置扫描器 -->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <!-- 扫描cn.com.bluemoon.common.*.mapper以及它的子包下的所有映射接口类 -->
        <property name="basePackage" value="cn.com.bluemoon.common.*.mapper" />
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory" />
    </bean>

    <!-- 分隔线 -->
    <!-- 配置Spring的事务管理器 -->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource" />
    </bean>

    <!-- 注解方式配置事物 -->
    <!-- <tx:annotation-driven transaction-manager="transactionManager" /> -->

    <!-- 拦截器方式配置事物 -->
    <tx:advice id="transactionAdvice" transaction-manager="transactionManager">
        <tx:attributes>
            <tx:method name="add*" propagation="REQUIRED" />
            <tx:method name="append*" propagation="REQUIRED" />
            <tx:method name="insert*" propagation="REQUIRED" />
            <tx:method name="save*" propagation="REQUIRED" />
            <tx:method name="update*" propagation="REQUIRED" />
            <tx:method name="modify*" propagation="REQUIRED" />
            <tx:method name="edit*" propagation="REQUIRED" />
            <tx:method name="delete*" propagation="REQUIRED" />
            <tx:method name="remove*" propagation="REQUIRED" />
            <tx:method name="repair" propagation="REQUIRED" />
            <tx:method name="delAndRepair" propagation="REQUIRED" />

            <tx:method name="get*" propagation="SUPPORTS" />
            <tx:method name="find*" propagation="SUPPORTS" />
            <tx:method name="load*" propagation="SUPPORTS" />
            <tx:method name="search*" propagation="SUPPORTS" />
            <tx:method name="datagrid*" propagation="SUPPORTS" />

            <tx:method name="*" propagation="SUPPORTS" />
        </tx:attributes>
    </tx:advice>
    <aop:config>
        <aop:pointcut id="transactionPointcut" expression="execution(* cn.com.bluemoon.service..*Impl.*(..))" />
        <aop:advisor pointcut-ref="transactionPointcut" advice-ref="transactionAdvice" />
    </aop:config>

    <!-- 配置druid监控spring jdbc -->
    <bean id="druid-stat-interceptor" class="com.alibaba.druid.support.spring.stat.DruidStatInterceptor">
    </bean>
    <bean id="druid-stat-pointcut" class="org.springframework.aop.support.JdkRegexpMethodPointcut" scope="prototype">
        <property name="patterns">
            <list>
                <value>cn.com.bluemoon.service.*</value>
            </list>
        </property>
    </bean>
    <aop:config>
        <aop:advisor advice-ref="druid-stat-interceptor" pointcut-ref="druid-stat-pointcut" />
    </aop:config>
</beans>
```

同样的，使用注解方式的话，还是一样的套路。只不过这些配置信息都是以Java文件的方式来呈现。对应的Java配置信息如下。我是使用Spring+Jpa来做orm层分

```java
//指定配置文件的路径。方便使用@Value这个注解来注入对应的配置项
@PropertySource(value = {
        "classpath:/redis.properties",
        "classpath:/jdbc.properties",
})
//这个注解必须加上，表明该类是配置类，在容器启动的时候就会将这个类中的所用配置信息加载到Spring容器中
@Configuration
//配置JPA要把哪些路径下面的包加载到ioc容器中
//JPA注解，类似扫描的意思。将对应的包下面的类加载到容器里面。否则在使用@Autowid的时候会报注入失败分错误
@EnableJpaRepositories(basePackages = {
        "cn.com.spring.Dao"
})
//Spring的注解，将报名下使用@Service,@Controller注解的类加载到容器里面使之正常工作
@ComponentScan(basePackages = {
        "cn.com.spring.service"
})
//该类定义的这些属性都与配置文件中的具体配置项相对应使用@Value注解来讲对应的值注入到该属性中
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
	public JedisConnectionFactory jedisConnectionFactory() {
		//RedisStandaloneConfiguration
		//设置连接信息
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
		configuration.setDatabase(db);
		configuration.setHostName(host);
		configuration.setPort(port);
		configuration.setPassword(RedisPassword.of(password));
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxTotal(maxActive);
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        jedisPoolConfig.setMaxWaitMillis(maxWait);

		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(configuration);
        //设置连接池
		jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
		return jedisConnectionFactory;
	}



	@Bean
    public RedisTemplate<String, String> redisTemplate(JedisConnectionFactory jedisConnectionFactory){
        RedisTemplate<String, String> redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
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
    //方法参数可以是已经注册的bean。当Spring容器启动的时候，这些bean也能根据实际情况加载进来
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

    //配置
    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory ){
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        return jpaTransactionManager;
    }
}

```
