package com.taotao.order.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbOrderItemMapper;
import com.taotao.mapper.TbOrderMapper;
import com.taotao.mapper.TbOrderShippingMapper;
import com.taotao.order.dao.JedisClient;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private TbOrderItemMapper orderItemMapper;
    @Autowired
    private TbOrderMapper orderMapper;
    @Autowired
    private TbOrderShippingMapper orderShippingMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${ORDER_GEN_KEY}")
    private String ORDER_GEN_KEY;
    @Value("${ORDER_INIT_ID}")
    private String ORDER_INIT_ID;
    @Value("${ORDER_DETAIL_GEN_KEY}")
    private String ORDER_DETAIL_GEN_KEY;
    @Override
    public TaotaoResult creatOrder(TbOrder order, List<TbOrderItem> itemList, TbOrderShipping orderShipping) {
        //向订单中插入数据
        //获取订单号
        String id = jedisClient.get(ORDER_GEN_KEY);
        if(StringUtils.isBlank(id)){
            jedisClient.set(ORDER_GEN_KEY,ORDER_INIT_ID);
        }
        long orderId = jedisClient.incr(ORDER_GEN_KEY);//订单号

        //补全pojo属性
        order.setOrderId(orderId+"");
                //状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
        order.setStatus(1);
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        //0未评价，1评价
        order.setBuyerRate(0);
        //向订单表插入数据
        orderMapper.insert(order);
        //插入订单明细
        for (TbOrderItem tbOrderItem : itemList) {
            //补全订单明细
            long orderItemId = jedisClient.incr(ORDER_DETAIL_GEN_KEY);
            tbOrderItem.setId(orderItemId+"");
            tbOrderItem.setOrderId(orderId+"");
            orderItemMapper.insert(tbOrderItem);
        }
        //插入物流表
        orderShipping.setOrderId(orderId + "");
        orderShipping.setUpdated(new Date());
        orderShipping.setCreated(new Date());
        orderShippingMapper.insert(orderShipping);
        System.out.println("taotao"+TaotaoResult.ok(orderId));
        System.out.println("111");
        return TaotaoResult.ok(orderId);
    }
}
