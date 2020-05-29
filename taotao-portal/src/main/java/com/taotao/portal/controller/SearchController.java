package com.taotao.portal.controller;

import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

/**
 * 查询商品信息
 */
@Controller
public class SearchController {
    @Autowired
    SearchService searchService;
    @RequestMapping("/search")
    public String search(@RequestParam("q")String queryString,@RequestParam(defaultValue = "1") Integer page, Model model){
        if(queryString!=null){
            try {
                queryString = new String(queryString.getBytes("iso8859-1"),"utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        SearchResult search = searchService.search(queryString, page);
        //传递参数给页面
        model.addAttribute("query",queryString);
        model.addAttribute("totalPages",search.getRecordCount());
        model.addAttribute("itemList",search.getItemList());
        model.addAttribute("page",page);
        return "search";
    }
}
