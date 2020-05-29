package com.taotao.search.service.impl;

import com.taotao.search.dao.ItemSearchDao;
import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 查询service
 */
@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    ItemSearchDao searchDao;
    @Override
    public SearchResult search(String queryString, int page, int rows) throws Exception {
        //创建查询对象
        SolrQuery query = new SolrQuery();
        //设置查询条件
        query.setQuery(queryString);
        //设置分页
        query.setStart((page-1)*rows);
        query.setRows(rows);
        //设置默认搜索域
        query.set("df","item_keywords");
        //设置高亮显示
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<em style=\" color:red\">");
        query.setHighlightSimplePost("</em>");
        //查询
        SearchResult result = searchDao.searchItem(query);
        //计算查询结果总页数
        long recordCount = result.getRecordCount();
        long pageCount = recordCount/rows;
        if(recordCount % rows>0){
            pageCount++;
        }
        result.setPageCount((int) pageCount);
        result.setCurPage(page);
        return result;
    }
}
