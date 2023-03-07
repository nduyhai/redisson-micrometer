package com.example.redissonmicrometer;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.connection.MasterSlaveEntry;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.binder.MeterBinder;

public class RedissonMetrics implements MeterBinder {

  private final Iterable<Tag> tags;
  private RedissonClient redissonClient;

  public RedissonMetrics(RedissonClient redissonClient, Iterable<Tag> tags) {
    this.redissonClient = redissonClient;
    this.tags = Tags.of(tags);
  }

  @Override
  public void bindTo(MeterRegistry registry) {
    final var redisson = (Redisson) redissonClient;

    Gauge.builder(Names.CONNECTIONS, redisson,
            client -> client.getConnectionManager().getEntrySet().size()).tags(tags)
        .description("Number of active connections to the given db").register(registry);

    Gauge.builder(Names.CLIENTS, redisson,
            client -> client.getConnectionManager().getEntrySet().stream()
                .map(MasterSlaveEntry::getAvailableClients).mapToInt(e -> e).sum()).tags(tags)
        .description("Number of available clients").register(registry);

    Gauge.builder(Names.SLAVES, redisson,
            client -> client.getConnectionManager().getEntrySet().stream()
                .map(MasterSlaveEntry::getAvailableSlaves).mapToInt(e -> e).sum()).tags(tags)
        .description("Number of available slaves").register(registry);

    Gauge.builder(Names.MASTER_POOL_SIZE, redisson,
            client -> client.getConnectionManager().getConfig().getMasterConnectionPoolSize())
        .tags(tags).description("Number of master pool size").register(registry);

    Gauge.builder(Names.SLAVES_POOL_SIZE, redisson,
            client -> client.getConnectionManager().getConfig().getSlaveConnectionPoolSize())
        .description("Number of slaves pool size").register(registry);
  }

  static final class Names {

    static final String CONNECTIONS = of("connections");
    static final String CLIENTS = of("clients");
    static final String SLAVES = of("slaves");
    static final String MASTER_POOL_SIZE = of("masterPoolSize");
    static final String SLAVES_POOL_SIZE = of("slavesPoolSize");

    private Names() {
    }

    private static String of(String name) {
      return "redisson." + name;
    }

  }
}
