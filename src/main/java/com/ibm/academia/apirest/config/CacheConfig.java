package com.ibm.academia.apirest.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

  public static final String BANK_API_CACHE_NAME = "BankApiApplicationCache";

  @Bean
  public CacheManager cacheManager() {
    return new ConcurrentMapCacheManager(BANK_API_CACHE_NAME);
  }
}
