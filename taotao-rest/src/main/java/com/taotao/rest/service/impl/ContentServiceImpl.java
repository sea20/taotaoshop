package com.taotao.rest.service.impl;

import com.ctc.wstx.util.StringUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ContentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
/**
 * 轮播图
 */
import java.util.List;
@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    TbContentMapper contentMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${INDEX_CONTENT_REDIS_KEY}")
    String INDEX_CONTENT_REDIS_KEY;
    @Override
    public List<TbContent> getContentList(Long contentCid) {
        //redis查询
        try {
            String res = jedisClient.hget(INDEX_CONTENT_REDIS_KEY, contentCid + "");
            if(!StringUtils.isBlank(res)){
                //如果不是空
                List<TbContent> list = JsonUtils.jsonToList(res, TbContent.class);
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(contentCid);
        List<TbContent> list = contentMapper.selectByExample(example);
        //redis存储
        try {
            //把list转换成字符串
            String json = JsonUtils.objectToJson(list);
            jedisClient.hset("常量",contentCid+"",json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
