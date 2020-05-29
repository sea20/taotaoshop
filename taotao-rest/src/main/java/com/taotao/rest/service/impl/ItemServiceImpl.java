package com.taotao.rest.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.rest.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品信息管理
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    TbItemMapper tbItemMapper;
    @Autowired
    TbItemParamItemMapper itemParamItemMapper;
    @Autowired
    TbItemDescMapper tbItemDescMapper;
    @Override
    public TaotaoResult getItemBaseInfo(long itemId) {
        //根据商品id查询商品信息
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
        return TaotaoResult.ok(tbItem);
    }

    @Override
    public TaotaoResult getItemDesc(long itemId) {
        //根据商品id查询商品描述

        TbItemDesc itemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);
        return TaotaoResult.ok(itemDesc);
    }

    @Override
    public TaotaoResult getItemParam(long itemId) {
        //根据商品的id查询规格参数
        TbItemParamItemExample example = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
        if(list!=null){
            return TaotaoResult.ok(list.get(0));
        }
        return TaotaoResult.build(400,"无此信息");
    }
}
