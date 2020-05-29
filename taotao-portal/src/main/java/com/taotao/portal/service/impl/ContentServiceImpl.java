package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbContent;
import com.taotao.portal.service.ContentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 访问rest得到content
 */
@Service
public class ContentServiceImpl implements ContentService {
    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${BASE_INDEX_AD_URL}")
    private String BASE_INDEX_AD_URL;
    @Value("${PIC_BASE_URL}")
    private String PIC_BASE_URL;
    @Override
    public String getContentList() {
        //调用服务层（rest）服务
        String result = HttpClientUtil.doGet(REST_BASE_URL + BASE_INDEX_AD_URL);
        //把字符串转换回TaotaoResult
        try {
            TaotaoResult taotaoResult = TaotaoResult.formatToList(result, TbContent.class);
            //取出内容列表
            List<TbContent> list = (List<TbContent>) taotaoResult.getData();
            //创建jsp需要的json
            List<Map> resultList = new ArrayList<>();
            Map map;
            for (TbContent tbContent : list) {
                map = new HashMap();
                map.put("src",PIC_BASE_URL+tbContent.getPic());
                map.put("height",240);
                map.put("width",670);
                map.put("srcB",PIC_BASE_URL+tbContent.getPic2());
                map.put("heightB",240);
                map.put("widthB",550);
                map.put("href",tbContent.getUrl());
                map.put("alt",tbContent.getSubTitle());
                resultList.add(map);
            }
            return JsonUtils.objectToJson(resultList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
