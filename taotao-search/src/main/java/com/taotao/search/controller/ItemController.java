package com.taotao.search.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 索引库维护
 */
@Controller
@RequestMapping("/manager")
public class ItemController {
    @Autowired
    ItemService itemService;
    /**
     * 将商品信息导入到索引库
     */
    @RequestMapping("/importall")
    @ResponseBody
    public void importAllItems(){
        TaotaoResult result = itemService.importAllItems();
        System.out.println("更新完毕");
        //return result;
    }
}
