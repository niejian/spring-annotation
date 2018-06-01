package cn.com.spring.bean;

import org.springframework.beans.factory.annotation.Value;

public class RedisPropertiesBean {
	private Integer port;
	private String host;
	private String password;
	private Integer db;
	private Integer timeout;
	private Integer maxIdle;
	private Integer maxActive;
	private Integer maxWait;
	private boolean testOnBorrow;

	public Integer getPort() {
		return port;
	}

	@Value("${redis.port}")
	public void setPort(Integer port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	@Value("${redis.host}")
	public void setHost(String host) {
		this.host = host;
	}

	public String getPassword() {
		return password;
	}

	@Value("${redis.pass}")
	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getDb() {
		return db;
	}

	@Value("${redis.default.db}")
	public void setDb(Integer db) {
		this.db = db;
	}

	public Integer getTimeout() {
		return timeout;
	}

	@Value("${redis.timeout}")
	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public Integer getMaxIdle() {
		return maxIdle;
	}

	@Value("${redis.maxIdle}")
	public void setMaxIdle(Integer maxIdle) {
		this.maxIdle = maxIdle;
	}

	public Integer getMaxActive() {
		return maxActive;
	}

	@Value("${redis.maxActive}")
	public void setMaxActive(Integer maxActive) {
		this.maxActive = maxActive;
	}

	public Integer getMaxWait() {
		return maxWait;
	}

	@Value("${redis.maxWait}")
	public void setMaxWait(Integer maxWait) {
		this.maxWait = maxWait;
	}

	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}
	
	@Value("${redis.testOnBorrow}")
	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	@Override
	public String toString() {
		return "RedisPropertiesBean [port=" + port + ", host=" + host + ", password=" + password + ", db=" + db
				+ ", timeout=" + timeout + ", maxIdle=" + maxIdle + ", maxActive=" + maxActive + ", maxWait=" + maxWait
				+ ", testOnBorrow=" + testOnBorrow + "]";
	}

	
}
