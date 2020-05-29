package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 商品搜索service
 */
@Service
public class SearchServiceImpl implements SearchService {
    @Value("${SEARCH_BASE_URL}")
    private String SEARCH_BASE_URL;
    @Override
    public SearchResult search(String queryString, int page) {
        //调用search的服务
        //查询参数
        Map<String,String> parm = new HashMap<>();
        parm.put("q",queryString);
        parm.put("page",page+"");
        try {
            //调用服务
            String json = HttpClientUtil.doGet(SEARCH_BASE_URL, parm);
            //把字符串转换成java对象
            TaotaoResult result = TaotaoResult.formatToPojo(json, SearchResult.class);
            if(result.getStatus()==200){
                SearchResult searchResult = (SearchResult) result.getData();
                return searchResult;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
