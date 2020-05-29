package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 *广告
 */
@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    TbContentMapper contentMapper;
    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${REST_CONTENT_SYNC_URL}")
    private String REST_CONTENT_SYNC_URL;

    /**
     * 广告各类的详细列表
     * @param categoryId
     * @param page
     * @param rows
     * @return
     */
    @Override
    public EUDataGridResult getContentList(long categoryId, int page, int rows) {
        //System.out.println("开始了0.0");
        //System.out.println(categoryId);
        //System.out.println(page);
        //System.out.println(rows);
        PageHelper.startPage(page,rows);
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
        System.out.println(list);
        PageInfo<TbContent> info = new PageInfo<>(list);
        long total = info.getTotal();
        EUDataGridResult result = new EUDataGridResult();
        result.setRows(list);
        result.setTotal(total);
        System.out.println(list);
        return result;
    }

    /**
     * 加入新广告
     * @param content
     * @return
     */
    @Override
    public TaotaoResult insertContent(TbContent content) {
        content.setUpdated(new Date());
        content.setCreated(new Date());
        contentMapper.insert(content);
        //缓存同步
        try {
            HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_SYNC_URL+content.getCategoryId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TaotaoResult.ok();
    }

    /**
     * 删除广告
     * @param ids
     * @return
     */
    @Override
    public TaotaoResult deleteContent(Long ids) {
        TbContent content = contentMapper.selectByPrimaryKey(ids);
        contentMapper.deleteByPrimaryKey(ids);
        //缓存同步
        try {
            HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_SYNC_URL+content.getCategoryId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TaotaoResult.ok();
    }
}
