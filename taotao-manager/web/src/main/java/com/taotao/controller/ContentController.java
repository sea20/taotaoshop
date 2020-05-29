package com.taotao.controller;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 内容管理
 */
@Controller
@RequestMapping("/content")
public class ContentController {
    @Autowired
    ContentService contentService;
    @RequestMapping("/query/list")
    @ResponseBody
    public EUDataGridResult getContentList(Long categoryId,int page,int rows){
        EUDataGridResult result = contentService.getContentList(categoryId, page, rows);
        return result;
    }
    @RequestMapping("/save")
    @ResponseBody
    public TaotaoResult insertContent(TbContent content){
        TaotaoResult result = contentService.insertContent(content);
        return result;
    }
    @RequestMapping("/delete")
    @ResponseBody
    public TaotaoResult deleteContent(Long ids){
        return contentService.deleteContent(ids);
    }
}
