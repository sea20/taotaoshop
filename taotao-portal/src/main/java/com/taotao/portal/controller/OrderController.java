package com.taotao.portal.controller;

import com.taotao.pojo.TbUser;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.pojo.Order;
import com.taotao.portal.service.CartService;
import com.taotao.portal.service.OrderService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    CartService cartService;
    @Autowired
    OrderService orderService;
    @RequestMapping("/order-cart")
    public String showOrderCart(HttpServletRequest request, Model model){
        //取购物车商品列表
        List<CartItem> list = cartService.getCartItemList(request);
        //传递给页面
        model.addAttribute("cartList",list);
        return "order-cart";
    }
    @RequestMapping("/create")
    public String createOrder(Order order,HttpServletRequest request,Model model){
        //获取用户信息(拦截器存入)
        TbUser user = (TbUser) request.getAttribute("user");
        //补全用户信息
        order.setUserId(user.getId());
        order.setBuyerNick(user.getUsername());
        try {
            String orderId = orderService.createOrder(order);
            model.addAttribute("orderId",orderId);
            model.addAttribute("payment",order.getPayment());
            model.addAttribute("date",new DateTime().plusDays(3).toString("yyyy-MM-dd"));
            return "success";
        }catch (Exception e){
            model.addAttribute("message","创建订单失败");
            return "error/exception";
        }

    }

}
