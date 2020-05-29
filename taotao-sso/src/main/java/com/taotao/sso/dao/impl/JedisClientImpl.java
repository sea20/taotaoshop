package com.taotao.sso.dao.impl;

import com.taotao.sso.dao.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Repository
public class JedisClientImpl implements JedisClient {
    @Autowired
    private JedisPool jedisPool;
    @Override
    public String get(String key) {
        Jedis resource = jedisPool.getResource();
        String s = resource.get(key);
        resource.close();
        return s;
    }

    @Override
    public String set(String key, String value) {
        Jedis resource = jedisPool.getResource();
        String s = resource.set(key,value);
        resource.close();
        return s;
    }

    @Override
    public Long hset(String hkey, String key, String value) {
        Jedis resource = jedisPool.getResource();
        Long res = resource.hset(hkey, key, value);
        resource.close();
        return res;
    }

    @Override
    public String hget(String hkey, String key) {
        Jedis resource = jedisPool.getResource();
        String res = resource.hget(hkey, key);
        resource.close();
        return res;
    }

    @Override
    public long hdel(String hkey,String key) {
        Jedis resource = jedisPool.getResource();
        long res = resource.del(hkey,key);
        resource.close();
        return res;
    }

    @Override
    public long del(String key) {
        Jedis resource = jedisPool.getResource();
        long res = resource.del(key);
        resource.close();
        return res;
    }

    @Override
    public long incr(String key) {
        Jedis resource = jedisPool.getResource();
        Long res = resource.incr(key);
        resource.close();
        return res;
    }

    @Override
    public long expire(String key, int second) {
        Jedis resource = jedisPool.getResource();
        Long res = resource.expire(key, second);
        resource.close();
        return res;
    }

    @Override
    public long ttl(String key) {
        Jedis resource = jedisPool.getResource();
        Long res = resource.ttl(key);
        resource.close();
        return res;
    }
}
