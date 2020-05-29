package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;
    @Value("${UPDATE_SEARCH_URL}")
    private String UPDATE_SEARCH_URL;
    /**
     * 测试
     * @param ItemId
     * @return
     */
    @Override
    public TbItem findByItemId(long ItemId) {
        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(ItemId);
        List<TbItem> tbItems = itemMapper.selectByExample(example);
        TbItem tbItem = tbItems.get(0);
        return tbItem;
    }

    /**
     * 分页操作
     * @param page
     * @param rows
     * @return
     */
    @Override
    public EUDataGridResult getItemList(int page, int rows) {
        TbItemExample example = new TbItemExample();
        PageHelper.startPage(page,rows);
        List<TbItem> list = itemMapper.selectByExample(example);
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        long total = pageInfo.getTotal();
        EUDataGridResult result = new EUDataGridResult();
        result.setRows(list);
        result.setTotal(total);
        return result;
    }

    /**
     * 添加商品
     * @param item
     * @return
     */
    @Override
    public TaotaoResult creatItem(TbItem item,String desc,String itemParams) throws Exception {
        item.setId(IDUtils.getItemId());
        item.setStatus((byte)1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        itemMapper.insert(item);
        TaotaoResult taotaoResult = insertItemDesc(item.getId(), desc);
        if(taotaoResult.getStatus()!=200){
            throw new Exception();
        }else{
            taotaoResult = insertItemParams(item.getId(), itemParams);
            if(taotaoResult.getStatus()!=200){
                throw new Exception();
            }
        }
        HttpClientUtil.doGet(UPDATE_SEARCH_URL);
        return TaotaoResult.ok();
    }

    /**
     * 添加商品描述
     * @param id
     * @param desc
     * @return
     */
    public TaotaoResult insertItemDesc(long id,String desc){
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        itemDesc.setItemId(id);
        itemDesc.setItemDesc(desc);
        itemDescMapper.insert(itemDesc);
        return TaotaoResult.ok();
    }
    /**
     * 添加商品规格
     * @param itemParams
     * @param itemId
     * @return
     */
    public TaotaoResult insertItemParams(long itemId,String itemParams){
        TbItemParamItem paramsItem = new TbItemParamItem();
        paramsItem.setCreated(new Date());
        paramsItem.setUpdated(new Date());
        paramsItem.setParamData(itemParams);
        paramsItem.setItemId(itemId);
        System.out.println(itemParams);
        itemParamItemMapper.insert(paramsItem);
        return TaotaoResult.ok();
    }
}
