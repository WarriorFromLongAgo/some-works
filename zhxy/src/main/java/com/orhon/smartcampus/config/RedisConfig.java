package com.orhon.smartcampus.config;

import java.nio.charset.Charset;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;


/**
 * redis配置<br>
 * 集群下启动session共享，需打开@EnableRedisHttpSession<br>
 * 单机下不需要
 *
 * @author caodw
 *
 *
 */
//@EnableRedisHttpSession
@Configuration
public class RedisConfig {


	public String host = "39.104.181.104";

	public Integer port =  6379;




	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Bean("redisTemplate")
	public RedisTemplate redisTemplate(@Lazy RedisConnectionFactory connectionFactory) {
		RedisTemplate redis = new RedisTemplate();
		GenericToStringSerializer<String> keySerializer = new GenericToStringSerializer<String>(String.class);
		redis.setKeySerializer(keySerializer);
		redis.setHashKeySerializer(keySerializer);
		GenericJackson2JsonRedisSerializer valueSerializer = new GenericJackson2JsonRedisSerializer();
		StringRedisSerializer serializer = new StringRedisSerializer(Charset.forName("UTF-8"));
		redis.setValueSerializer(valueSerializer);
		redis.setHashValueSerializer(serializer);
		redis.setConnectionFactory(connectionFactory);

		return redis;
	}


	@Bean
	public JedisPoolConfig jedisPoolConfig(){
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		//最大连接数
		jedisPoolConfig.setMaxTotal(100);
		//最小空闲连接数
		jedisPoolConfig.setMinIdle(20);
		//当池内没有可用连接时，最大等待时间
		jedisPoolConfig.setMaxWaitMillis(10000);
		//其他属性可以自行添加
		return jedisPoolConfig;
	}


	@Bean
	public RedisConnectionFactory redisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		//设置redis服务器的host或者ip地址
		redisStandaloneConfiguration.setHostName(host);
		redisStandaloneConfiguration.setPort(port);
		//获得默认的连接池构造
		//这里需要注意的是，edisConnectionFactoryJ对于Standalone模式的没有（RedisStandaloneConfiguration，JedisPoolConfig）的构造函数，对此
		//我们用JedisClientConfiguration接口的builder方法实例化一个构造器，还得类型转换
		JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcf = (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
		//修改我们的连接池配置
		jpcf.poolConfig(jedisPoolConfig);
		//通过构造器来构造jedis客户端配置
		JedisClientConfiguration jedisClientConfiguration = jpcf.build();

		return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
	}



}
