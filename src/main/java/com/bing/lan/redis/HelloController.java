package com.bing.lan.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by lb on 2020/7/28.
 */

@Slf4j
@RestController
public class HelloController {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    HelloService helloService;

    @RequestMapping("/list")
    public Map<String, String> list() {
        return helloService.list();
    }

    @RequestMapping("/getById")
    public String getById(String id) {
        String byId = helloService.getById(id);
        return byId;
    }

    @RequestMapping("/updateById")
    public String updateById(String id, String username) {
        String s = helloService.updateById(id, username);
        return s;
    }

    @RequestMapping("/deleteById")
    public String deleteById(String id) {
        String s = helloService.deleteById(id);
        return s;
    }

    @RequestMapping("/getValue")
    public Object value(String key) {
        Object o = redisTemplate.opsForValue().get(key);
        log.info("getValue key: {}, value: {}", key, o);
        return o;
    }

    @RequestMapping("/setValue")
    public void setValue(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
        log.info("setValue key: {}, value: {}", key, value);
    }
}
