package com.lgj.lgjshop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

@Component
public class JedisUtil {

    @Autowired
    JedisPool jedisPool;

    public String get(String key) throws Exception {
        Jedis jedis = jedisPool.getResource();
        String value = jedis.get(key);
        jedis.close();
        return value;
    }

    public void set(String key, String value) throws Exception {
        Jedis jedis = jedisPool.getResource();
        jedis.set(key, value);
        jedis.close();
    }


    public void set(String key, String value, int exp) throws Exception {
        Jedis jedis = jedisPool.getResource();
        jedis.setex(key, exp, value);
        jedis.close();
    }
    public Set<String> smembers(String key) throws Exception {
        Jedis jedis = jedisPool.getResource();
        Set<String> test = jedis.smembers(key);
        jedis.close();
        return test;
    }

    public Long sadd(String key,String account) throws Exception {
        Jedis jedis = jedisPool.getResource();
        Long hset = jedis.sadd(key,account);
        jedis.close();
        return hset;
    }
    public Long srem(String key, String account) throws Exception {
        Jedis jedis = jedisPool.getResource();
        Long hdel = jedis.srem(key, account);
        jedis.close();
        return hdel;
    }
    public boolean sismember(String key, String account) throws Exception {
        Jedis jedis = jedisPool.getResource();
        Boolean sismember = jedis.sismember(key, account);
        jedis.close();
        return sismember;
    }
    public void flush() throws Exception {
        Jedis jedis = jedisPool.getResource();
        jedis.flushAll();
        jedis.close();
    }
}