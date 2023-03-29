package com.ibm.academia.apirest.config;

import com.ibm.academia.apirest.model.FindBankResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.*;

@Configuration
public class RedisCacheConfig {

  @Value("${spring.data.redis.host}")
  private String redisHost;

  @Value("${spring.data.redis.port}")
  private int redisPort;

  @Bean
  public LettuceConnectionFactory lettuceConnectionFactory() {
    final var redisStandaloneConfig = new RedisStandaloneConfiguration();
    redisStandaloneConfig.setHostName(redisHost);
    redisStandaloneConfig.setPort(redisPort);
    return new LettuceConnectionFactory(redisStandaloneConfig);
  }

  @Bean
  public ReactiveHashOperations<String, Integer, FindBankResponse> redisOperations(
      LettuceConnectionFactory connectionFactory) {
    RedisSerializationContext<String, FindBankResponse> serializationContext =
        RedisSerializationContext.<String, FindBankResponse>newSerializationContext(
                new StringRedisSerializer())
            .hashKey(new GenericToStringSerializer<>(Integer.class))
            .hashValue(new Jackson2JsonRedisSerializer<>(FindBankResponse.class))
            .build();
    return new ReactiveRedisTemplate<>(connectionFactory, serializationContext).opsForHash();
  }
}
