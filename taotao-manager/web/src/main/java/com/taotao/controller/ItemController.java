package com.taotao.controller;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class ItemController {
    @Autowired
    ItemService itemService;
    @RequestMapping("/item/{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable long itemId){
        TbItem item = itemService.findByItemId(itemId);
        return item;
    }
    @RequestMapping("hehe")
    public String heha(){
        System.out.println("123");
        return "123h";
    }

    /**
     * 分页操作
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/item/list")
    @ResponseBody
    public EUDataGridResult getItemList(int page,int rows){
        EUDataGridResult itemList = itemService.getItemList(page, rows);
        return itemList;
    }

    /**
     * 保存
     * @param item
     * @param desc
     * @param itemParams
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/item/save", method = RequestMethod.POST )
    @ResponseBody
    public TaotaoResult saveTbItem(TbItem item , String desc,String itemParams) throws Exception {
        TaotaoResult result = itemService.creatItem(item,desc,itemParams);
        return result;
    }
}
