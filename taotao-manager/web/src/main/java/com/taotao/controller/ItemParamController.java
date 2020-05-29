package com.taotao.controller;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;
import com.taotao.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品模板管理
 */
@Controller
@RequestMapping("/item/param")
public class ItemParamController {
    @Autowired
    private ItemParamService itemParamService;
    @RequestMapping("/query/itemcatid/{itemCatId}")
    @ResponseBody
    public TaotaoResult getItemParamByCid(@PathVariable long itemCatId){
        TaotaoResult result = itemParamService.getItemParamByCid(itemCatId);
        return result;
    }
    @RequestMapping("/save/{itemCatId}")
    @ResponseBody
    public TaotaoResult insertItemParam(@PathVariable long itemCatId,String paramData){
        TbItemParam itemParam = new TbItemParam();
        itemParam.setItemCatId(itemCatId);
        itemParam.setParamData(paramData);
        TaotaoResult result = itemParamService.insertItemParam(itemParam);
        return result;
    }
    @RequestMapping("/list")
    @ResponseBody
    public EUDataGridResult insertItemParam(int page,int rows){
        //System.out.println("1开始1");
        EUDataGridResult result = itemParamService.getParamList(page, rows);
        //System.out.println("1完成2");
        return result;
    }
}
