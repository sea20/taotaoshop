package com.taotao.rest.controller;

import com.taotao.common.utils.JsonUtils;
import com.taotao.rest.pojo.CatData;
import com.taotao.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.*;
/**
 * 查询分类列表
 * @return
 */
@Controller
public class ItemCatController {
    @Autowired
    ItemCatService itemCatService;
    @ResponseBody
    @RequestMapping(value="/itemcat/list",produces="application/json;charset=utf-8")
    public String getItemCatList(String callback){
        CatData list = itemCatService.getItemCatList();
        String json = JsonUtils.objectToJson(list);
        String result = callback+'('+json+");";
        System.out.println(result);
        return result;
    }
    /*@RequestMapping("/itemcat/list")
    @ResponseBody
    public Object getItemCatList(String callback){
        CatData list = itemCatService.getItemCatList();
        MappingJacksonValue value = new MappingJacksonValue(list);
        value.setJsonpFunction(callback);

        return value;
    }*/
}
