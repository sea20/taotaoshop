package com.taotao.controller;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 广告内容分类管理
 */
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
    @Autowired
    ContentCategoryService contentCategoryService;
    @RequestMapping("/list")
    @ResponseBody
    public List<EUTreeNode> getContentCatList(@RequestParam(value = "id",defaultValue = "0") long parentId){
        List<EUTreeNode> list = contentCategoryService.getCategoryList(parentId);
        return list;
    }
    @RequestMapping("/create")
    @ResponseBody
    public TaotaoResult insertContentCategory(long parentId,String name){
        TaotaoResult result = contentCategoryService.insertContentCategory(parentId, name);
        return result;
    }
    @RequestMapping("/delete")
    @ResponseBody
    public TaotaoResult deleteContentCategory(Long id){

        System.out.println(id);
        contentCategoryService.deleteContentCategory(id);
        return TaotaoResult.ok();
    }
    @RequestMapping("/update")
    @ResponseBody
    public TaotaoResult updateContentCategoryName(Long id,String name){

        System.out.println(id);
        System.out.println(name);
        contentCategoryService.updateContentCategoryName(id,name);
        return TaotaoResult.ok();
    }
}
