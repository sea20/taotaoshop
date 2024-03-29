package com.taotao.portal.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 购物车controller
 */
@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartService cartService;
    //向购物车添加商品
    @RequestMapping("/add/{itemId}")
    public String addCartItem(@PathVariable long itemId, @RequestParam(defaultValue = "1") Integer num,
                              HttpServletRequest request, HttpServletResponse response){
        TaotaoResult result = cartService.addCartItem(itemId, num, request, response);
        return "redirect:/cart/success.html";
    }
    //跳转到成功页面
    @RequestMapping("/success")
    public String showSuccess(){
        //System.out.println("success");
        return "cartSuccess";
    }
    //获取购物车信息
    @RequestMapping("/cart")
    public String showCart(HttpServletRequest request, Model model){
        List<CartItem> cartItemList = cartService.getCartItemList(request);
        model.addAttribute("cartList",cartItemList);
        return "cart";
    }
    @RequestMapping("/delete/{itemId}")
    public String deleteCartItem(@PathVariable long itemId, HttpServletRequest request,HttpServletResponse response ){
        TaotaoResult result = cartService.deleteCartItem(itemId, request, response);
        return "redirect:/cart/cart.html";
    }
}
