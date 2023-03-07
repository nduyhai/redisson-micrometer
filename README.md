# Redisson Spring Boot Micrometer

This is an example about metrics with redisson 

## View metrics

```shell
curl http://localhost:8080/actuator/metrics
```

## Redis with docker

```shell
version: '3.1'

services:
 redis:
    image: redis:alpine
    container_name: redis_db
    command: redis-server --appendonly yes
    ports:
      - 6379:6379

```