package com.taotao.controller;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 列表的树结构
 */
@Controller
@RequestMapping("/item/cat")
public class ItemCatController {

    @Autowired
    ItemCatService service;
    @RequestMapping("/list")
    @ResponseBody
    public List<EUTreeNode> getCatList(@RequestParam(value = "id",defaultValue = "0") long parentId){
        System.out.println(parentId);
        List<EUTreeNode> List = service.getCatList(parentId);
        return List;
    }
}
