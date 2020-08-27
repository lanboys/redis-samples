package com.bing.lan.redis;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by lb on 2020/7/28.
 */

@Slf4j
@Service
@CacheConfig(cacheNames = "hello")// cacheName没有配置的话，使用默认配置
public class HelloService {

    private Map<String, String> dbMock = new HashMap<>();

    public Map<String, String> list() {
        for (String key : dbMock.keySet()) {
            System.out.println("list(): key: " + key + ", value: " + dbMock.get(key));
        }
        return dbMock;
    }

    @Cacheable(key = "#id")
    public String getById(String id) {
        System.out.println("getById(): " + id);
        return dbMock.get(id);
    }

    @CachePut(key = "#id")
    public String updateById(String id, String username) {
        System.out.println("updateById(): " + id);
        System.out.println("updateById(): " + username);
        dbMock.put(id, username);
        return username;
    }

    @CacheEvict
    public String deleteById(String id) {
        System.out.println("deleteById(): " + id);
        return dbMock.remove(id);
    }
}
