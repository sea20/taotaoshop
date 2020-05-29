package com.taotao.rest.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 缓存同步
 */
@Controller
@RequestMapping("/cache/sync")
public class RedisController {
    @Autowired
    RedisService redisService;
    @RequestMapping("/content/{contentCid}")
    @ResponseBody
    public TaotaoResult contentSync(@PathVariable long contentCid){
        TaotaoResult result = redisService.syncContent(contentCid);
        return result;
    }

}
