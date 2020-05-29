package com.taotao.order.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.order.pojo.Order;
import com.taotao.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 订单controller
 */
@Controller
public class OrderController {
    @Autowired
    OrderService orderService;

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult createOrder(@RequestBody Order order){
        //System.out.println("create");
       TaotaoResult result = null;
        try{
            result = orderService.creatOrder(order, order.getOrderItems(), order.getOrderShipping());
        }catch (Exception e){
            // System.out.println(ExceptionUtil.getStackTrace(e));
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        //System.out.println(result);
        //System.out.println(result);
        //System.out.println(result==null);
        return result;
    }
}
