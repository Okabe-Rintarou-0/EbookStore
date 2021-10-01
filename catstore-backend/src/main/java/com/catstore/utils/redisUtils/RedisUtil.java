package com.catstore.utils.redisUtils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.lang.reflect.Type;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class RedisUtil {
    @Autowired
    private JedisPool jedisPool;

    public void set(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        jedis.set(key, value);
        jedis.close();
    }

    public void set(String key, Object value) {
        Jedis jedis = jedisPool.getResource();
        jedis.set(key, value.toString());
        jedis.close();
    }

    public String get(String key) {
        Jedis jedis = jedisPool.getResource();
        String value = jedis.get(key);
        jedis.close();
        return value;
    }

    public <T> T get(String key, Type type) {
        Jedis jedis = jedisPool.getResource();
        String value = jedis.get(key);
        T ret = JSONObject.parseObject(value, type);
        jedis.close();
        return ret;
    }

    public Long del(String key) {
        Jedis jedis = jedisPool.getResource();
        Long deleted = jedis.del(key);
        jedis.close();
        return deleted;
    }

    public void del(String key, Long delay) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Jedis jedis = jedisPool.getResource();
                jedis.del(key);
                jedis.close();
                System.out.println("try del");
            }
        }, delay);
    }
}
