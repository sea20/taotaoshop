package com.taotao.rest.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * redis与mysql数据同步(轮播图)
 */
@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    JedisClient jedisClient;
    @Value("${INDEX_CONTENT_REDIS_KEY}")
    String INDEX_CONTENT_REDIS_KEY;
    @Override
    public TaotaoResult syncContent(long contentCid) {
        try {
            long hdel = jedisClient.hdel(INDEX_CONTENT_REDIS_KEY, contentCid + "");
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return TaotaoResult.ok();
    }
}
