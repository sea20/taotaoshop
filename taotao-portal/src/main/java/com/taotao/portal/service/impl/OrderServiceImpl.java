package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.portal.pojo.Order;
import com.taotao.portal.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 处理订单service
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Value("${ORDER_BASE_URL}")
    private String ORDER_BASE_URL;
    @Value("${ORDER_CREATE_URL}")
    private String ORDER_CREATE_URL;
    @Override
    public String createOrder(Order order) {
        //调用taotao-order提交订单
        String json = HttpClientUtil.doPostJson(ORDER_BASE_URL + ORDER_CREATE_URL, JsonUtils.objectToJson(order));
        //System.out.println(json);
        //把json转换成Taotaoresult
        TaotaoResult taotaoResult = TaotaoResult.format(json);
        if(taotaoResult.getStatus()==200){
            Object orderId =  taotaoResult.getData();
            return orderId.toString();
        }
        return "";
    }
}
