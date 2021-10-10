package com.catstore.utils.redisUtils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
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

    public void expire(String key, int seconds) {
        Jedis jedis = jedisPool.getResource();
        jedis.expire(key, seconds);
        jedis.close();
    }

    public void expireAt(String key, long unixTime) {
        Jedis jedis = jedisPool.getResource();
        jedis.expireAt(key, unixTime);
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

    public void hset(String key, String field, String value) {
        Jedis jedis = jedisPool.getResource();
        jedis.hset(key, field, value);
        jedis.close();
    }

    public void lpush(String key, List<String> values) {
        Jedis jedis = jedisPool.getResource();
        for (String value : values)
            jedis.lpush(key, value);
        jedis.close();
    }

    public void lpush(String key, String... values) {
        Jedis jedis = jedisPool.getResource();
        jedis.lpush(key, values);
        jedis.close();
    }

    public void rpush(String key, List<String> values) {
        Jedis jedis = jedisPool.getResource();
        for (String value : values)
            jedis.rpush(key, value);
        jedis.close();
    }

    public <T>void rpushObj(String key, List<T> values) {
        Jedis jedis = jedisPool.getResource();
        for (T value : values)
            jedis.rpush(key, value.toString());
        jedis.close();
    }

    public void rpush(String key, String... values) {
        Jedis jedis = jedisPool.getResource();
        jedis.rpush(key, values);
        jedis.close();
    }

    public String lpop(String key) {
        Jedis jedis = jedisPool.getResource();
        String value = jedis.lpop(key);
        jedis.close();
        return value;
    }

    public List<String> lrange(String key, int start, int stop) {
        Jedis jedis = jedisPool.getResource();
        List<String> values = jedis.lrange(key, start, stop);
        jedis.close();
        return values;
    }

    public <T> List<T> lrange(String key, int start, int stop, Type type) {
        Jedis jedis = jedisPool.getResource();
        List<String> values = jedis.lrange(key, start, stop);
        List<T> list = new ArrayList<>();
        for (String value : values) {
            list.add(JSONObject.parseObject(value, type));
        }
        jedis.close();
        return list;
    }

    public List<String> getWholeList(String key) {
        Jedis jedis = jedisPool.getResource();
        List<String> values = jedis.lrange(key, 0, -1);
        jedis.close();
        return values;
    }

    public <T> List<T> getWholeList(String key, Type type) {
        Jedis jedis = jedisPool.getResource();
        List<String> values = jedis.lrange(key, 0, -1);
        jedis.close();
        List<T> list = new ArrayList<>();
        for (String value : values) {
            list.add(JSONObject.parseObject(value, type));
        }
        return list;
    }
}
