package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 页面跳转controller
 *
 */
@Controller
public class pageController {
    /**
     * 栈实首页
     * @return
     */
    @RequestMapping("/")
    public String showIndex(){
        return "index";
    }
    /**
     * 展示且他页面
     * @param page
     * @return
     */
    @RequestMapping("/{page}")
    public String showPage(@PathVariable String page){
        return page;
    }
}
