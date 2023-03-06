package com.example.redissonmicrometer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class RedissonMicrometerApplication {

  public static void main(String[] args) {
    SpringApplication.run(RedissonMicrometerApplication.class, args);
  }

}
