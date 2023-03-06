package com.example.redissonmicrometer;

import java.util.Collections;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonMetricsConfig {

  @Bean
  public RedissonMetrics redissonMetrics(RedissonClient redissonClient) {
    return new RedissonMetrics(redissonClient, Collections.emptyList());
  }

}
