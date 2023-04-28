package com.manuelr.bank.api.config;

import com.manuelr.bank.api.model.FindBankResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.*;
import java.util.Collections;

@Configuration
public class RedisCacheConfig {

  private final String redisClusterMasterEndpoint;

  public RedisCacheConfig(
      @Value("${spring.data.redis.cluster.master.endpoint}") String redisClusterMasterEndpoint) {
    this.redisClusterMasterEndpoint = redisClusterMasterEndpoint;
  }

  @Bean
  public LettuceConnectionFactory lettuceConnectionFactory() {
    final var clusterConfiguration =
        new RedisClusterConfiguration(Collections.singletonList(redisClusterMasterEndpoint));
    return new LettuceConnectionFactory(clusterConfiguration);
  }

  @Bean
  public ReactiveHashOperations<String, Integer, FindBankResponse> redisOperations(
      LettuceConnectionFactory connectionFactory) {
    final RedisSerializationContext<String, FindBankResponse> serializationContext =
        RedisSerializationContext.<String, FindBankResponse>newSerializationContext(
                new StringRedisSerializer())
            .hashKey(new GenericToStringSerializer<>(Integer.class))
            .hashValue(new Jackson2JsonRedisSerializer<>(FindBankResponse.class))
            .build();
    return new ReactiveRedisTemplate<>(connectionFactory, serializationContext).opsForHash();
  }
}
