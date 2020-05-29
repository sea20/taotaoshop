package com.taotao.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.common.pojo.EUDataGridResult;

public interface ItemService {
    /**
     * 测试而已
     * @param ItemId
     * @return
     */
    public TbItem findByItemId(long ItemId);

    /**
     * 返回分页信息
     * @param page
     * @param rows
     * @return
     */
    public EUDataGridResult getItemList(int page, int rows);

    /**
     * 添加商品信息
     * @param item
     * @return
     */
    public TaotaoResult creatItem(TbItem item,String decs,String itemParams)throws Exception;
}
