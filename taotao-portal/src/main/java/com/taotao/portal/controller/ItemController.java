package com.taotao.portal.controller;

import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.Item;
import com.taotao.portal.pojo.ItemInfo;
import com.taotao.portal.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.*;

/**
 * 商品详情页面展示
 */
@Controller
public class ItemController {
    @Autowired
    ItemService itemService;
    @RequestMapping("/item/{itemId}")
    public String showItem(@PathVariable long itemId, Model model){
        ItemInfo item = itemService.getItemById(itemId);
        model.addAttribute("item",item);
      //  System.out.println(item);
        return "item";
    }
    @RequestMapping(value = "/item/desc/{itemId}",produces = MediaType.TEXT_HTML_VALUE+" ;charset=utf-8")
    @ResponseBody
    public String getItemDesc(@PathVariable long itemId){
        //System.out.println("desc1");
        return itemService.getIdemDescById(itemId);
    }
    @RequestMapping(value = "/item/param/{itemId}",produces = MediaType.TEXT_HTML_VALUE+" ;charset=utf-8")
    @ResponseBody
    public String getItemParam(@PathVariable long itemId){
        //System.out.println("param1");
        return itemService.getItemParamById(itemId);
    }
}
