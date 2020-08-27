package com.bing.lan.redis.config;

import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.Duration;

/**
 * Created by lb on 2020/8/19.
 */

@Component
public class CacheConfig {

    /**
     * {@link org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration}
     */
    @Component
    public static class SimpleRedisCacheManagerBuilderCustomizer implements RedisCacheManagerBuilderCustomizer {

        @Override
        public void customize(RedisCacheManager.RedisCacheManagerBuilder builder) {
            try {
                Field field = builder.getClass().getDeclaredField("defaultCacheConfiguration");
                field.setAccessible(true);
                RedisCacheConfiguration configuration = (RedisCacheConfiguration) field.get(builder);

                // 重新配置 value 序列化方法
                configuration = configuration.serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string()))
                        .entryTtl(Duration.ofSeconds(100));// 缓存有效期

                builder.cacheDefaults(configuration);

                RedisCacheConfiguration finalConfiguration = configuration;
                builder.getConfiguredCaches().forEach(s -> builder.withCacheConfiguration(s, finalConfiguration));

                // 手动添加 cacheName
                //builder.withCacheConfiguration("lan", finalConfiguration);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Component
    public static class SimpleCacheManagerCustomizer implements CacheManagerCustomizer<RedisCacheManager> {

        @Override
        public void customize(RedisCacheManager cacheManager) {
            System.out.println("customize(): ");
        }
    }
}
