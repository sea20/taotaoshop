package com.taotao.portal.service;


import com.taotao.portal.pojo.Item;
import com.taotao.portal.pojo.ItemInfo;

public interface ItemService {
    ItemInfo getItemById(long itemId);
    String getIdemDescById(long itemId);
    String getItemParamById(long itemId);
}
